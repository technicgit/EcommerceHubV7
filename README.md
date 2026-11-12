# EcommerceHubV7

Selenium automation framework for testing the CloudBerry Store.

## Technologies

- Java
- Selenium WebDriver
- TestNG
- Maven
- Apache POI
- Log4j2
- Extent Reports
- Selenium Grid

## Test Coverage

- Application launch
- Login
- Login using Excel test data
- Add to cart
- Complete purchase
- Add to wishlist
- Affiliate registration

## Framework Features

- Page Object Model
- Parallel execution across Chrome, Edge, and Firefox
- Local and Selenium Grid execution
- Test groups
- Logging and failure screenshots
- HTML test reports

## Running Tests

Configure the execution environment in:

`src/test/resources/config.properties`

For remote execution, start Selenium Grid first.

From the project folder, run:

```bash
mvn test
```

## Known Issue

The purchase test has an intermittent checkout failure during parallel execution and is under investigation.
