package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import co.freeside.betamax.TapeMode;
import com.currencycloud.client.model.Conversion;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ConversionTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(mode = TapeMode.READ_WRITE, tape = "can_cancel_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCancelConversion() throws Exception {
        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("GBP");
        conversion.setSellCurrency("USD");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal(1000));
        conversion.setReason("mortgage payment");
        conversion.setTermAgreement(true);
        conversion = client.createConversion(conversion);
        assertThat(conversion.getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));

//        client.cancelConversionm(conversion);
    }

}