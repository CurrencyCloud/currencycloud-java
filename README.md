[![Build Status](https://travis-ci.org/CurrencyCloud/currencycloud-java.png?branch=master)](https://travis-ci.org/CurrencyCloud/currencycloud-java)

# Currency Cloud API v2 Java client

This is the official Java SDK for the Currency Cloud API. Additional documentation 
for each API endpoint can be found at [connect.currencycloud.com][connect]. If you have any queries or you require support, please contact our implementation team at implementation@currencycloud.com.

## Installation

To use `currencycloud-java` you currently need to get the source and build it yourself. The easiest way to do this
is using git and Maven:

```Shell
git clone git@github.com:CurrencyCloud/currencycloud-java.git
cd currencycloud-java
mvn clean install
```

Then include `target/currencycloud-java-*.jar` in your project's classpath, or include it using Maven:

```xml
<dependency>
    <groupId>com.currencycloud.currencycloud-java</groupId>
    <artifactId>currencycloud-java</artifactId>
    <version>0.7-SNAPSHOT</version>
</dependency>
```

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


## Errors

When an error occurs in the API, the library aims to give us much information
as possible. A `CurrencyCloudException` will be thrown that contains the HTTP response
code, error codes and details messages with parameters for each field/parameter
that caused an error. Please consult the javadocs for more information.

When troubleshooting API calls with Currency Cloud support, including the full
error in any correspondence can be very helpful.

## Logging

The SDK uses [slf4j](slf4j) for logging, wich means you are free to use any of the 
popular logging providers supported by slf4j in your project. We recommend using logback.


# Development

Test cases can be run with `mvn test`. 

## Dependencies
* [Rescu][rescu]

## Versioning

This project uses [semantic versioning][semver]. You can safely
express a dependency on a major version and expect all minor and patch versions
to be backwards compatible.

# Copyright

Copyright (c) 2015 Currency Cloud. See [LICENSE][license] for details.



[connect]:   https://connect.currencycloud.com/documentation/getting-started/introduction
[travis]:    https://travis-ci.org/CurrencyCloud/currencycloud-java
[rescu]:     https://github.com/mmazi/rescu
[semver]:    http://semver.org/
[slf4j]:     http://www.slf4j.org
[license]:   LICENSE.md
