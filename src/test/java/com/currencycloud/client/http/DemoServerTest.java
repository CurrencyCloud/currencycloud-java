package com.currencycloud.client.http;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.model.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

/**
 * This test executes actual http calls to the demo server and shouldn't be run when unit tests are run. Run when
 * needed only.
 */
@Ignore
public class DemoServerTest {

    private static final Logger log = LoggerFactory.getLogger(DemoServerTest.class);

    private CurrencyCloudClient currencyCloud = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo);

    @Before
    public void login() {
        currencyCloud.authenticate("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0");
    }

    /**
     * Test that payment types collection is handled correctly.
     * */
    @Test
    public void testPaymentTypes() throws Exception {
        try {
            Beneficiary beneficiary = Beneficiary.createForValidate("GB", "GBP", "GB");
            beneficiary.setBankAddress(Arrays.asList("Trafalgar Square", "London", "UK"));
            beneficiary.setBankName("Acme Bank");
            beneficiary.setPaymentTypes(Arrays.asList("priority", "regular"));
            currencyCloud.validateBeneficiary(beneficiary);
            assertThat("Should fail.", false);
        } catch (ResponseException e) {
            log.info(e.toString());
            Map<String, List<ErrorMessage>> msgs = e.getErrorMessages();
            List<ErrorMessage> paymentTypesErrors = msgs.get("payment_types");
            if (paymentTypesErrors != null) {
                for (ErrorMessage paymentTypesError : paymentTypesErrors) {
                    if (Arrays.asList("payment_types_type_is_wrong", "payment_types_not_included_in_list")
                            .contains(paymentTypesError.getCode())) {
                        throw new AssertionError(paymentTypesError.getMessage());
                    }
                }
            }
        }
    }
    /** Test that payment types collection is handled correctly. */
    @Test
    public void testAddress() throws Exception {
        List<String> paymentTypes = Arrays.asList("priority", "regular");
        Beneficiary beneficiary = Beneficiary.createForCreate("John W Doe", "DE", "EUR", "John Doe");
        beneficiary.setBeneficiaryAddress(Collections.singletonList("Hamburg, GE"));
        beneficiary.setBeneficiaryCountry("DE");
        beneficiary.setBicSwift("COBADEFF");
        beneficiary.setIban("DE89370400440532013000");
        beneficiary.setBeneficiaryEntityType("individual");
        beneficiary.setBeneficiaryCompanyName("ACME Ltd.");
        beneficiary.setBeneficiaryFirstName("John");
        beneficiary.setBeneficiaryLastName("Doe");
        beneficiary.setBeneficiaryCity("Hamburg");
        beneficiary.setBankAddress(Arrays.asList("Trafalgar Square", "London", "UK"));
        beneficiary.setBankName("Acme Bank");
        beneficiary.setPaymentTypes(paymentTypes);

        beneficiary = currencyCloud.createBeneficiary(beneficiary);

        assertThat(beneficiary.getPaymentTypes(), is(equalTo(paymentTypes)));
    }

    @Test
    public void testAccounts() throws Exception {
        Accounts accounts = currencyCloud.findAccounts(null, null);
        log.debug("Accounts = {}", accounts);

        Account account = currencyCloud.currentAccount();
        log.debug("Current account = {}", account);

        String myAccId = account.getId();
        account = currencyCloud.retrieveAccount(myAccId);
        assertThat(account.getId(), equalTo(myAccId));

        boolean found = false;
        for (Account a : accounts.getAccounts()) {
            if (Objects.equals(a.getId(), myAccId)) {
                found = true;
            }
        }
        assertThat("Current account not found among the ones listed.", found);

//        account.setCountry("SI");
//        account = currencyCloud.updateAccount(account); // todo: permission denied
//        log.debug("account = {}", account);
    }
}
