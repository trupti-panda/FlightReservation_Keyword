Set projectLocation=C:\selenium\workspace\FlightReservation(Keyword)
cd %projectLocation%
Set classPath=%projectLocation%\bin;%projectLocation%\libs\*
java org.testng.TestNG testng.xml
pause