# BrowserStack Demo TestNG <a href="https://www.browserstack.com/"><img src="https://www.vectorlogo.zone/logos/browserstack/browserstack-icon.svg" alt="BrowserStack" height="30"/></a> <a href="https://java.com"><img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="Java" height="30" /></a> <a href="https://www.selenium.dev/"><img src="https://seeklogo.com/images/S/selenium-logo-DB9103D7CF-seeklogo.com.png" alt="Selenium" height="30" /></a>

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

- To start a locally hosted website.
  ```
  mvn -P start-server compile exec:java
  ```
  Website will open on `localhost:8000`.

#### Web browsers

- Run a single test.
  ```
  mvn -P web-single test
  ```
- Run parallel tests.
  ```
  mvn -P web-parallel test
  ```
- Run a failed test.
  ```
  mvn -P web-fail test
  ```
- Run a local test.
  ```
  mvn -P web-local test
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
  mvn -P android-fail test
  ```
- Run a local test.
  ```
  mvn -P android-local test
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
  mvn -P ios-fail test
  ```
- Run a local test.
  ```
  mvn -P ios-local test
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

- To start a locally hosted website.
  ```
  ./gradlew run
  ```
  Website will open on `localhost:8000`.

#### Desktop browsers

- Run a single test
  ```
  ./gradlew clean desktop-single
  ```
- Run parallel tests
  ```
  ./gradlew clean desktop-parallel
  ```
- Run a failed test
  ```
  ./gradlew clean desktop-fail
  ```
- Run a local test
  ```
  ./gradlew clean desktop-local
  ```
- Run parallel local tests
  ```
  ./gradlew clean desktop-local-parallel
  ```

#### Mobile browsers

- Run a single test
  ```
  ./gradlew clean mobile-single
  ```
- Run parallel tests
  ```
  ./gradlew clean mobile-parallel
  ```
- Run a failed test
  ```
  ./gradlew clean mobile-fail
  ```
- Run a local test
  ```
  ./gradlew clean mobile-local
  ```
- Run parallel local tests
  ```
  ./gradlew clean mobile-local-parallel
  ```

### App-Automate

#### Android devices

- Run a single test
  ```
  ./gradlew clean android-single
  ```
- Run parallel tests
  ```
  ./gradlew clean android-parallel
  ```
- Run a failed test
  ```
  ./gradlew clean android-fail
  ```
- Run a local test
  ```
  ./gradlew clean android-local
  ```
- Run parallel local tests
  ```
  ./gradlew clean android-local-parallel
  ```

#### iOS devices

- Run a single test
  ```
  ./gradlew clean ios-single
  ```
- Run parallel tests
  ```
  ./gradlew clean ios-parallel
  ```
- Run a failed test
  ```
  ./gradlew clean ios-fail
  ```
- Run a local test
  ```
  ./gradlew clean ios-local
  ```
- Run parallel local tests
  ```
  ./gradlew clean ios-local-parallel
  ```

#### Espresso tests

- Run an Espresso test
  ```
  ./gradlew clean espresso
  ```
  
#### XCUI tests

- Run a XCUI test
  ```
  ./gradlew clean xcuitest
  ```

## Notes
- You can view your Automate test results on the [BrowserStack Automate dashboard](https://automate.browserstack.com/).
- You can view your App-Automate test results on the [BrowserStack App-Automate dashboard](https://app-automate.browserstack.com/).
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
  export BROWSERSTACK_USERNAME=<your-username> && export BROWSERSTACK_ACCESS_KEY=<your-access-key>
  ```
