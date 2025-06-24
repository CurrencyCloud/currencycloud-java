package com.currencycloud.client;

import com.currencycloud.client.model.DetailedRate;
import com.currencycloud.client.model.Rate;
import com.currencycloud.client.model.Rates;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class RatesTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "242993ca94b9d1c6c1d8f7d3275a6f36");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testCanFindRates() {
        Rates rates = client.findRates(Arrays.asList("GBPUSD", "EURGBP"), null);

        assertThat(rates, not(nullValue()));
        Collection<String> currencies = rates.getCurrencyPairs();
        assertThat(currencies, not(empty()));

        assertThat(currencies, hasSize(2));

        for (String cp : currencies) {
            Rate b = rates.getRate(cp);
            assertThat(b, is(not(Matchers.nullValue())));
        }

        String cp = currencies.iterator().next();
        Rate rate = rates.getRate(cp);
        assertThat(cp, equalTo("EURGBP"));
        assertThat(rate.getBid(), equalTo(new BigDecimal("0.71445")));
        assertThat(rate.getOffer(), equalTo(new BigDecimal("0.71508")));

        assertThat(rates.getUnavailable(), empty());
    }

    @Test
    public void testCanProvidedDetailedRate() {
        DetailedRate detailedRate = client.detailedRates("GBP", "USD", "buy", new BigDecimal("10000"), null, null);

        assertThat(detailedRate.getClientSellAmount(), equalTo(new BigDecimal("15234.00")));
        assertThat(detailedRate.getSettlementCutOffTime(), equalTo(parseDateTime("2015-04-29T14:00:00Z")));
    }

    @Test
    public void testCanProvidedDetailedRateWithConversionDatePreference() {
        DetailedRate detailedRate = client.detailedRates("GBP", "USD", "buy", new BigDecimal("10000"), null, "optimize_liquidity");

        assertThat(detailedRate.getClientSellAmount(), equalTo(new BigDecimal("14081.00")));
        assertThat(detailedRate.getSettlementCutOffTime(), equalTo(parseDateTime("2020-05-21T14:00:00Z")));
    }

}
