# BrowserStack Demo TestNG <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

| Product | Status |
| --- | --- |
| Automate | [![BrowserStack Status](https://automate.browserstack.com/badge.svg?badge_key=dHFiQ2xsZ1RDVUs4QXpYUmJFTEs3M1RiRVJTNGNnSHFGZGw3SnpFVXU2OD0tLTZacmdhanpLL01JaFJocUFGU3JJNkE9PQ==--f3ba05658718ec70bb69eee413997ddf1b8c2b0b)](https://automate.browserstack.com/public-build/dHFiQ2xsZ1RDVUs4QXpYUmJFTEs3M1RiRVJTNGNnSHFGZGw3SnpFVXU2OD0tLTZacmdhanpLL01JaFJocUFGU3JJNkE9PQ==--f3ba05658718ec70bb69eee413997ddf1b8c2b0b) |
| App-Automate | [![BrowserStack Status](https://app-automate.browserstack.com/badge.svg?badge_key=WHRFK0I3eXlzalBRYzRNU0lKajFCUnNTclFLcllHbWZoa0hSSWZ4ZzhnQT0tLXZ2UFozbTJTZXBEUnY2MVBaa0dZM0E9PQ==--7a67691ee07e1db565ef4902b0e260b2f3a5f963)](https://app-automate.browserstack.com/public-build/WHRFK0I3eXlzalBRYzRNU0lKajFCUnNTclFLcllHbWZoa0hSSWZ4ZzhnQT0tLXZ2UFozbTJTZXBEUnY2MVBaa0dZM0E9PQ==--7a67691ee07e1db565ef4902b0e260b2f3a5f963) |

Test execution using [TestNG](http://testng.org) on BrowserStack.

## Using Maven

### Setup

- Clone the repo
- Install dependencies
  ```
  mvn compile
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

### Automate

#### Desktop browsers

- Run a single test.
  ```
  mvn -P desktop-single test
  ```
- Run parallel tests.
  ```
  mvn -P desktop-parallel test
  ```
- Run a failed test.
  ```
  mvn -P desktop-fail test
  ```
- Run a local test.
  ```
  mvn -P desktop-local test
  ```
- Run parallel local tests.
  ```
  mvn -P desktop-local-parallel test
  ```

#### Mobile browsers

- Run a single test.
  ```
  mvn -P mobile-single test
  ```
- Run parallel tests.
  ```
  mvn -P mobile-parallel test
  ```
- Run a failed test.
  ```
  mvn -P mobile-fail test
  ```
- Run a local test.
  ```
  mvn -P mobile-local test
  ```
- Run parallel local tests.
  ```
  mvn -P mobile-local-parallel test
  ```

### App-Automate

#### Android devices

- Run a single test.
  ```
  mvn -P android-single test
  ```
- Run parallel tests.
  ```
  mvn -P android-parallel test
  ```
- Run a failed test.
  ```
  mvn -P android-failed test
  ```
- Run a local test.
  ```
  mvn -P android-local test
  ```
- Run parallel local tests.
  ```
  mvn -P android-local-parallel test
  ```

#### iOS devices

- Run a single test.
  ```
  mvn -P ios-single test
  ```
- Run parallel tests.
  ```
  mvn -P ios-parallel test
  ```
- Run a failed test.
  ```
  mvn -P ios-failed test
  ```
- Run a local test.
  ```
  mvn -P ios-local test
  ```
- Run parallel local tests.
  ```
  mvn -P ios-local-parallel test
  ```

#### Espresso tests

- Run an Espresso test.
  ```
  mvn -P espresso test
  ```

#### XCUI tests

- Run a XCUI test.
  ```
  mvn -P xcuitest test
  ```

## Using Gradle

### Setup

- Clone the repo
- Install dependencies 
  ```
  ./gradlew build
  ```
- Update the environment variables with your [BrowserStack Username and Access Key](https://www.browserstack.com/accounts/settings)

### Running your tests

### Automate

#### Desktop browsers

- Run a single test
  ```
  ./gradlew desktop-single
  ```
- Run parallel tests
  ```
  ./gradlew desktop-parallel
  ```
- Run a failed test
  ```
  ./gradlew desktop-fail
  ```
- Run a local test
  ```
  ./gradlew desktop-local
  ```
- Run parallel local tests
  ```
  ./gradlew desktop-local-parallel
  ```

#### Mobile browsers

- Run a single test
  ```
  ./gradlew mobile-single
  ```
- Run parallel tests
  ```
  ./gradlew mobile-parallel
  ```
- Run a failed test
  ```
  ./gradlew mobile-fail
  ```
- Run a local test
  ```
  ./gradlew mobile-local
  ```
- Run parallel local tests
  ```
  ./gradlew mobile-local-parallel
  ```

### App-Automate

#### Android devices

- Run a single test
  ```
  ./gradlew android-single
  ```
- Run parallel tests
  ```
  ./gradlew android-parallel
  ```
- Run a failed test
  ```
  ./gradlew android-fail
  ```
- Run a local test
  ```
  ./gradlew android-local
  ```
- Run parallel local tests
  ```
  ./gradlew android-local-parallel
  ```

#### iOS devices

- Run a single test
  ```
  ./gradlew ios-single
  ```
- Run parallel tests
  ```
  ./gradlew ios-parallel
  ```
- Run a failed test
  ```
  ./gradlew ios-fail
  ```
- Run a local test
  ```
  ./gradlew ios-local
  ```
- Run parallel local tests
  ```
  ./gradlew ios-local-parallel
  ```

#### Espresso tests

- Run an Espresso test
  ```
  ./gradlew espresso
  ```
  
#### XCUI tests

- Run a XCUI test
  ```
  ./gradlew ios-local-parallel
  ```

## Notes
- You can view your Automate test results on the [BrowserStack Automate dashboard](https://automate.browserstack.com/).
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
- To use specific number of threads:
  - For Maven use `-Dthreads=<thread-count>`. Example `-Dthreads=3`
  - For Gradle use `-Pthreads=<thread-count>`. Example `-Pthreads=3`
- Apps used to test on Android:
    - Demo App: [WikipediaSample.apk](https://www.browserstack.com/app-automate/sample-apps/android/WikipediaSample.apk)
    - Local App: [LocalSample.apk](https://www.browserstack.com/app-automate/sample-apps/android/LocalSample.apk)
- Apps used to test on iOS:
    - Demo App: [BStackSampleApp.ipa](https://www.browserstack.com/app-automate/sample-apps/ios/BStackSampleApp.ipa)
    - Local App: [LocalSample.ipa](https://www.browserstack.com/app-automate/sample-apps/ios/LocalSample.ipa)
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
