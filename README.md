# RewardCalculator API
Calculates Reward Based on Transactions of each month
The Reward Calculator API is a RESTful web service that calculates reward points for a list of customer transactions. It allows you to get the monthly reward points for each customer, as well as the total reward points for each customer across all months.


## Data 

Dataset present in /rewardcalculator/src/main/resources/transactions.csv

![image](https://user-images.githubusercontent.com/112735598/221350185-df46fbec-44b6-4642-a2db-636aea0a8638.png)


![image](https://user-images.githubusercontent.com/112735598/221350118-6111370f-2a6a-4c87-8b3c-f592df12091f.png)


## Getting started
To get started with the Reward Calculator API, you will need to have Java 8 or higher installed. You can then clone the repository and run the application using the following commands:
```
git clone https://github.com/your-username/reward-calculator.git
```
```
cd reward-calculator
```
```
./mvnw spring-boot:run
```

Once the application is running, you can access the following endpoints:

/reward-points/monthly-reward: returns the monthly reward points for each customer
/reward-points/total: returns the total reward points for each customer across all months


## Request
This endpoint does not require any request parameters.

## Response
The response is a JSON object with the following structure:
```
{
    "<customer-name>": {
        "<month>": <reward-points>,
        "<month>": <reward-points>,
        ...
    },
    "<customer-name>": {
        "<month>": <reward-points>,
        "<month>": <reward-points>,
        ...
    },
    ...
}
```

Where <customer-name> is the name of a customer, <month> is a string in the format "YYYY-MM" representing the month, and <reward-points> is an integer representing the total reward points for the customer in the given month.

Example
## Request:
 
GET /reward-points/monthly-reward

## Response:
```
  {
    "Alice": {
        "2022-01": 90,
        "2022-02": 150
    },
    "Bob": {
        "2022-01": 50,
        "2022-03": 120
    }
}
```

GET /reward-points/total
Returns the total reward points for each customer across all months.

## Request
This endpoint does not require any request parameters.

## Response
The response is a JSON object with the following structure:
```
{
    "<customer-name>": <total-reward-points>,
    "<customer-name>": <total-reward-points>,
    ...
}
```
Where <customer-name> is the name of a customer, and <total-reward-points> is an integer representing the total reward points for the customer across all months.

Example
Request:

GET /reward-points/total

Response:
```
{
    "Alice": 240,
    "Bob": 170
}
```

## Invoking the API
You can invoke the Reward Calculator API using any HTTP client, such as curl or Postman. Here are some example requests:

## Get monthly reward points
```
GET http://localhost:8080/reward-points/monthly-reward
```

## Get total reward points
```
GET http://localhost:8080/reward-points/total
```
  
## Running Tests
  
To run the RewardPointsCalculatorTest class, you can use the following command:
```
  ./mvnw -Dtest=RewardPointsCalculatorTest test
```
By default, the tests will be run using an in-memory H2 database. If you want to run the tests using a different database or configuration, you can modify the application-test.yml file in the src/test/resources directory.
  
## Comments in code
The source code for the Reward Calculator API contains comments explaining how the code works. Please refer to the source code for more information.

