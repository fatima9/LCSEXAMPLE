Export the project in any IDE as existing gradle project
In the terminal go to the project directory and build the project with ./gradlew build
Start the application in the IDE as spring boot app
In the postman, make a post call by importing the below curl

curl --location --request POST 'http://localhost:8080/lcs' \
--header 'Accept: application/json' \
--header 'Content-Type: application/json' \
--data-raw '{
"setOfStrings": [
{"value": "comcast"},
{"value": "comcastic"},
{"value": "broadcaster"}
]
}'

