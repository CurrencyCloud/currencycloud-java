package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Conversion;
import com.currencycloud.client.model.Settlement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SettlementsTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_add_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanAddConversionToSettlement() throws Exception {
        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("GBP");
        conversion.setSellCurrency("USD");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal(1000));
        conversion.setReason("mortgage payment");
        conversion.setTermAgreement(true);
        conversion = client.createConversion(conversion);

        assertThat(conversion.getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
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

        System.out.println("Settlement toString: " + updatedSettlement.toString());
    }

    @Test
    @Betamax(tape = "can_remove_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRemoveConversionFromSettlement() throws Exception {
        Settlement settlement = client.retrieveSettlement("63eeef54-3531-4e65-827a-7d0f37503fcc");
        Settlement deletedSettlement = client.removeConversion(settlement.getId(), "24d2ee7f-c7a3-4181-979e-9c58dbace992");

        assertThat(deletedSettlement, not(nullValue()));
        assertThat(deletedSettlement.getType(), equalTo("bulk"));
        assertThat(deletedSettlement.getCreatedAt(), equalTo(parseDateTime("2015-05-04T20:29:16+00:00")));
        assertThat(deletedSettlement.getStatus(), equalTo("open"));
    }

    @Test
    @Betamax(tape = "can_release", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanReleaseSettlement() throws Exception {
        Settlement settlement = client.retrieveSettlement("51c619e0-0256-40ad-afba-ca4114b936f9");
        Settlement releasedSettlement = client.releaseSettlement(settlement.getId());

        assertBasicPropertiesEqual(settlement, releasedSettlement);
        assertThat(releasedSettlement.getReleasedAt(), equalTo(parseDateTime("2015-05-04T21:44:23+00:00")));
        assertThat(releasedSettlement.getStatus(), equalTo("released"));
    }

    @Test
    @Betamax(tape = "can_unrelease", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanUnreleaseSettlement() throws Exception {
        Settlement settlement = client.retrieveSettlement("51c619e0-0256-40ad-afba-ca4114b936f9");
        Settlement unreleaseSettlement = client.unreleaseSettlement(settlement.getId());

        assertBasicPropertiesEqual(settlement, unreleaseSettlement);
        assertThat(unreleaseSettlement.getReleasedAt(), nullValue());
        assertThat(unreleaseSettlement.getStatus(), equalTo("open"));
    }

    private static void assertBasicPropertiesEqual(Settlement settlement, Settlement updatedSettlement) {
        assertThat(settlement, not(equalTo(updatedSettlement)));
        assertThat(settlement.getId(), equalTo(updatedSettlement.getId()));
        assertThat(settlement.getCreatedAt(), equalTo(updatedSettlement.getCreatedAt()));
        assertThat(settlement.getShortReference(), equalTo(updatedSettlement.getShortReference()));
    }
}
