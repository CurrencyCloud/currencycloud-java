package com.currencycloud.examples;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.model.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * This is a Java SDK implementation of the example in the
 * <a href="https://connect.currencycloud.com/documentation/getting-started/cookbook">Currency Cloud API v2.0 Cookbook</a>.
 */
public class CurrencyCloudCookbook {

    public static void main(String... args) throws Exception {
        // Please provide your login id and api key here to run this example.
        runCookBook("apitester", "99b0d6895f95e46d9eaf5c85aa0f64dca9007b7ab0778721b6cdc0a8bc7c56cf");
    }

    public static void runCookBook(String loginId, String apiKey) {
        /*
        1. Authenticate
        In order to access the API you first need to authenticate with your login ID and API key.
        */

        CurrencyCloudClient currencyCloud = new CurrencyCloudClient(
                CurrencyCloudClient.Environment.demo,
                loginId, apiKey
        );

        /*
        2. Get a Quote
        Before creating the conversion it is useful to get an indication of how many GBP you will have to sell
        to buy 10,000 EUR.
        */

        DetailedRate detailedRate = currencyCloud.detailedRates("EUR", "GBP", "buy", new BigDecimal("10000.00"), null);

        System.out.printf("detailedRate: %s%n", detailedRate);

        /*
        3. Convert
        We are happy with the EURGBP rate indicated by Currency Cloud and now wish to create the conversion
        */

        Conversion conversion = Conversion.create("EUR", "GBP", "buy");
        conversion = currencyCloud.createConversion( conversion, new BigDecimal("10000.00"), "Invoice Payment", true);

        System.out.printf("conversion: %s%n", conversion);

        /*
        A successful response means that the currency conversion has been executed and the amount of sold funds need to
        arrive with Currency Cloud by the settlement_date. The bought funds will be available to pay after the
        conversion has settled on the conversion_date.
        */

        /*
        4. Add a Beneficiary

        We want to make a priority payment to a supplier based in Germany. To do this, we first need to check which
        details are required.
        */

        List<Map<String, String>> beneficiaryRequiredDetails =
                currencyCloud.beneficiaryRequiredDetails("EUR", "DE", null);

        System.out.printf("beneficiaryRequiredDetails: %s%n", beneficiaryRequiredDetails);

        /*
        We know the IBAN and BIC/SWIFT numbers for the beneficiary, so we can use these details.
        */

        Beneficiary beneficiary = Beneficiary.create("Acme GmbH", "DE", "EUR", "John Doe");
        beneficiary.setBeneficiaryCountry("DE");
        beneficiary.setBicSwift("COBADEFF");
        beneficiary.setIban("DE89370400440532013000");
        List<String> beneficiaryAddress = new ArrayList<String>();
        beneficiaryAddress.add("Acme building in Germany");
        beneficiaryAddress.add("Acme street in Germany");
        beneficiary.setBeneficiaryAddress(beneficiaryAddress);
        beneficiary = currencyCloud.createBeneficiary(beneficiary);

        System.out.printf("beneficiary: %s%n", beneficiary);

        /*
        Validate this beneficiary before we attempt a payment to avoid any payment failures.
         */

        currencyCloud.validateBeneficiary(beneficiary);

        /*
        5. Provide details of the Payer
         */
        List<String> payerAddress = new ArrayList<String>();
        payerAddress.add("Payer Address Line 1");
        payerAddress.add("Payer Address Line 2");
        Payer payer = Payer.create(
                "individual",
                "Test Payer Company",
                "Test Payer First Name",
                "Test Payer Last Name",
                payerAddress,
                "Paris",
                "FR",
                new Date());

        /*
        5. Pay

        Finally we want to create a payment to send the funds to the beneficiary.
        */

        Payment payment = Payment.create(
                "EUR", beneficiary.getId(), new BigDecimal("10000"), "Invoice Payment", "Invoice 1234",
                null, "regular", conversion.getId(), null
        );
        payment = currencyCloud.createPayment(payment, payer);

        System.out.printf("payment: %s%n", payment);

        /*
        Currency Cloud will make the payment when the related conversion settles.
        */
    }
}
