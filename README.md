# Start
## Using command line
mvn clean install
java -jar atm-service/target/atm-service-0.0.1-SNAPSHOT.jar

## Using an IDE such as IntelliJ
right click atm-service/src/main/java/com.sailpoint.SailpointAtmApplication.java
Run

# API calls 
## Login
accessToken=$(curl -X POST -H "Content-Type: application/json" "http://localhost:8080/api/v1/atm/login" -d '{"pinNumber": "1234", "cardNumber": "0123456789123456"}')

## Deposit
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer $accessToken" "http://localhost:8080/api/v1/atm/deposit" -d '{"value": 1000}'

## View balance
curl -X GET -H "Authorization: Bearer $accessToken" "http://localhost:8080/api/v1/atm/balance"

## Withdraw
curl -X PUT -H "Content-Type: application/json" -H "Authorization: Bearer $accessToken" "http://localhost:8080/api/v1/atm/withdraw" -d '{"value": 100}'

# Todo
1. Transaction and concurrency
2. Logging
3. Docker image
4. Component test using cucumber
5. Unit test
6. Use failsafe plugin for integration test and surefire plugin for unit test