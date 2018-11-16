# Sensors data from the city of Aarhus, Danemark
The purpose of this work is to provide some data visualization for IOT sensors data.

source : **http://iot.ee.surrey.ac.uk:8080/datasets.html**
github page : **https://github.com/Vinspi/GD_UI**

All the application is bundled in the jar, you can easily start it by typing "java -jar app.jar" or execute it with java if you are on a Windows system.

Once launched, data are accessible through the UI at **http://localhost:8090**, you can click on a marker on the map to retrieve, parse and show data.

You can also choose a period then click the **update** button to see weather data (pressure, humidity, temperature and wind speed).

## The API endpoints are :

| API endpoint                    | Method | Request example                                            | Results                                                                                                                                  |
|---------------------------------|--------|------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------|
| http://localhost:8090/data      | POST   | {   limit: '100',   fields: ['lon','lat','name','value'] } | Query the data to retrieve the fields you ask for,  available fields are : 'lon', 'lat', 'name', 'value'.                                |
| http://localhost:8090/points    | GET    |                             ...                            | Query the data to retrieve all distinct geographical points.                                                                             |
| http://localhost:8090/getWindSpeed     | GET    | ?from=2014-08-14T01:00:00&to=2014-08-15T17:50:00           | Query the data to retrieve all measurement of wind speed between the two dates (from, to)                                                |
| http://localhost:8090/getHumidity      | GET    | ?from=2014-08-14T01:00:00&to=2014-08-15T17:50:00           | Query the data to retrieve all measurement of humidity between the two dates (from, to)                                                  |
| http://localhost:8090/getPressure      | GET    | ?from=2014-08-14T01:00:00&to=2014-08-15T17:50:00           | Query the data to retrieve all measurement of pressure between the two dates (from, to)                                                  |
| http://localhost:8090/getTemperature   | GET    | ?from=2014-08-14T01:00:00&to=2014-08-15T17:50:00           | Query the data to retrieve all measurement of temperature between the two dates (from, to)                                               |
| http://localhost:8090/pointData | GET    | ?lat=56.1527&lon=10.197                                    | QUery the data to retrieve all measurement for the geographical point at (lat,lon). Because of the size of the data results are limited. |
