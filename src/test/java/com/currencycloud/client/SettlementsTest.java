package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Conversion;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "UnnecessaryBoxing"})
public class SettlementsTest extends BetamaxTestSupport {

//    private String token = "6f5f99d1b860fc47e8a186e3dce0d3f9";

    @Test
    @Betamax(tape = "can_add_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanAddConversion() throws Exception {
        Conversion conversion = client.createConversion(
                "GBP", "USD", "buy", new BigDecimal(1000), "mortgage payment", true,
                null, null, null, null, null);

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

//        settlement = CurrencyCloud::Settlement.create

//        updated_settlement = settlement.add_conversion(conversion.id)

//        assertThat(settlement, equalTo(updated_settlement));

//        assertThat(settlement.conversion_ids, equalTo( ["24d2ee7f-c7a3-4181-979e-9c58dbace992"])
//        assertThat(settlement.entries).to_not be_empty

//        gbp_currency = settlement.entries[0]
//        assertThat(gbp_currency).to include("GBP" => { "receive_amount" => "1000.00", "send_amount" => "0.00" })

//        usd_currency = settlement.entries[1]
//        assertThat(usd_currency).to include("USD" => { "receive_amount" => "0.00", "send_amount" => "1511.70" })

//        assertThat(settlement.updated_at, equalTo('2015-05-04T20:40:56+00:00')
    }


}