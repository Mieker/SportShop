# Sport Shop Orders API

## Introduction
This service provides RESTful API endpoints for Sport Shop Orders to get, create and delete shop orders data on Mongo DB database.

## Getting Started
You will need jdk 17 and docker.
### Installation
1.	clone project from https://github.com/Mieker/SportShop.git
2. run docker
3. build application
      ```
      mvn clean install
      ```
4. run docker compose up -d

## Implementation and using cases

## Test
To build and test this service you need to run belows commands:
```
mvn test
```
Mongo DB will be populated automatically on first run of docker container.
Authorization for provided users:

login: Christina
password: 1234christina
role: ADMIN

login: John
password: JohnnyBGood
role: CUSTOMER

login: Laszlo
password: letMeIn!
role: CUSTOMER

## Swagger / OpenAPI
Swagger UI is available at `http://localhost:8050/api/orders/swagger` \
OpenAPI is available at `http://localhost:8050/api/orders/api-doc`

## Contribute
For any comments or questions please contact mieker@wp.pl