# Testez-une-application-full-stack

# Yoga

## Start the project

Clone the project

> [git clone] (https://github.com/MarvinCrepin/Testez-une-application-full-stack/)

## Install API

> cd back

# Install dependencies:

> mvn clean install

# Run the server :

> mvn spring-boot:run

# Access to the API at http://localhost:8080.

# Generate the jacoco code coverage:

mvn clean test

## Front End

> cd front

# Install dependencies:

> npm install

Launch Front-end:

> npm run start;


### MySQL

SQL script for creating the schema is available `ressources/sql/script.sql`

By default the admin account is:
- login: yoga@studio.com
- password: test!1234

#### E2E

Launching e2e test:

> npm run e2e

Generate coverage report (you should launch e2e test before):

> npm run e2e:coverage

Report is available here :

> front/coverage/lcov-report/index.html

#### Unitary test

Launching test:

> npm run test
