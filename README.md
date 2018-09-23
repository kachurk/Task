# Task (transfer money between accounts)

It's simple RESTful application which is implemeted using Java 8 and also h2, Blade and Ebean frameworks, enjoy :)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Installing and Deployment

You need to clone repository on your own env and execute following commans:

```
mvn clean install // build project and run tests
```

And Deployment

```
java -jar target/task.jar // server should be started
```
The server should be started on http://127.0.0.1:9000 and initial data should be loaded for accounts with id 1 and 2.

### API Resources
  - [GET /account/:id](#/account/:id)
  - [GET /transaction/:id](#/transaction/:id)
  - [POST /account](#/account)
  - [POST /transaction](#/transaction)

### POST /account
it creates a new account in in-memory database h2  Example: http://localhost:9000/account

##### Request
#
``` json
{
   "balance":100,
   "userName":"fisrtUser"
}
```
##### Response
#
``` json
{
  "id": 6,
  "userName": "fisrtUser",
  "balance": "100.00"
}
```

### POST /transation
It executes money transfer between two accounts. requestId is required filed to support idempotency of the request, for detailed information, please see the documentation for the class **_TransactionExecutor.java_**

Example: http://localhost:9000/transation
##### Request
#
``` json
{
   "requestId":"b6d7732e-5c9c-44ef-9fc7-b2474d72a338",
   "fromAccountId":1,
   "toAccountId":2,
   "amount":2
}
```
##### Response
#
``` json
{
  "requestId": "b6d7732e-5c9c-44ef-9fc7-b2474d72a338",
  "id": 3,
  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": "2.00"
}
```


## Authors

* **Konstain Kachur** - konstantin.kachur@gmail.com
