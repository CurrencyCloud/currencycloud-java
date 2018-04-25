package com.currencycloud.examples;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.backoff.BackOff;
import com.currencycloud.client.backoff.BackOffResult;
import com.currencycloud.client.exception.CurrencyCloudException;
import com.currencycloud.client.model.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * This is a Java SDK implementation of the examples in the
 * <a href="https://connect.currencycloud.com/documentation/getting-started/cookbook">Currency Cloud API v2.0 Cookbook</a>.
 * All API calls are wrapped in try/catch blocks and executed using an exponential backoff-and-retry policy.
 *
 * The default parameters used are:
 * - BackOff.<T>builder().withMaxAttempts(7) - Maximum number of retries set to 7
 * - BackOff.<T>builder().withBase(125) - Minimum wait time in milliseconds set to 125
 * - BackOff.<T>builder().withCap (90000) - Maximum wait time in milliseconds set to 90000
 * - BackOff.<T>builder().withExceptionType(TooManyRequestsException.class) - ApiException type to retry on. All other
 * exceptions are passed up the call stack
 *
 * Please see BackOffTest.java for a comprehensive set of test cases
 */
public class CurrencyCloudCookbook {

    public static void main(String[] args) throws Exception {
        // Please provide your login id and api key here to run this example.
        runCookBook("development@currencycloud.com", "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef");
    }

    public static void runCookBook(String loginId, String apiKey) {
        /*
         * 1. Generate an authentication token. This authentication token will be used in all subsequent calls and
         * will expire after 30mins of inactivity after login. Token requests are limited to 10 calls/min. Individual
         * contacts will be locked out of the account after 4 unsuccessful login attempts.
         */
        CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, loginId, apiKey);
        try {
            final BackOffResult<Void> authenticateResult = BackOff.<Void>builder()
                    .withTask(() -> {
                        client.authenticate();
                        return null;
                    })
                    .execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 2. Get a quote for the requested currency based on the spread table of the currently logged in contact. If
         * delivery date is not supplied it will default to a deal which settles in 2 working days.
         */
        DetailedRate detailedRate = null;
        try {
            final BackOffResult<DetailedRate> detailedRateResult = BackOff.<DetailedRate>builder()
                    .withTask(() -> client.detailedRates(
                            "EUR",
                            "GBP",
                            "buy",
                            new BigDecimal("12345.67"),
                            null
                            )
                    )
                    .execute();
            detailedRate = detailedRateResult.data.orElse(null);
            System.out.println("Single Detailed Rate: " + detailedRate.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * 3. We are happy with the rate and now wish to create the conversion. A successful response means that the
         * currency conversion has been executed and the amount of sold funds need to arrive at Currencycloud by the
         * settlement_date. The funds will be available for payment after the conversion has settled on the conversion_date.
        */
        Conversion conversion = null;
        try {
            final BackOffResult<Conversion> conversionResult = BackOff.<Conversion>builder()
                    .withTask(() -> {
                        Conversion conversionTemp = Conversion.create();
                        conversionTemp.setBuyCurrency("EUR");
                        conversionTemp.setSellCurrency("GBP");
                        conversionTemp.setFixedSide("buy");
                        conversionTemp.setAmount(new BigDecimal("12345.67"));
                        conversionTemp.setReason("Invoice Payment");
                        conversionTemp.setTermAgreement(true);
                        return client.createConversion(conversionTemp);
                    })
                    .execute();
            conversion = conversionResult.data.orElse(null);
        } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }

        System.out.println(conversion.toString());

        /*
         * 4. Create a new beneficiary. Some of the optional parameters may be required depending on the currency and
         * the country of the beneficiary and beneficiary bank. Please use the /reference/beneficiary_required_details
         * call to know which fields would be required.
         */
        List<Map<String, String>> beneficiaryRequiredDetails = null;
        try {
            final BackOffResult<List<Map<String, String>>> beneficiaryRequiredDetailsResult = BackOff.<List<Map<String, String>>>builder()
                    .withTask(() -> client.beneficiaryRequiredDetails("EUR", "IT", "IT"))
                    .execute();
            beneficiaryRequiredDetails = beneficiaryRequiredDetailsResult.data.orElse(null);
        } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }

        System.out.println(beneficiaryRequiredDetails.toString());

        /*
         * We know the IBAN and BIC/SWIFT numbers for the beneficiary, so we can use these details.
         */

        Beneficiary beneficiary = null;
        try {
            final BackOffResult<Beneficiary> beneficiaryResult = BackOff.<Beneficiary>builder()
                    .withTask(() -> {
                        Beneficiary beneficiaryObj = Beneficiary.create("Antica Salumeria Pane 1864", "IT", "EUR", "Fortunato Pane");
                        beneficiaryObj.setBeneficiaryCountry("IT");
                        beneficiaryObj.setBicSwift("BKRAITMM");
                        beneficiaryObj.setIban("IT40L2798279187CC4WJAU999QH");
                        List<String> beneficiaryAddress = new ArrayList<String>();
                        beneficiaryAddress.add("Via Luigi Settembrini n° 111");
                        beneficiaryAddress.add("80138, Naples, Italy");
                        beneficiaryObj.setBeneficiaryAddress(beneficiaryAddress);
                        return client.createBeneficiary(beneficiaryObj);
                    })
                    .execute();
            beneficiary = beneficiaryResult.data.orElse(null);
        } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }

        System.out.println(beneficiary.toString());

        /*
         * Validate this beneficiary before we attempt a payment to avoid payment failures.
         */

        final Beneficiary beneficiaryTemp = beneficiary;
        try {
            final BackOffResult<Void> validateBeneficiaryResult = BackOff.<Void>builder()
                    .withTask(() -> {
                        System.out.println(client.validateBeneficiary(beneficiaryTemp).toString());
                        return null;
                    })
                    .execute();
            } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }

        /*
         * 5. Provide details of the Payer and Pay
         */

        Payer payer = null;
        try {
            final BackOffResult<Payer> payerResult = BackOff.<Payer>builder()
                    .withTask(() -> {
                        List<String> payerAddress = new ArrayList<String>();
                        payerAddress.add("12 Steward St");
                        payerAddress.add("London E1 6FQ");
                        return Payer.create(
                                "individual",
                                "Currencycloud Ltd.",
                                "Guido",
                                "Bianco",
                                payerAddress,
                                "London",
                                "GB",
                                new Date()
                        );
                    })
                    .execute();
            payer = payerResult.data.orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Finally, we create a payment to send the funds to the beneficiary. Currencycloud will execute the payment
         * when the related conversion settles.
         */

        final Payer payerTemp = payer;
        final Payment paymentTemp = Payment.create(
                "EUR",
                beneficiary.getId(),
                new BigDecimal("12345.67"),
                "Invoice Payment",
                "Invoice 1234",
                null,
                "regular",
                conversion.getId(),
                null
        );

        Payment payment = null;
        try {
            final BackOffResult<Payment> paymentResult = BackOff.<Payment>builder()
                    .withTask(() -> client.createPayment(paymentTemp, payerTemp))
                    .execute();
            payment = paymentResult.data.orElse(null);
        } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }

        System.out.println(payment.toString());

        /*
         * 6. All sessions must come to an end, either manually using this call, or the session will automatically
         * timeout after 30 minutes of inactivity. If the session is no longer required, it is best practice to close
         * the session rather than leaving it to time-out. A successful response will return a 200 code with a blank body.
         */
        try {
            final BackOffResult<Void> endSessionResult = BackOff.<Void>builder()
                    .withTask(() -> {
                        client.endSession();
                        return null;
                    })
                    .execute();
        } catch (CurrencyCloudException e) {
            e.printStackTrace();
        }
    }
}
