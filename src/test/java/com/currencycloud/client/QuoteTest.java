package com.currencycloud.client;

import com.currencycloud.client.model.Quote;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class QuoteTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() {
        log.debug("------------------------- " + name.getMethodName() + " -------------------------");
    }

    @Test
    public void testCanCreateQuote() {
        Quote quote = Quote.create("EUR", "GBP", "buy", new BigDecimal("10000.00"), "30s");
        quote.setConversionDatePreference("default");
        Quote createdQuote = client.createQuote(quote);
        assertThat(createdQuote.getBuyCurrency(), equalTo("EUR"));
        assertThat(createdQuote.getSellCurrency(), equalTo("GBP"));
        assertThat(createdQuote.getFixedSide(), equalTo("buy"));
        assertThat(createdQuote.getClientBuyAmount(), equalTo(new BigDecimal("10000.00")));
        assertThat(createdQuote.getClientSellAmount(), equalTo(new BigDecimal("8059.00")));
        assertThat(createdQuote.getClientRate(), equalTo(new BigDecimal("0.8059")));
        assertThat(createdQuote.getCurrencyPair(), equalTo("EURGBP"));
        assertThat(createdQuote.getCreatedAt(), notNullValue());
        assertThat(createdQuote.getExpiresAt(), notNullValue());
    }
}
