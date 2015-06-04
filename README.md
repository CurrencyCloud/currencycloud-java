[![Build Status](https://travis-ci.org/CurrencyCloud/currencycloud-java.png?branch=master)](https://travis-ci.org/CurrencyCloud/currencycloud-java)

# Currency Cloud API v2 Java client

## Version: 0.7.1-SNAPSHOT

This is the official Java SDK for the Currency Cloud API. Additional documentation 
for each API endpoint can be found at [connect.currencycloud.com][connect]. 

If you have any queries or you require support, please contact our implementation team at implementation@currencycloud.com.  Please quote your login id in any correspondence as this makes
it far simpler for us to locate your account and give you the support you need.

## Prerequisites

### 1. Maven

CurrencyCloud-Java is a Maven project.  You will require [Apache Maven][maven] 3 in order to build the SDK.  You can download the snapshots from [Sonatype Nexus][nexus] if you desire, but
it will be far easier to simply make use of maven to build the project and do the dependency management for you.

### 2. Oracle JDK 7 or equivalent JDK

CurrencyCloud-Java requires at least a Java version 7 compatible JDK.

### 3. A valid sandbox login id and api key on the CurrencyCloud sandbox API environment.

While we expose certain routes on the sandbox API without the requirement for authentication, we rate-limit these requests aggressively to prevent abuse of the sandbox.  Rate-limiting on authenticated requests
 is far more lenient.

## Installing the Currency Cloud SDK

### 1. Using Git and Maven

#### Steps

In a shell, do the following

```Shell
    devuser@localhost ~ $ git clone https://github.com/CurrencyCloud/currencycloud-java.git     
    devuser@localhost ~ $ cd currencycloud-java
    devuser@localhost currencycloud-java $ mvn clean install
```

Then add `target/currencycloud-java-*.jar` in your project's classpath, or include it by adding the following dependency to your project `pom.xml`:

```xml
<dependency>
    <groupId>com.currencycloud.currencycloud-java</groupId>
    <artifactId>currencycloud-java</artifactId>
    <version>0.7-SNAPSHOT</version>
</dependency>
```

### 2. Manually downloading the dependency

1. Open https://oss.sonatype.org/#nexus-search;quick~currencycloud-java
2. Navigate to the version of currencycloud-java that you wish to use
3. Download the currencycloud-java-0.7-SNAPSHOT.jar 

**Please note:**  This downloads **ONLY** the Currency Cloud SDK jar, and you will need to manually locate and download any required dependencies.  

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

Avoid creating one client per request.  Sessions have a timeout of several tens of minutes; this is specifically because we want customers to reuse existing sessions for as long as is feasible.
Try to write your application so that it establishes a single instance of the CurrencyCloudClient class on startup and shares this between threads, keeping it alive until you shut the application down.  

This will translate into fewer requests on your part and less server load on our part.

We rate-limit connections on the sandbox in order to encourage users to follow the above pattern.  

## On Behalf Of
If you want to make calls on behalf of another user (e.g. someone who has a sub-account with you), you 
can execute certain commands 'on behalf of' the user's contact id. Here is an example:

```Java
currencyCloud.onBehalfOfDo("c6ece846-6df1-461d-acaa-b42a6aa74045", new Runnable() {
    public void run() {
        currencyCloud.createBeneficiary(...);
        currencyCloud.createConversion(...);
        currencyCloud.createPayment(...);
    }
});
```

Or in Java 8:

```Java
currencyCloud.onBehalfOfDo("c6ece846-6df1-461d-acaa-b42a6aa74045", () -> {
    currencyCloud.createBeneficiary(...);
    currencyCloud.createConversion(...);
    currencyCloud.createPayment(...);
});

```

Each of the above transactions will be executed in scope of the limits for that contact and linked to that contact. Note
that the real user who executed the transaction will also be stored.

## Concurrency and Sessions 

The SDK is thread-safe, and ideally you should share one session across all your worker threads - this is because we specifically rate limit authentication requests.

## Errors
When an error occurs in the API, the library aims to give us much information
as possible. A `CurrencyCloudException` will be thrown that contains much useful information
that you can access via its methods (please consult the javadoc for more information).
When the exception is logged, it will provide information such as the following:

```yaml
BadRequestException
---
platform: Java 1.7.0_51 (Oracle Corporation)
request:
  parameters:
    login_id: non-existent-login-id
    api_key: ef0fd50fca1fb14c1fab3a8436b9ecb57528f0
  verb: post
  url: https://devapi.thecurrencycloud.com/v2/authenticate/api
response:
  status_code: 400
  date: Wed, 29 Apr 2015 22:46:53 GMT
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

When troubleshooting API calls with Currency Cloud support, including the full
error in any correspondence can be very helpful.

## Logging

The SDK uses [slf4j](slf4j) for logging, wich means you are free to use any of the 
popular logging providers supported by slf4j in your project. We recommend using logback.


# Development

Test cases can be run with `mvn test`. 

## Dependencies
* [Rescu][rescu]
* [SLF4J][slf4j]

## Versioning

This project uses [semantic versioning][semver]. You can safely
express a dependency on a major version and expect all minor and patch versions
to be backwards compatible.

# Copyright

Copyright (c) 2015 Currency Cloud. See [LICENSE][license] for details.



[maven]:     https://maven.apache.org/index.html
[nexus]:     http://www.sonatype.org/nexus/
[slf4j]:     http://www.slf4j.org/
[connect]:   https://connect.currencycloud.com/documentation/getting-started/introduction
[travis]:    https://travis-ci.org/CurrencyCloud/currencycloud-java
[rescu]:     https://github.com/mmazi/rescu
[semver]:    http://semver.org/
[slf4j]:     http://www.slf4j.org
[license]:   LICENSE.md
