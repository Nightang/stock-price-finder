package org.nightang.ws;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Properties;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.config.RequestConfig.Builder;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.StandardHttpRequestRetryHandler;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class HttpClientWrapper {
	
	private static final Log log = LogFactory.getLog(HttpClientWrapper.class);
	
	private CloseableHttpClient httpClient;
	
	private Configuration config;
	private ResponseHandler<String> responseHandler;
	
	public HttpClientWrapper(String configPath, HttpRequestInterceptor hreqi, 
			HttpResponseInterceptor hrespi) throws GeneralSecurityException, IOException {
		this.config = new Configuration(configPath);
		this.httpClient = buildHttpClient(config, hreqi, hrespi);
		this.responseHandler = buildResponseHandler();		
	}
	
	public void close() throws IOException {
		httpClient.close();
	}
	
	public String doGet(String url) throws IOException {
		long start = System.currentTimeMillis();			
		HttpGet get = new HttpGet(url);
		String result = httpClient.execute(get, responseHandler);				
		log.info("GET: " + url + " | Duration (ms): " + (System.currentTimeMillis() - start));
		return result;
	}
	
	public String doPost(String path) throws IOException {
		return doPost(path, null, null);
	}
	
	public String doPostText(String path, String text) throws IOException {
		return doPost(path, text, null);
	}
	
	public String doPostJson(String path, String json) throws IOException {
		return doPost(path, json, "application/json");
	}
	
	private String doPost(String url, String entityStr, String contentType) throws IOException {
		long start = System.currentTimeMillis();
		HttpPost post = new HttpPost(url);
		if (entityStr != null) {
			StringEntity entity = new StringEntity(entityStr);
			post.setEntity(entity);
			
			if (contentType != null) {
				post.setHeader("Content-type", contentType);
			}
		}
		String result = httpClient.execute(post, responseHandler);				
		log.info("POST: " + url + " | Duration (ms): " + (System.currentTimeMillis() - start));
		return result;
	}
	
	public String doPut(String url) throws IOException {
		long start = System.currentTimeMillis();
		HttpPut put = new HttpPut(url);
		String result = httpClient.execute(put, responseHandler);
		log.info("PUT: " + url + " | Duration (ms): " + (System.currentTimeMillis() - start));
		return result;
	}
	
	private ResponseHandler<String> buildResponseHandler() {
		return new ResponseHandler<String>() {
			
			@Override
			public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
				int status = response.getStatusLine().getStatusCode();
				
				HttpEntity entity = response.getEntity();
				String entityStr = (entity == null)? null: EntityUtils.toString(entity, "UTF-8");
				
				if (status >= 200 && status < 300) {
					return entityStr;
				} else {
					log.warn("Non-Nomral Status Code Returned: " + status + ", Content: " + entityStr);
					throw new ClientProtocolException(entityStr);
				}
			}
			
		};
	}
	
	private CloseableHttpClient buildHttpClient(Configuration config, 
		HttpRequestInterceptor hreqi, HttpResponseInterceptor hrespi) throws GeneralSecurityException, IOException {
		log.info("Initialize HTTP Client ......");
		
		// Create a registry of custom connection socket factories for supported
		// protocol schemes.
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
				.register("http", PlainConnectionSocketFactory.INSTANCE)
				.register("https", initSSLSocketFactory(config))
				.build();
		
		// Create a connection manager with custom configuration.
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
		
		// Create global request configuration
		Builder rcBuilder = RequestConfig.custom();
		rcBuilder.setCookieSpec(CookieSpecs.DEFAULT);
		rcBuilder.setExpectContinueEnabled(true);
		rcBuilder.setSocketTimeout(config.getWsSocketTimeout());
		rcBuilder.setConnectTimeout(config.getWsConnectTimeout());
		rcBuilder.setConnectionRequestTimeout(config.getWsConnectionRequestTimeout());
		if(config.isEnableProxy()) {
			// Set Proxy Setting
			log.info("Set Up Proxy: " + config.getProxyHost());
			rcBuilder.setProxy(config.getProxyHost());
		}
		RequestConfig defaultRequestConfig = rcBuilder.build();
		
		// Create an HttpClient with the given custom dependencies and configuration.
		HttpClientBuilder hcp = HttpClients.custom();
		hcp.setConnectionManager(cm);
		hcp.setRetryHandler(new StandardHttpRequestRetryHandler());
		hcp.setDefaultRequestConfig(defaultRequestConfig);
		if(config.isEnableProxyCredentials()) {
			// Set Proxy Credentials Setting
			log.info("Enable Proxy Credentials");
			hcp.setDefaultCredentialsProvider(config.getProxyCredProvider());
		}
		hcp.addInterceptorLast(new HttpRequestInterceptor() {
			@Override
			public void process(HttpRequest req, HttpContext context) throws HttpException, IOException {
				req.addHeader("Accept-Encoding", "gzip");
			}
		});
		// Add Extension Interceptor
		if(hreqi != null) {
			hcp.addInterceptorLast(hreqi);
		}
		
		hcp.addInterceptorLast(new HttpResponseInterceptor() {
			@Override
			public void process(HttpResponse resp, HttpContext context) throws HttpException, IOException {
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					Header ceheader = entity.getContentEncoding();
					if (ceheader != null) {
						HeaderElement[] codecs = ceheader.getElements();
						for (int i=0; i<codecs.length; i++) {
							if (codecs[i].getName().equalsIgnoreCase("gzip")) {
								resp.setEntity(new GzipDecompressingEntity(resp.getEntity()));
								return;
							}
						}
					}
				}
			}
		});
		// Add Extension Interceptor
		if(hrespi != null) {
			hcp.addInterceptorLast(hrespi);
		}
		
		return hcp.build();
	}
	
	private static SSLConnectionSocketFactory initSSLSocketFactory(Configuration config) 
					throws GeneralSecurityException, IOException 
			 {
		// SSL context for secure connections can be created either based on
		// system or application specific properties.
		
		if(config.isEnableSSLCertValidation()) {
			log.info("Enable SSL Certificate Validation");
			
			// Create a KeyStore containing our trusted CAs
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			
			if(config.isLoadJavaDefaultKeys()) {
				log.info("Load Certificate from Java Default Key Store ......");
				String path = System.getProperties().getProperty("java.home") 
							+ File.separator + "lib" 
							+ File.separator + "security" 
							+ File.separator + "cacerts";
				char[] password = config.getLoadJavaDefaultKeysPassword() != null ? 
						config.getLoadJavaDefaultKeysPassword().toCharArray() : null;
				keyStore.load(new FileInputStream(path), password);
			} else {
				keyStore.load(null, null);
			}
			
			if(config.isLoadPrivateKeys()) {
				log.info("Load Certificate from Private Key Store, Path: " + config.getLoadPrivateKeysPath());
				CertificateFactory cf = CertificateFactory.getInstance("X.509");
				File certFolder = new File(config.getLoadPrivateKeysPath());
				for (final File certFile : certFolder.listFiles()) {
					if (!certFile.isDirectory() && certFile.getName().endsWith(".cer")) {
						InputStream is = new FileInputStream(certFile);
						try {
							Certificate cert = cf.generateCertificate(is);
							keyStore.setCertificateEntry(certFile.getName(), cert);
							log.info("Successfully loaded cert : " + certFile.getName());
						} finally {
							is.close();
						}
					}
				}
			}
			
			// Create a TrustManager that trusts the CAs in our KeyStore
			String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
			TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
			tmf.init(keyStore);
			
			// Create an SSLContext that uses our TrustManager
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			sslcontext.init(null, tmf.getTrustManagers(), null);
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext, new DefaultHostnameVerifier());
			
			return ssf;			
		} 
		// If Trust Store is NOT loaded, by pass certificate checking
		else {
			log.info("Disable SSL Certificate Validation");
			
			SSLContext sslcontext = SSLContext.getInstance("TLS");
			TrustManager[] trustAllCerts = new TrustManager[] {
					new X509TrustManager() {
						@Override
						public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						}
						@Override
						public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
						}
						@Override
						public X509Certificate[] getAcceptedIssuers() {
							return null;
						}
					} 
			};
			sslcontext.init(null, trustAllCerts, new SecureRandom());
			SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
			
			return ssf;			
		}				
		
	}
	
	private class Configuration {
		
		private int wsSocketTimeout;
		private int wsConnectTimeout;
		private int wsConnectionRequestTimeout;
		
		private boolean enableSSLCertValidation;
		private boolean loadPrivateKeys;
		private String loadPrivateKeysPath;
		private boolean loadJavaDefaultKeys;
		private String loadJavaDefaultKeysPassword;
		
		private boolean enableProxy;
		private HttpHost proxyHost;
		
		private boolean enableProxyCredentials;
		private CredentialsProvider proxyCredProvider;
		
		public Configuration(String filePath) throws IOException {
			Properties properties = new Properties();
			properties.load(new FileInputStream(filePath));
			
			wsSocketTimeout = Integer.parseInt(properties.getProperty("ws.socketTimeout"));
			wsConnectTimeout = Integer.parseInt(properties.getProperty("ws.connectTimeout"));
			wsConnectionRequestTimeout = Integer.parseInt(properties.getProperty("ws.connectionRequestTimeout"));
			
			enableSSLCertValidation = "TRUE".equalsIgnoreCase(properties.getProperty("ssl.certificate.validation.enable"));
			if(enableSSLCertValidation) {
				loadPrivateKeys = "TRUE".equalsIgnoreCase(properties.getProperty("key.store.load.private.keys"));
				loadPrivateKeysPath = properties.getProperty("key.store.load.private.keys.path", "");
				loadJavaDefaultKeys = "TRUE".equalsIgnoreCase(properties.getProperty("key.store.load.java.default.store"));
				loadJavaDefaultKeysPassword = properties.getProperty("key.store.load.java.default.store.password", "");
			}

			enableProxy = "TRUE".equalsIgnoreCase(properties.getProperty("proxy.enable"));
			if(enableProxy) {
				String proxyHostStr = properties.getProperty("proxy.host", "");
				int proxyPort = Integer.parseInt(properties.getProperty("proxy.port"));
				String proxyScheme = properties.getProperty("proxy.scheme", "");
				proxyHost = new HttpHost(proxyHostStr, proxyPort, proxyScheme);				
			}
			
			enableProxyCredentials = "TRUE".equalsIgnoreCase(properties.getProperty("proxy.credentials.enable"));
			if(enableProxyCredentials) {
				String proxyUsername = properties.getProperty("proxy.credentials.username", "");
				String proxyPassword = properties.getProperty("proxy.credentials.password", "");
				proxyCredProvider = new BasicCredentialsProvider();
				proxyCredProvider.setCredentials(
		                new AuthScope(proxyHost.getHostName(), proxyHost.getPort()),
		                new UsernamePasswordCredentials(proxyUsername, proxyPassword));
			}
		}

		public int getWsSocketTimeout() {
			return wsSocketTimeout;
		}

		public int getWsConnectTimeout() {
			return wsConnectTimeout;
		}

		public int getWsConnectionRequestTimeout() {
			return wsConnectionRequestTimeout;
		}

		public boolean isEnableSSLCertValidation() {
			return enableSSLCertValidation;
		}

		public boolean isLoadPrivateKeys() {
			return loadPrivateKeys;
		}

		public String getLoadPrivateKeysPath() {
			return loadPrivateKeysPath;
		}

		public boolean isLoadJavaDefaultKeys() {
			return loadJavaDefaultKeys;
		}

		public String getLoadJavaDefaultKeysPassword() {
			return loadJavaDefaultKeysPassword;
		}

		public boolean isEnableProxy() {
			return enableProxy;
		}

		public HttpHost getProxyHost() {
			return proxyHost;
		}

		public boolean isEnableProxyCredentials() {
			return enableProxyCredentials;
		}

		public CredentialsProvider getProxyCredProvider() {
			return proxyCredProvider;
		}
				
	}
	
}
