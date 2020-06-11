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
3. Download and Install two essential plugins (in Intellij go to File -> Settings -> Plugins):
    1) RoboPOJOGenerator
    2) Lombok
3. Run project TestNG file: apiTesting.xml


To run tests in Docker + Selenoid:
1 Download and install Docker
2. Open Powershell
3. Run next command: <cn location>.cn_windows_amd64.exe selenoid -p 8081:8080 start --vnc
4. Open localhost:8080/#/
5. Run tests in Docker

To load a report file, run next command:
`allure serve C:<path to allure-results folder>`
