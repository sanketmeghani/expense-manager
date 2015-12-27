# Solution to alchemytech backend coding challenge

Goal
====
Produce a simple web-app backend to complement the supplied front-end code.

Tasks Completed
--------------
Following tasks have been completed in the give solution:

1. Saves expenses as entered to a database.
2. Retrieves them for display on the page. 
3. Add a new column to the table displaying the VAT amount for each expense.
4. Calculate the VAT client-side as the user enters a new expense, before they save the expense to the database.
4. Added a README to contain instructions on how to build and run the app.

APIs are exposed on /api/* path. For example to get stored expensed send a get request on http://localhost:8080/api/expenses

Platform
--------------
I have used Ubuntu 14.04 64 Bit for development and testing. Eclipse and Sublime text have been used as IDEs.

Technologies/Libraries Used
--------------

1. Java (1.8.0_25)
2. MySQL (5.5.46)
3. Dropwizard (0.9.1)
4. Flyway (3.2.1)
5. NodeJs (4.2.3)
6. npm (2.14.7)
7. jsonassert (1.3.0)

Instructions
--------------
Please follow these instructions for building and running the application.

1. Clone existing git repository to download the solution and change directory to `expense-manager`

`git clone https://github.com/sanketmeghani/expense-manager.git`
`cd expense-manager`

2. Edit **config/flyway-development.properties** (configuration file for flyway) to update following properties with database configuration

**flyway.user** - Database username
**flyway.password** - Database user password
**flyway.schemas** - Database schema name
**flyway.url** - MySQL JDBC URL

3. Edit **config/application-development.yml** (configuration file for dropwizard) to update following properties with database configuration

**database.user** - Database username
**database.password** - Database user password
**database.url** - MySQL JDBC URL with database schema name

4. Run following maven command to create database and schemas

`mvn compile flyway:migrate -Dflyway.configFile=config/flyway-development.properties`

7. Execute following maven command to run test cases

`mvn clean test`

8. Executed following maven command to build and package application as a fat executable jar

`mvn clean package`

9. Execute following command to deploy application on localhost

`java -jar target/expense-manager-1.0.jar server config/application-development.yml`

10. Server logs are available in **logs/application.log** and request logs are available in **logs/access.log**

11. Application could be accessed by visting [http://localhost:8080]


Note
--------------
I have updated **src/main/resources/assets/src/js/apps/codingtest/expenses/expenses-controller.js** and **src/main/resources/assets/src/js/apps/codingtest/partials/expenses-content.html**. Hence you may need to rebuild client bundle if deploying in other than development mode.

Follow these steps to rebuild client bundle.

`cd src/main/resources/assets`
`gulp build-dev` (You may need to insrall gulp, npm if it is not already installed. Execute `npm install gulp -g` and `npm install --dev` to install node modules dependencies before executing this command)
`npm install --dev` (You can skip this step if required node module dependencies are already installed)