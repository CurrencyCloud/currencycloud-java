[![Build Status](https://travis-ci.com/CurrencyCloud/currencycloud-java.svg?branch=master)](https://travis-ci.com/github/CurrencyCloud/currencycloud-java)
[![Maven Central](https://img.shields.io/maven-central/v/com.currencycloud.currencycloud-java/currencycloud-java.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.currencycloud.currencycloud-java%22%20AND%20a:%22currencycloud-java%22)
# Currencycloud API v2 Java client
## Version: 5.4.0
This is the official Java SDK for the Currencycloud API. Additional documentation for each API endpoint can be found at [developer.currencycloud.com][docs].

If you have any queries please contact our development team at development@currencycloud.com Please quote your login Id in any correspondence as this allows us to locate your account and give you the support you need.

## Prerequisites
### 1. Maven (optional, but highly recommended)
CurrencyCloud-Java is a Maven project. We highly recommend using [Apache Maven][maven] 3 (or a compatible build tool like Gradle) 
to build your project. While using Maven is not strictly required 
it will simplify building the project and handling dependencies.

### 2. Oracle JDK 8 or 11 (or equivalent JDK)
CurrencyCloud-Java requires at least a Java version 8 compatible JDK.
Note that it does not fully support JDK 17, however, 17 may be used by applying the workaround described in this [issue](https://github.com/CurrencyCloud/currencycloud-java/issues/148)

### 3. A valid sandbox login id and api key on the Currencycloud sandbox API environment.
You can register for a demo API key at [developer.currencycloud.com][developer].

While we expose certain routes on the sandbox API without the requirement for authentication, we rate-limit these requests significantly to prevent abuse. Rate-limiting on authenticated requests is less restrictive.

## Installing the Currencycloud SDK
### 1. Using Maven
To use the Currencycloud SDK in a Maven project, add the following dependency to your project's `pom.xml`:
```xml
<dependency>
    <groupId>com.currencycloud.currencycloud-java</groupId>
    <artifactId>currencycloud-java</artifactId>
    <version>5.4.0</version>
</dependency>
```
### 2. Using Gradle
To use the Currencycloud SDK in a Gradle project, add the following dependency to your project's `build.gradle`:
```Groovy
repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {

    implementation 'com.currencycloud.currencycloud-java:currencycloud-java:5.4.0'
}
```

### 3. Manually downloading the jars
Download the Currencycloud SDK jar:
1. Open https://oss.sonatype.org/#nexus-search;quick~currencycloud-java
2. Navigate to the version of currencycloud-java that you wish to use
3. Download the currencycloud-java-5.4.0.jar

Get the list of all dependencies:
```Shell
mvn dependency:list -DincludeScope=runtime
```
As of version 2.0.0, this returns the following list:
```
com.google.code.findbugs:jsr305:jar:3.0.2:compile
com.fasterxml.jackson.core:jackson-annotations:jar:2.13.1:compile
com.fasterxml.jackson.core:jackson-core:jar:2.13.1:compile
org.slf4j:slf4j-api:jar:1.7.32:compile
oauth.signpost:signpost-core:jar:1.2.1.2:compile
ch.qos.logback:logback-core:jar:1.2.10:compile
cglib:cglib:jar:3.3.0:compile
commons-codec:commons-codec:jar:1.3:compile
com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:jar:2.13.1:compile
com.fasterxml.jackson.core:jackson-databind:jar:2.9.1:compile
ch.qos.logback:logback-classic:jar:1.2.10:compile
com.github.mmazi:rescu:jar:2.0.2:compile
javax.ws.rs:javax.ws.rs-api:jar:2.1.1:compile
org.ow2.asm:asm:jar:9.2:compile
javax.ws.rs:jsr311-api:jar:1.1.1:compile
```
You will need to find each of these dependencies and download it from the [Sonatype Nexus][sonatype] as described above.

Finally, include all downloaded jars in your project's classpath.

# Usage
An example in Java 8 (Java 8 syntax is only used to format the output):
```Java
// Create API proxy; specifiy the environment to connect to and your API credentials.
CurrencyCloudClient currencyCloud = new CurrencyCloudClient(
    CurrencyCloudClient.Environment.demo, "<your login id>", "<your API key>"
);

// Make API calls
List<Currency> currencies = currencyCloud.currencies();
System.out.println("Supported currencies: " + currencies.stream().map(Currency::getCode).collect(Collectors.joining(", ")));

List<Balance> balances = currencyCloud.findBalances(null, null).getBalances();
System.out.println("Balances: " + balances.stream()
        .map((b) -> String.format("%s %s", b.getCurrency(), b.getAmount()))
        .collect(Collectors.joining(", ")));

// End session
currencyCloud.endSession();
```
In order to customise the HTTP client use the HTTP client configuration builder e.g.,
```Java
CurrencyCloudClient currencyCloud = new CurrencyCloudClient(
    CurrencyCloudClient.Environment.demo, 
    "<your login id>",
    "<your API key>",
    CurrencyCloudClient.HttpClientConfiguration.builder()
        .httpConnTimeout(3000)
        .httpReadTimeout(45000)
        .build()
);
```
For a better example, see
[CurrencyCloudCookbook.java](/src/test/java/com/currencycloud/examples/CurrencyCloudCookbook.java), which is an implementation of [the Cookbook](https://connect.currencycloud.com/documentation/getting-started/cookbook) from the documentation.

## Common Misconceptions, Anti-patterns and Suggestions
1. Avoid creating one client per request
2. Sessions typically have a timeout of several tens of minutes; this is to allow customers to reuse existing sessions for as long as possible
3. Write your application so that it establishes a single instance of the CurrencyCloudClient class on startup and shares this between threads, keeping it alive until you shut the application down. This will translate into fewer requests on your part and less server load on ours
4. Requests over the internet will fail on occasion for seemingly no apparent reason, and the SDK includes a comprehensive set of [error handling capabilities](#errors) to help troubleshoot those situations. Sometimes however, the best strategy is simply to retry. This is the case particularly with transient errors like **HTTP 429 - Too Many Requests** but wrapping calls in for/while loops is discouraged as in some extreme cases this may trigger our anti-DoS defences.  As of version 1.2.3 we have introduced an [Exponential Backoff with Jitter][ebwj] retry feature which we recommend you use to safely handle retries. Please see CurrencyCloudCookbook.java and BackOffTest.java for examples
5. API calls can throw exceptions. Try/Catch blocks are your friend. Use them generously

## On Behalf Of
If you want to make calls on behalf of another user (e.g. someone who has a sub-account with you), you can execute certain commands 'on behalf of' the user's contact id. Here is an example:
```Java
currencyCloud.onBehalfOfDo("c6ece846-6df1-461d-acaa-b42a6aa74045", new Runnable() {
    public void run() {
        currencyCloud.createBeneficiary(...);
        currencyCloud.createConversion(...);
        currencyCloud.createPayment(...);
    }
});
```
Or in Java 8 and above:
```Java
currencyCloud.onBehalfOfDo("c6ece846-6df1-461d-acaa-b42a6aa74045", () -> {
    currencyCloud.createBeneficiary(...);
    currencyCloud.createConversion(...);
    currencyCloud.createPayment(...);
});
```
Each of the above transactions will be executed in scope of the limits for that contact and linked to that contact. Note that the real user who executed the transaction will also be stored.

## Concurrency and Sessions 
The SDK is thread-safe, and you should share one session across all your worker threads to avoid reaching the limit on authentication requests.

## Errors
When an error occurs in the API, the library is designed to provide as much information as possible. A `CurrencyCloudException` will then be thrown which contains useful information you can access via its methods (please consult the javadoc for more information).

When the exception is logged, it will provide information such as the following:
```yaml
---
exception_type: "BadRequestException"
platform: Java 1.8.0_131 (Oracle Corporation)
request:
  parameters:
    login_id: non-existent-login-id
    api_key: ef0fd50fca1fb14c1fab3a8436b9ecb57528f0
  verb: post
  url: https://devapi.currencycloud.com/v2/authenticate/api
response:
  status_code: 400
  date: Wed, 29 Apr 2017 22:46:53 GMT
  request_id: 2775253392756800903
errors:
- field: api_key
  code: api_key_length_is_invalid
  message: api_key should be 64 character(s) long
  params:
    length: 64
```
This is split into 5 sections:

1. Exception Type: In this case `BadRequestException` represents an HTTP 400 error
2. Platform: The Java implementation that was used in the client
3. Request: Details about the HTTP request that was made, e.g. the POST parameters
4. Response: Details about the HTTP response that was returned, e.g. HTTP status code
5. Errors: A list of errors that provide additional information

The Errors section contains valuable information:

- Field: The parameter that the error is linked to
- Code: A code representing this error
- Message: A human readable message that explains the error
- Params: A map that contains dynamic parts of the error message for building custom error messages

When troubleshooting API calls with Currencycloud support, including the full
error in any correspondence will be **very** helpful.

## Logging
The SDK uses [slf4j](slf4j) for logging, wich means you are free to use any of the popular logging providers supported by slf4j in your project (eg. log4j, Logback, or Java Logging), but you must add your chosen logging provider to your project's dependencies yourself. We recommend [Logback][logback]:
```xml
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.1.3</version>
    <optional>true</optional>
</dependency>
```

# Development

## Building Currencycloud SDK from sources
To build the project from sources, you will need git and [Maven 3][maven].

In a shell, do the following:
```Shell
devuser@localhost ~ $ git clone https://github.com/CurrencyCloud/currencycloud-java.git     
devuser@localhost ~ $ cd currencycloud-java
devuser@localhost currencycloud-java $ mvn clean install
```

## Testing

Test cases can be run with `mvn test`

## Dependencies
```
co.freeside:betamax:jar:1.1.2:test
org.codehaus.groovy:groovy-all:jar:2.4.21:test
commons-lang:commons-lang:jar:2.4:test
org.eclipse.jetty:jetty-util:jar:7.3.1.v20110307:test
org.hamcrest:hamcrest-junit:jar:2.0.0.0:test
org.hamcrest:java-hamcrest:jar:2.0.0.0:test
org.eclipse.jetty:jetty-http:jar:7.3.1.v20110307:test
junit:junit:jar:4.13.2:test
commons-logging:commons-logging:jar:1.1.1:test
org.hamcrest:hamcrest-core:jar:1.3:test
org.eclipse.jetty:jetty-continuation:jar:7.3.1.v20110307:test
org.yaml:snakeyaml:jar:1.30:test
org.eclipse.jetty:jetty-server:jar:7.3.1.v20110307:test
org.eclipse.jetty:jetty-io:jar:7.3.1.v20110307:test
javax.servlet:servlet-api:jar:2.5:test
org.apache.httpcomponents:httpclient:jar:4.2.1:test
org.apache.httpcomponents:httpcore:jar:4.2.1:test
```

## Contributing
**We welcome pull requests from everyone!** Please see [CONTRIBUTING][contr]

Our sincere thanks for [helping us][hof] create the best API for moving money anywhere around the world!

## Versioning
This project uses [semantic versioning][semver]. You can safely
express a dependency on a major version and expect all minor and patch versions to be backwards compatible.

## Deprecation Policy
Technology evolves quickly and we are always looking for better ways to serve our customers. From time to time we need to make room for innovation by removing sections of code that are no longer necessary. We understand this can be disruptive and consequently we have designed a Deprecation Policy that protects our customers' investment and that allows us to take advantage of modern tools, frameworks and practices in developing software.

Deprecation means that we discourage the use of a feature, design or practice because it has been superseded or is no longer considered efficient or safe but instead of removing it immediately, we mark it as **@Deprecated** to provide backwards compatibility and time for you to update your projects. While the deprecated feature remains in the SDK for a period of time, we advise that you replace it with the recommended alternative which is explained in the relevant section of the code.

We remove deprecated features after **three months** from the time of announcement.

The security of our customers' assets is of paramount importance to us and sometimes we have to deprecate features because they may pose a security threat or because new, more secure, ways are available. On such occasions we reserve the right to set a different deprecation period which may range from **immediate removal** to the standard **three months**. 

Once a feature has been marked as deprecated, we no longer develop the code or implement bug fixes. We only do security fixes.

### List of features being deprecated
```
2021-07-06
- ibans/find (Already disabled in backend server)
- virtual_accounts/find (already disabled in backend server)
```

# Support
We actively support the latest version of the SDK. We support the immediate previous version on best-efforts basis. All other versions are no longer supported nor maintained.

# Release History
* [5.4.0](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-5.4.0)
  * Makes timeout on the HTTP Client configurable
  * Fixes the type of chargeAmount on PaymentTrackingInfo
  * Updates version of snakeyaml

* [5.3.0](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-5.3.0)
  * Adds on_behalf_of to funding_accounts/find

* [5.2.0](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-5.2.0)
  * Updates third party library dependencies 

* [5.1.2](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-5.1.2)
  * Adds estimatedArrival in Payment Object for responses to calls to retrievePayment
  * Adds optional uniqueRequestId to createTransfer calls
  * Adds uniqueRequestId in Transfer Response Object
  
* [5.0.9](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-5.0.9)
  * Deprecates and disables virtual_accounts/find. This call is no longer supported.
  * Deprecates and disables ibans/find. This call is no longer supported.
  * Adds on_behalf_of option to reference/conversion_dates.
  * Adds support for transfers/cancel endpoint.
  * Adds offline_conversion_dates to reference/conversion_dates response.
  * Adds next_day_conversion_date to reference/conversion_dates response.
  * Adds support for payments/validate endpoint.
  * Adds bank_account_verified parameter to Accounts Entity.
  * Adds bank_account_verified parameter to accounts/find call.
  * Adds terms_and_conditions_accepted to Accounts entity.
  * Adds terms_and_conditions_accepted to accounts/create calls.
   
* [4.1.3](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-4.1.3) 
  * Adds payments/payment_fees endpoint support
  * Adds payments/assign_payment_fee endpoint support
  * Adds payments/unassign_payment_fee endpoint support
  * Adds paymentFeeId and paymentFeeName to PaymentFeeRule response entity
  * Removes partner_status from conversion search parameter and response

* [4.0.2](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-4.0.2) 
  * Removes accountId and contactId from reports/report_requests/find
  * Adds firstConversionCutoffDatetime and optimizeLiquidityConversionDate to ConversionDates response
  * Removes all settlements endpoints after the retirement of this functionality
  * Removes purpose_code parameter from payments find
  
* [3.8.1](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-3.8.1) 
  * Adds payments tracking info endpoint
* [3.7.0](https://github.com/CurrencyCloud/currencycloud-java/releases/tag/currencycloud-java-3.7.0) 
  * Adds withdrawal account endpoints
  * Updates CurrencyCloudException toString to return correctly formatted yaml
* [3.6.1] - Adds conversion date preference parameter to conversion/create and rates/detailed
* [3.5.1] - Adds top-up margin balance endpoint
* [3.4.4] - Adds funding accounts endpoint
* [3.3.1] - Adds payment level fees apis
* [3.2.5] - Fixes /payments/authorise parameter name
* [3.2.2] - Add Reference Bank Details and Payment Delivery Date endpoints, remove deprecated IBAN and VAN endpoints, remove deprecated CurrencyCloudClient.createSettlement() method, update dependencies and add support for JDK 12
* [2.2.1] - Remove erroneous On Behalf Of parameter from conversions api endpoints. Adds beneficiary_external_reference to beneficiary create, update and find endpoints
* [2.1.3] - Add Accounts Payment Charges Settings endpoints. Adds Charge Type parameter to Payments Endpoints
* [2.0.0] - Remove deprecated methods, update dependencies and copyright
* [1.8.1] - Add Payment Confirmation and Sender Details
* [1.7.4] - Add Reporting paths and operations, remove json-smart dependency, change toString implementation, add missing Currency fields, update IBAN and VirtualAccounts, and deprecate obsolete IBAN and VirtualAccounts methods
* [1.6.1] - Add support for JDK 11
* [1.6.0] - Update Payment Purpose Code, add Payment tests, fix Javadoc warnings and update Maven plugins
* [1.5.1] - Add Payment Authorisation and Payment Purpose Code
* [1.4.4] - Add Conversion Quote Cancel, Conversion Cancel, Conversion Date Change Quote, Conversion Date Change, Conversion Date Change History, Conversion Split Preview, Conversion Split, Conversion Split History and Conversion Profit and Loss
* [1.3.0] - Add PaymentSubmission class and fix retrievePaymentSubmission
* [1.2.3] - Add VANs, add exponential backoff-and-retry, change toString in Currencycloud model classes to return RFC4627 compliant JSON, refactor ThreadSupport to private inner class and improve debug logging
* [1.0.3] - Update com.github.mmazi.rescu to latest version, desupport Java 7, add support for Java 9 and Java 10, fix bug in Beneficiary Date of Birth (#55), introduce builder pattern for *find* query parameters, deprecate tight-coupled methods and add production logging settings filtering out token and key  
* [0.9.1] - Add Transfers and IBANs, add missing API paths and operations (#42), update dependencies to newer versions, bug fixes (including #32 and #38), and other minor changes
* [0.7.8] - Address a concurrency issue discovered in the onBehalfOf functionality (#48) 

# Copyright
Copyright (c) 2015-2019 Currencycloud. See [LICENSE][license] for details.

[maven]:     https://maven.apache.org/index.html
[nexus]:     http://www.sonatype.org/nexus/
[slf4j]:     http://www.slf4j.org/
[logback]:   http://logback.qos.ch/
[rescu]:     https://github.com/mmazi/rescu
[jackson]:   https://github.com/FasterXML/jackson
[docs]:      https://connect.currencycloud.com/documentation/getting-started/introduction
[developer]: https://developer.currencycloud.com
[travis]:    https://travis-ci.org/CurrencyCloud/currencycloud-java
[semver]:    http://semver.org/
[sonatype]:  https://oss.sonatype.org/
[ebwj]:      https://aws.amazon.com/blogs/architecture/exponential-backoff-and-jitter/
[license]:   LICENSE.md
[contr]:     CONTRIBUTING.md
[hof]:       HALL_OF_FAME.md
