package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
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
        client = prepareTestClient("mark.sutton@currencycloud.com", "48a3d8694aeb1132588a27d1a7dbbc4ef92dbebd83e51194a67a02f9b81f67d8", "0e144495abb487ebed089f7c7e0e0b9c");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_cancel_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCancelConversion() throws Exception {
        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("GBP");
        conversion.setSellCurrency("USD");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal(2750));
        conversion.setReason("re-mortgage fees");
        conversion.setTermAgreement(true);
        conversion = client.createConversion(conversion);

        assertThat(conversion.getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));

/*
        assertThat(conversion.getSettlementDate(), equalTo(parseDateTime("2015-05-06T14:00:00+00:00")));
        assertThat(conversion.getConversionDate(), equalTo(parseDateTime("2015-05-06T00:00:00+00:00")));
        assertThat(conversion.getShortReference(), equalTo("20150504-PGRNVJ"));
        assertThat(conversion.getCreatorContactId(), equalTo("c4d838e8-1625-44c6-a9fb-39bcb1fe353d"));
        assertThat(conversion.getAccountId(), equalTo("8ec3a69b-02d1-4f09-9a6b-6bd54a61b3a8"));
        assertThat(conversion.getCurrencyPair(), equalTo("GBPUSD"));
        assertThat(conversion.getStatus(), equalTo("awaiting_funds"));
        assertThat(conversion.getBuyCurrency(), equalTo("GBP"));
        assertThat(conversion.getSellCurrency(), equalTo("USD"));
        assertThat(conversion.getClientBuyAmount(), equalTo(new BigDecimal("1000.00")));
        assertThat(conversion.getClientSellAmount(), equalTo(new BigDecimal("1511.70")));
        assertThat(conversion.getFixedSide(), equalTo("buy"));
        assertThat(conversion.getMidMarketRate(), equalTo(new BigDecimal("1.5118")));
        assertThat(conversion.getCoreRate(), equalTo(new BigDecimal("1.5117")));
        assertThat(conversion.getPartnerRate(), nullValue());
        assertThat(conversion.getPartnerStatus(), equalTo("funds_arrived"));
        assertThat(conversion.getPartnerBuyAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(conversion.getPartnerSellAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(conversion.getClientRate(), equalTo(new BigDecimal("1.5117")));
        assertThat(conversion.getDepositRequired(), equalTo(false));
        assertThat(conversion.getDepositAmount(), equalTo(new BigDecimal("0.00")));
        assertThat(conversion.getDepositCurrency(), equalTo(""));
        assertThat(conversion.getDepositStatus(), equalTo("not_required"));
        assertThat(conversion.getDepositRequiredAt(), nullValue());
        assertThat(conversion.getPaymentIds(), empty());
        assertThat(conversion.getCreatedAt(), equalTo(parseDateTime("2015-05-04T20:28:29+00:00")));
        assertThat(conversion.getUpdatedAt(), equalTo(parseDateTime("2015-05-04T20:28:29+00:00")));

        Settlement settlement = client.createSettlement(Settlement.create());
        Settlement updatedSettlement = client.addConversion(settlement.getId(), conversion.getId());

        assertBasicPropertiesEqual(settlement, updatedSettlement);
        assertThat(updatedSettlement.getConversionIds(), equalTo(Collections.singletonList("24d2ee7f-c7a3-4181-979e-9c58dbace992")));
        Map<String, Settlement.Entry> entries = updatedSettlement.getEntries();
        assertThat(entries, not(anEmptyMap()));
        assertThat(entries, hasEntry("GBP", new Settlement.Entry(new BigDecimal("1000.00"), new BigDecimal("0.00"))));
        assertThat(entries, hasEntry("USD", new Settlement.Entry(new BigDecimal("0.00"), new BigDecimal("1511.70"))));
        assertThat(updatedSettlement.getUpdatedAt(), equalTo(parseDateTime("2015-05-04T20:40:56+00:00")));
*/

//        System.out.println("Conversion toString: " + updatedSettlement.toString());
    }

}