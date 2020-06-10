Welcome!

This project is written as a demo project to show Apache HTTP Components usage knowledge, how to create wrappers around it, and how to write API tests.

API endpoints are:
1. POST api.postcodes.io/postcodes
2. GET api.postcodes.io/postcodes?q=:postcode
3. GET api.postcodes.io/postcodes/:postcode

API documentation: http://postcodes.io/docs


## Main used plugins/frameworks:
1. Apache HTTP components
2. TestNG
3. Lombok
4. Allure


## Steps to run a project:
1. Pull project
2. Open project in any IDE
3. Run project TestNG file: apiTesting.xml

To load a report file, run next command:
`allure serve C:<path to allure-results folder>`
