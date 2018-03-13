[![Build Status](https://travis-ci.org/CurrencyCloud/currencycloud-java.png?branch=master)](https://travis-ci.org/CurrencyCloud/currencycloud-java)

# Currencycloud API v2 Java client

## Version: 0.9.1

This is the official Java SDK for the Currencycloud API. Additional documentation for each API endpoint can be found at [developer.currencycloud.com][docs]

If you have any queries or you require support, please contact our development team at development@currencycloud.com Please quote your login id in any correspondence as this makes it easier for us to locate your account and give you the support you need.

## Prerequisites

### 1. Maven (optional, but highly recommended)

CurrencyCloud-Java is a Maven project. We highly recommend using [Apache Maven][maven] 3 (or a compatible build tool like Gradle) 
to build your project. While using Maven is not strictly required 
it will simplify building the project and handling dependencies.

### 2. Oracle JDK 7 or equivalent JDK

CurrencyCloud-Java requires at least a Java version 7 compatible JDK.

### 3. A valid sandbox login id and api key on the Currencycloud sandbox API environment.

You can register for a demo API key at [developer.currencycloud.com][developer]

While we expose certain routes on the sandbox API without the requirement for authentication, we rate-limit these requests significantly to prevent abuse. Rate-limiting on authenticated requests is less restrictive.

## Installing the Currencycloud SDK

### 1. Using Maven

To use the Currencycloud SDK in a Maven project, add the following dependency to your project's `pom.xml`:

```xml
<dependency>
    <groupId>com.currencycloud.currencycloud-java</groupId>
    <artifactId>currencycloud-java</artifactId>
    <version>0.9.1</version>
</dependency>
```

### 2. Manually downloading the jars

Download the Currencycloud SDK jar:

1. Open https://oss.sonatype.org/#nexus-search;quick~currencycloud-java
2. Navigate to the version of currencycloud-java that you wish to use
3. Download the currencycloud-java-0.9.1.jar 

Get the list of all dependencies:

```Shell
mvn dependency:list -DincludeScope=runtime
```

As of version 0.9.1, this returns the following list:

```
cglib:cglib:3.2.6:compile
ch.qos.logback:logback-classic:1.2.3:compile
ch.qos.logback:logback-core:1.2.3:compile
com.fasterxml.jackson.core:jackson-annotations:2.9.4:compile
com.fasterxml.jackson.core:jackson-core:2.9.4:compile
com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.9.4:compile
com.github.mmazi:rescu:2.0.1:compile
com.google.code.findbugs:jsr305:3.0.2:compile
javax.ws.rs:javax.ws.rs-api:2.1:compile
javax.ws.rs:jsr311-api:1.1.1:compile
org.ow2.asm:asm:6.0:compile
org.slf4j:slf4j-api:1.7.25:compile
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
List<Currency> currencies = currencyCloud.getCurrencies();
System.out.println("Supported currencies: " + currencies.stream().map(Currency::getCode).collect(Collectors.joining(", ")));

List<Balance> balances = currencyCloud.findBalances(null, null, null, null).getBalances();
System.out.println("Balances: " + balances.stream()
        .map((b) -> String.format("%s %s", b.getCurrency(), b.getAmount()))
        .collect(Collectors.joining(", ")));

// End session
currencyCloud.endSession();
```

For a slightly longer example, see
[CurrencyCloudCookbook.java](/src/test/java/com/currencycloud/examples/CurrencyCloudCookbook.java),
which is an implementation of [the Cookbook](https://connect.currencycloud.com/documentation/getting-started/cookbook) 
from the documentation.

## Common Misconceptions and Antipatterns

### 1. Creating a client for each request.

Avoid creating one client per request.  

Sessions typically have a timeout of several tens of minutes; this is to allow customers to reuse existing sessions for as long as possible.

Try to write your application so that it establishes a single instance of the CurrencyCloudClient class on startup and shares this between threads, 
keeping it alive until you shut the application down.  

This will translate into fewer requests on your part and less server load on ours.

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
BadRequestException
---
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

1. Error Type: In this case `BadRequestException` represents an HTTP 400 error
2. Platform: The Java implementation that was used in the client
3. Request: Details about the HTTP request that was made, e.g. the POST parameters
4. Response: Details about the HTTP response that was returned, e.g. HTTP status code
5. Errors: A list of errors that provide additional information

The final section contains valuable information:

- Field: The parameter that the error is linked to
- Code: A code representing this error
- Message: A human readable message that explains the error
- Params: A map that contains dynamic parts of the error message for building custom error messages

When troubleshooting API calls with Currencycloud support, including the full
error in any correspondence will be very helpful.

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

Test cases can be run with `mvn test`. 

## Dependencies

```
co.freeside:betamax:1.1.2:compile
junit:junit:4.12:compile
org.codehaus.groovy:groovy-all:2.4.13:compile
org.hamcrest:hamcrest-junit:2.0.0.0:compile
org.yaml:snakeyaml:1.19:compile
```

## Release History

* [0.9.1] - Add Transfers and IBANs, add missing API paths and operations (#42), update dependencies to latest versions, bug fixes (including #32 and #38), and other minor changes
* [0.7.8] - Address a concurrency issue discovered in the onBehalfOf functionality (#48) 

## Versioning

This project uses [semantic versioning][semver]. You can safely
express a dependency on a major version and expect all minor and patch versions to be backwards compatible.

#### Copyright

Copyright (c) 2015-2018 Currencycloud. See [LICENSE][license] for details.

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
[license]:   LICENSE.md

