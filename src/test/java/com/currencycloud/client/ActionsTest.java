package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import com.currencycloud.client.model.Beneficiary;
import org.junit.Test;
import org.testng.Assert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ActionsTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "can_validate_beneficiaries")
    public void testCanBeClosed() throws Exception {
        client.setAuthToken("bbdd421bdda373ea69670c9101fa9197");
        List<String> paymentTypes = Collections.singletonList("regular");
        Beneficiary beneficiary = client.validateBeneficiary(
                "GB", "GBP", null, "12345678", "sort_code", "123456",
                null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null,
                paymentTypes
        );
        Assert.assertEquals(beneficiary.getPaymentTypes(), paymentTypes);
        Assert.assertEquals(beneficiary.getBankCountry(), "GB");
        Assert.assertEquals(beneficiary.getBankName(), "HSBC BANK PLC");
        Assert.assertEquals(beneficiary.getCurrency(), "GBP");
        Assert.assertEquals(beneficiary.getAccountNumber(), "12345678");
        Assert.assertEquals(beneficiary.getRoutingCodeType1(), "sort_code");
        Assert.assertEquals(beneficiary.getBeneficiaryAddress(), Collections.emptyList());
        Assert.assertEquals(beneficiary.getRoutingCodeValue1(), "123456");
        Assert.assertEquals(beneficiary.getBankAddress(), Arrays.asList("5 Wimbledon Hill Rd", "Wimbledon", "London"));
        Assert.assertNull(beneficiary.getBankAccountType());
    }
}