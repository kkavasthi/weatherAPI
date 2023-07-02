
# This will tell you about the next 3 days weather of your city.

This API will get user input for their city and will give json response about the weather information for next 3 days from today.


## Flow Diagram
![FlowDiagram.drawio(1).png](..%2F..%2FFlowDiagram.drawio%281%29.png)

#What you Will Need
IDE
JDK 1.8, 11
Maven 3.8 +

#Dependency
Used from Spring boot initalizer

#Executable jar
target/weather-0.0.1-SNAPSHOT.jar

#Deploy Process
First Create Docker image and push the image to Docker hub and than deploy the image to Kubernets

#References
docker login [ARTIFACTORY-REGISTRY-URL]

#GitHub
https://github.com/kkavasthi/weatherAPI

#Local Run
port:8080

#API EndPoint
http://localhost:8090/weather/v1/prediction/mumbai
#API Response
[
{
"date": "2023-07-03",
"highTemperature": 301.91,
"lowTemperature": 300.71,
"windSpeed": 7.38,
"thunderstorm": false,
"rain": true,
"action": [
"Use sunscreen lotion",
"Carry umbrella"
]
},
{
"date": "2023-07-04",
"highTemperature": 301.44,
"lowTemperature": 300.49,
"windSpeed": 6.86,
"thunderstorm": false,
"rain": true,
"action": [
"Use sunscreen lotion",
"Carry umbrella"
]
},
{
"date": "2023-07-05",
"highTemperature": 300.24,
"lowTemperature": 298.95,
"windSpeed": 6.84,
"thunderstorm": false,
"rain": true,
"action": [
"Use sunscreen lotion",
"Carry umbrella"
]
}
]