# BrowserStack Demo TestNG <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

| Product | Status |
| --- | --- |
| Automate | [![BrowserStack Status](https://automate.browserstack.com/badge.svg?badge_key=VVJsYk9IdWRoSG5TQldSbTE4elZZenlTS0V3S3hpWVRxTG9qWGdQVW5BVT0tLUFyV29QSTMwa1NiUkVmVWFIeUFoM2c9PQ==--36aaf62782c041f259b2e865d0364319ebc0947a)](https://automate.browserstack.com/public-build/VVJsYk9IdWRoSG5TQldSbTE4elZZenlTS0V3S3hpWVRxTG9qWGdQVW5BVT0tLUFyV29QSTMwa1NiUkVmVWFIeUFoM2c9PQ==--36aaf62782c041f259b2e865d0364319ebc0947a) |
| App-Automate | [![BrowserStack Status](https://app-automate.browserstack.com/badge.svg?badge_key=czEwd3UxdlA0UkFuY0NpZ2h0eVI4V25qbTBwL1drbGg4My9rdUxneUdOYz0tLTJZT0NwVTlhVXVHbEROenhIamVFQlE9PQ==--e893df98cc09afe78a93a9b2ae4fb36a6495ac52)](https://app-automate.browserstack.com/public-build/czEwd3UxdlA0UkFuY0NpZ2h0eVI4V25qbTBwL1drbGg4My9rdUxneUdOYz0tLTJZT0NwVTlhVXVHbEROenhIamVFQlE9PQ==--e893df98cc09afe78a93a9b2ae4fb36a6495ac52) |

Test execution using [TestNG](http://testng.org) on BrowserStack.

## Using Maven

### Setup

- Clone the repo
- Install dependencies `mvn compile`
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

#### Automate

- To run a single test, run `mvn -P single test`
- To run a local test, run `mvn -P local test`
- To run parallel tests, run `mvn -P parallel test`
- To run parallel tests on mobile browsers, run `mvn -P mobile test`
- To run parallel local tests, run `mvn -P local-parallel test`
- To run a failed test, run `mvn -P fail test`

#### App-Automate

- To run a single test, run `mvn -P appium-single test`
- To run a local test, run `mvn -P appium-local test`
- To run parallel tests, run `mvn -P appium-parallel test`
- To run a failed test, run `mvn -P appium-fail test`
- To run an Espresso test, run `mvn -P espresso test`
- To run a XCUI test, run `mvn -P xcuitest test`

## Using Gradle

### Setup

* Clone the repo
* Install dependencies `./gradlew build`
* Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

#### Automate

- To run a single test, run `./gradlew single`
- To run a local test, run `./gradlew local`
- To run parallel tests, run `./gradlew parallel`
- To run parallel tests on mobile browsers, run `./gradlew mobile`
- To run parallel local tests, run `./gradlew local-parallel`
- To run a failed test, run `./gradlew fail`

#### App-Automate

- To run a single test, run `./gradlew appium-single`
- To run a local test, run `./gradlew appium-local`
- To run parallel tests, run `./gradlew appium-parallel`
- To run a failed test, run `./gradlew appium-fail`
- To run an Espresso test, run `./gradlew espresso`
- To run a XCUI test, run `./gradlew xcuitest`

## Notes
- You can view your Automate test results on the [BrowserStack Automate dashboard](https://automate.browserstack.com/).
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
- To start a locally hosted website, run `mvn -P start-server compile exec:java`. Website will open on `localhost:8000`.
- To use specific number of threads:
  - For Maven use `-Dthreads=<thread-count>`. Example `-Dthreads=3`
  - For Gradle use `-Pthreads=<thread-count>`. Example `-Pthreads=3`
- Apps used to test using Appium:
  - Demo App: [WikipediaSample.apk](https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk)
  - Local App: [LocalSample.apk](https://www.browserstack.com/app-automate/sample-apps/android/LocalSample.apk)
- Resources used to test using Espresso:
  - App: [Calculator.apk](https://www.browserstack.com/app-automate/sample-apps/android/Calculator.apk)
  - Test-suite: [CalculatorTest.apk](https://www.browserstack.com/app-automate/sample-apps/android/CalculatorTest.apk)
- Resources used to test using XCUITest:
  - App: [BrowserStack-SampleApp.ipa](https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleApp.ipa)
  - Test-suite: [BrowserStack-SampleXCUITest.zip](https://www.browserstack.com/app-automate/sample-apps/ios/BrowserStack-SampleXCUITest.zip)
- Export the environment variables for the Username and Access Key of your BrowserStack account.
  ```sh
  export BROWSERSTACK_USERNAME=<browserstack-username> && export BROWSERSTACK_ACCESS_KEY=<browserstack-access-key>
  ```
