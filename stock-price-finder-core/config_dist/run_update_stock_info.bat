echo off

cls

java -Xms128m -Xmx512m -jar lib/stock-price-finder-core-1.0.jar updateList

echo Finished.
pause