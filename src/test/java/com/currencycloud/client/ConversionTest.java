package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import co.freeside.betamax.TapeMode;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
    @Betamax(tape = "can_quote_cancel", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanQuoteCancel() throws Exception {
        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("GBP");
        conversion.setSellCurrency("USD");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal(1000));
        conversion.setReason("mortgage payment");
        conversion.setTermAgreement(true);
        conversion = client.createConversion(conversion);
        assertThat(conversion.getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));

        ConversionCancellationQuote quote = client.cancellationQuote("24d2ee7f-c7a3-4181-979e-9c58dbace992");
        assertThat(quote.getFloatingCcy(), equalTo("USD"));
        assertThat(quote.getOverallProfitAndLoss(), equalTo("100.00"));
    }

    @Test
    @Betamax(mode = TapeMode.READ_ONLY, tape = "can_cancel_conversion", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
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

        ConversionProfitAndLoss loss = client.cancelConversion(conversion.getId(), "TEST");
        assertThat(loss.getConversionId(), equalTo(conversion.getId()));

    }

    @Test
    @Betamax(tape = "can_quote_date_change", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanQuoteDateChange() throws Exception {
        DateChange dc = client.dateChangeQuote("24d2ee7f-c7a3-4181-979e-9c58dbace992", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2018-05-08T17:00:00Z"));
        assertThat(dc.getProfitLoss(), equalTo("100.00"));
        assertThat(dc.getProfitLossCurrency(), equalTo("USD"));
    }

    @Test
    @Betamax(tape = "can_date_change", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanDateChange() throws Exception {
        DateChange dc = client.dateChange("24d2ee7f-c7a3-4181-979e-9c58dbace992", new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2018-05-08T17:00:00Z"));
        assertThat(dc.getProfitLoss(), equalTo("100.00"));
        assertThat(dc.getProfitLossCurrency(), equalTo("USD"));
    }

    @Test
    @Betamax(tape = "can_date_change_history", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanDateChangeHistory() throws Exception {
        DateChangeDetails det = client.dateChangeDetails("24d2ee7f-c7a3-4181-979e-9c58dbace992");
        assertThat(det.getFloatingCcy(), equalTo("USD"));
        assertThat(det.getChanges().size(), equalTo(2));
    }

    @Test
    @Betamax(tape = "can_quote_split", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanQuoteSplit() throws Exception {
        ConversionSplit split = client.conversionSplitPreview("24d2ee7f-c7a3-4181-979e-9c58dbace992", "500.00");
        assertThat(split.getParentConversion().getClientBuyAmt(), equalTo("500.00"));
        assertThat(split.getParentConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(split.getChildConversion().getClientBuyAmt(), equalTo("500.00"));
        assertThat(split.getChildConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace993"));
    }

    @Test
    @Betamax(tape = "can_split", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanSplit() throws Exception {
        ConversionSplit split = client.conversionSplit("24d2ee7f-c7a3-4181-979e-9c58dbace992", "500.00");
        assertThat(split.getParentConversion().getClientBuyAmt(), equalTo("500.00"));
        assertThat(split.getParentConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(split.getChildConversion().getClientBuyAmt(), equalTo("500.00"));
        assertThat(split.getChildConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace993"));
    }

    @Test
    @Betamax(tape = "can_split_history", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanSplitDetails() throws Exception {
        ConversionSplitHistory det = client.conversionSplitHistory("24d2ee7f-c7a3-4181-979e-9c58dbace992");

        assertThat(det.getOriginConversion().getDealRef(), equalTo("20150504-PGRNVJ"));
        assertThat(det.getParentConversion().getDealRef(), equalTo("20150504-PGRNVJ"));
        assertThat(det.getChildConversions().size(), equalTo(1));
        assertThat(det.getChildConversions().get(0).getDealRef(), equalTo("20150504-PGRNVK"));

    }
}