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

    public static void main(String[] args) throws Exception {
        // Please provide your login id and api key here to run this example.
        runCookBook("development@currencycloud.com", "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
    }

    public static void runCookBook(String loginId, String apiKey) {
        /*
        1. Authenticate
        In order to access the API you first need to authenticate with your login ID and API key.
        */

        CurrencyCloudClient currencyCloud = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, loginId, apiKey);

        /*
        2. Get a Quote
        Before creating the conversion it is useful to get an indication of how many GBP you will have to sell
        to buy 12,345.67 EUR.
        */

        DetailedRate detailedRate = currencyCloud.detailedRates(
                "EUR",
                "GBP",
                "buy",
                new BigDecimal("12345.67"),
                null
        );

        System.out.printf(detailedRate.toString());

        /*
        3. Convert
        We are happy with the EURGBP rate indicated by Currencycloud and now wish to create the conversion
        */

        Conversion conversion = Conversion.create("EUR", "GBP", "buy");
        conversion = currencyCloud.createConversion( conversion, new BigDecimal("12345.67"), "Invoice Payment", true);

        System.out.printf(conversion.toString());

        /*
        A successful response means that the currency conversion has been executed and the amount of sold funds need to
        arrive at Currencycloud by the settlement_date. The bought funds will be available to pay after the conversion
        has settled on the conversion_date.
        */

        /*
        4. Add a Beneficiary
        We want to make a priority payment to a supplier based in Germany. To do this, we first need to check which
        details are required.
        */

        List<Map<String, String>> beneficiaryRequiredDetails =
                currencyCloud.beneficiaryRequiredDetails("EUR", "DE", "DE");

        System.out.printf(beneficiaryRequiredDetails.toString());

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

        System.out.printf(beneficiary.toString());

        /*
        Validate this beneficiary before we attempt a payment to avoid any payment failures.
         */

        System.out.println(currencyCloud.validateBeneficiary(beneficiary).toString());

        /*
        5. Provide details of the Payer and Pay
         */

        List<String> payerAddress = new ArrayList<String>();
        payerAddress.add("Payer Address Line 1");
        payerAddress.add("Payer Address Line 2");
        Payer payer = Payer.create(
                "individual",
                "A Company",
                "Troy",
                "McClure",
                payerAddress,
                "Paris",
                "FR",
                new Date()
        );

        /*
        Finally we want to create a payment to send the funds to the beneficiary.
        */

        Payment payment = Payment.create(
                "EUR",
                beneficiary.getId(),
                new BigDecimal("10000"),
                "Invoice Payment",
                "Invoice 1234",
                null,
                "regular",
                conversion.getId(),
                null
        );
        payment = currencyCloud.createPayment(payment, payer);

        System.out.printf(payment.toString());

        /*
        Currencycloud will make the payment when the related conversion settles.
        */
    }
}
