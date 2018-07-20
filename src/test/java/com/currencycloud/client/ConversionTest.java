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
import java.util.List;

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

        ConversionCancellationQuote cancelQuote = ConversionCancellationQuote.create();
        cancelQuote.setId(conversion.getId());
        ConversionCancellationQuote quote = client.quoteCancelConversion(cancelQuote);
        assertThat(quote.getCurrency(), equalTo("USD"));
        assertThat(quote.getAmount(), equalTo(new BigDecimal("-2.51")));
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

        ConversionCancellation cancelConversion = ConversionCancellation.create();
        cancelConversion.setId(conversion.getId());
        cancelConversion.setNotes("TEST CONVERSION CANCEL");
        cancelConversion = client.cancelConversion(cancelConversion);
        assertThat(cancelConversion.getConversionId(), equalTo(conversion.getId()));
        assertThat(cancelConversion.getAmount(), equalTo(new BigDecimal("100.00")));
        assertThat(cancelConversion.getCurrency(), equalTo("USD"));
        assertThat(cancelConversion.getNotes(), equalTo("TEST CONVERSION CANCEL"));
    }

    @Test
    @Betamax(tape = "can_quote_date_change", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanQuoteDateChange() throws Exception {
        ConversionDateChange conversionDateChange = ConversionDateChange.create(
                "24d2ee7f-c7a3-4181-979e-9c58dbace992",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse("2018-05-08T17:00:00+00:00")
        );
        ConversionDateChange conversionDateChangeQuote = client.quoteChangeDateConversion(conversionDateChange);
        assertThat(conversionDateChangeQuote.getAmount(), equalTo(new BigDecimal("-2.51")));
        assertThat(conversionDateChangeQuote.getCurrency(), equalTo("USD"));
        assertThat(conversionDateChangeQuote.getConversionId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(conversionDateChangeQuote.getOldConversionDate(), equalTo(parseDateTime("2018-04-13T09:15:30+00:00")));
        assertThat(conversionDateChangeQuote.getNewConversionDate(), equalTo(parseDateTime("2018-05-08T17:00:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_date_change", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanDateChange() throws Exception {
        ConversionDateChange conversionDateChange = ConversionDateChange.create(
                "24d2ee7f-c7a3-4181-979e-9c58dbace992",
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX").parse("2018-05-08T17:00:00+00:00")
        );
        conversionDateChange = client.changeDateConversion(conversionDateChange);
        assertThat(conversionDateChange.getAmount(), equalTo(new BigDecimal("-2.51")));
        assertThat(conversionDateChange.getCurrency(), equalTo("USD"));
        assertThat(conversionDateChange.getConversionId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(conversionDateChange.getOldConversionDate(), equalTo(parseDateTime("2018-04-13T09:15:30+00:00")));
        assertThat(conversionDateChange.getNewConversionDate(), equalTo(parseDateTime("2018-05-08T17:00:00+00:00")));
    }

    @Test
    @Betamax(tape = "can_date_change_history", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanDateChangeHistory() throws Exception {
        ConversionDateChangeDetails dateChange = ConversionDateChangeDetails.create("24d2ee7f-c7a3-4181-979e-9c58dbace992");
        ConversionDateChangeDetails conversionDateChangeDetails = client.changeDateDetailsConversion(dateChange);
        assertThat(conversionDateChangeDetails.getFloatingCurrency(), equalTo("USD"));
        assertThat(conversionDateChangeDetails.getTotalProfitAndLoss(), equalTo(new BigDecimal("-1.34")));
        assertThat(conversionDateChangeDetails.getInitialValueDate(), equalTo(parseDateTime("2018-05-09T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getCurrentValueDate(), equalTo(parseDateTime("2018-05-09T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getInitialDeliveryDate(), equalTo(parseDateTime("2018-05-10T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getCurrentDeliveryDate(), equalTo(parseDateTime("2018-05-11T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().size(), equalTo(2));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getRequestedValueDate(), equalTo(parseDateTime("2018-05-10T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getNewValueDate(), equalTo(parseDateTime("2018-05-11T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getNewDeliveryDate(), equalTo(parseDateTime("2018-05-11T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getProfitAndLoss(), equalTo(new BigDecimal("-0.32")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getType(), equalTo("self service date change"));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getStatus(), equalTo("trade_update_completed"));
        assertThat(conversionDateChangeDetails.getDateChanges().get(0).getAdminFee(), nullValue());
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getRequestedValueDate(), equalTo(parseDateTime("2018-05-10T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getNewValueDate(), equalTo(parseDateTime("2018-05-10T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getNewDeliveryDate(), equalTo(parseDateTime("2018-05-11T09:00:00+00:00")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getProfitAndLoss(), equalTo(new BigDecimal("-1.02")));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getType(), equalTo("self service date change"));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getStatus(), equalTo("trade_update_completed"));
        assertThat(conversionDateChangeDetails.getDateChanges().get(1).getAdminFee(), nullValue());
    }

    @Test
    @Betamax(tape = "can_preview_split", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanPreviewSplit() throws Exception {
        ConversionSplit conversion = ConversionSplit.create("24d2ee7f-c7a3-4181-979e-9c58dbace992", new BigDecimal("1500.00"));
        ConversionSplit splitPreview = client.previewSplitConversion(conversion);
        assertThat(splitPreview.getParentConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(splitPreview.getParentConversion().getShortReference(), equalTo("20180716-XMXMMS"));
        assertThat(splitPreview.getParentConversion().getSellAmount(), equalTo(new BigDecimal("2417.10")));
        assertThat(splitPreview.getParentConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(splitPreview.getParentConversion().getBuyAmount(), equalTo(new BigDecimal("3000.00")));
        assertThat(splitPreview.getParentConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(splitPreview.getParentConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(splitPreview.getParentConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(splitPreview.getParentConversion().getStatus(), equalTo("awaiting_funds"));
        assertThat(splitPreview.getChildConversion().getId(), equalTo("c8a323d8-7366-4bf3-b7c5-a6590e07eda3"));
        assertThat(splitPreview.getChildConversion().getShortReference(), equalTo("20180716-KWQYDK"));
        assertThat(splitPreview.getChildConversion().getSellAmount(), equalTo(new BigDecimal("1208.55")));
        assertThat(splitPreview.getChildConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(splitPreview.getChildConversion().getBuyAmount(), equalTo(new BigDecimal("1500.00")));
        assertThat(splitPreview.getChildConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(splitPreview.getChildConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(splitPreview.getChildConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(splitPreview.getChildConversion().getStatus(), equalTo("awaiting_funds"));
    }

    @Test
    @Betamax(tape = "can_split", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanSplit() throws Exception {
        ConversionSplit conversion = ConversionSplit.create("24d2ee7f-c7a3-4181-979e-9c58dbace992", new BigDecimal("1500.00"));
        ConversionSplit conversionSplit = client.splitConversion(conversion);
        assertThat(conversionSplit.getParentConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(conversionSplit.getParentConversion().getShortReference(), equalTo("20180716-XMXMMS"));
        assertThat(conversionSplit.getParentConversion().getSellAmount(), equalTo(new BigDecimal("2417.10")));
        assertThat(conversionSplit.getParentConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplit.getParentConversion().getBuyAmount(), equalTo(new BigDecimal("3000.00")));
        assertThat(conversionSplit.getParentConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplit.getParentConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplit.getParentConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplit.getParentConversion().getStatus(), equalTo("awaiting_funds"));
        assertThat(conversionSplit.getChildConversion().getId(), equalTo("c8a323d8-7366-4bf3-b7c5-a6590e07eda3"));
        assertThat(conversionSplit.getChildConversion().getShortReference(), equalTo("20180716-KWQYDK"));
        assertThat(conversionSplit.getChildConversion().getSellAmount(), equalTo(new BigDecimal("1208.55")));
        assertThat(conversionSplit.getChildConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplit.getChildConversion().getBuyAmount(), equalTo(new BigDecimal("1500.00")));
        assertThat(conversionSplit.getChildConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplit.getChildConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplit.getChildConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplit.getChildConversion().getStatus(), equalTo("awaiting_funds"));
    }

    @Test
    @Betamax(tape = "can_split_history", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanSplitDetails() throws Exception {
        ConversionSplitHistory conversion = ConversionSplitHistory.create("24d2ee7f-c7a3-4181-979e-9c58dbace992");
        ConversionSplitHistory conversionSplitHistory = client.historySplitConversion(conversion);
        assertThat(conversionSplitHistory.getParentConversion().getId(), equalTo("24d2ee7f-c7a3-4181-979e-9c58dbace992"));
        assertThat(conversionSplitHistory.getParentConversion().getShortReference(), equalTo("20180716-XMXMMS"));
        assertThat(conversionSplitHistory.getParentConversion().getSellAmount(), equalTo(new BigDecimal("2417.10")));
        assertThat(conversionSplitHistory.getParentConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplitHistory.getParentConversion().getBuyAmount(), equalTo(new BigDecimal("3000.00")));
        assertThat(conversionSplitHistory.getParentConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplitHistory.getParentConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplitHistory.getParentConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplitHistory.getParentConversion().getStatus(), equalTo("awaiting_funds"));
        assertThat(conversionSplitHistory.getOriginConversion().getId(), equalTo("9d7919b5-c72d-41e1-9745-d2d5dc35e338"));
        assertThat(conversionSplitHistory.getOriginConversion().getShortReference(), equalTo("20180626-YVRVTT"));
        assertThat(conversionSplitHistory.getOriginConversion().getSellAmount(), equalTo(new BigDecimal("3222.80")));
        assertThat(conversionSplitHistory.getOriginConversion().getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplitHistory.getOriginConversion().getBuyAmount(), equalTo(new BigDecimal("4000.00")));
        assertThat(conversionSplitHistory.getOriginConversion().getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplitHistory.getOriginConversion().getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplitHistory.getOriginConversion().getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplitHistory.getOriginConversion().getStatus(), equalTo("awaiting_funds"));
        assertThat(conversionSplitHistory.getChildConversions().size(), equalTo(2));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getId(), equalTo("c8a323d8-7366-4bf3-b7c5-a6590e07eda3"));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getShortReference(), equalTo("20180716-KWQYDK"));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getSellAmount(), equalTo(new BigDecimal("1208.55")));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getBuyAmount(), equalTo(new BigDecimal("1500.00")));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplitHistory.getChildConversions().get(0).getStatus(), equalTo("awaiting_funds"));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getId(), equalTo("615227c4-a955-4a6c-a415-68accc3ae47f"));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getShortReference(), equalTo("20180716-EARWAY"));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getSellAmount(), equalTo(new BigDecimal("1208.55")));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getSellCurrency(), equalTo("GBP"));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getBuyAmount(), equalTo(new BigDecimal("1500.00")));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getBuyCurrency(), equalTo("EUR"));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getSettlementDate(), equalTo(parseDateTime("2018-06-28T13:00:00+00:00")));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getConversionDate(), equalTo(parseDateTime("2018-06-28T00:00:00+00:00")));
        assertThat(conversionSplitHistory.getChildConversions().get(1).getStatus(), equalTo("awaiting_funds"));
    }

    @Test
    @Betamax(tape = "can_retrieve_profit_and_losses", match={MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveConversionProfitAndLosses() throws Exception {
        ConversionProfitAndLosses profitAndLossesData = client.retrieveProfitAndLossConversion(null, null);
        List<ConversionProfitAndLoss> profitAndLosses = profitAndLossesData.getConversionProfitAndLosses();
        Pagination pagination = profitAndLossesData.getPagination();
        assertThat(profitAndLosses, not(empty()));
        assertThat(profitAndLosses.size(), equalTo(pagination.getTotalEntries()));
        ConversionProfitAndLoss profitAndLoss = profitAndLosses.iterator().next();
        assertThat(profitAndLoss.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(profitAndLoss.getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(profitAndLoss.getEventAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(profitAndLoss.getEventContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(profitAndLoss.getConversionId(), equalTo("337342f6-b4e3-46d4-a934-bd7d6aa74585"));
        assertThat(profitAndLoss.getEventType(), equalTo("self_service_cancellation"));
        assertThat(profitAndLoss.getAmount(), equalTo(new BigDecimal("-2.00")));
        assertThat(profitAndLoss.getCurrency(), equalTo("GBP"));
        assertThat(profitAndLoss.getNotes(), emptyOrNullString());
        assertThat(profitAndLoss.getEventDateTime(), equalTo(parseDateTime("2018-02-19T17:30:37+00:00")));
        assertThat(pagination.getTotalEntries(), equalTo(3));
        assertThat(pagination.getTotalPages(), equalTo(1));
        assertThat(pagination.getCurrentPage(), equalTo(1));
        assertThat(pagination.getPerPage(), equalTo(25));
        assertThat(pagination.getPreviousPage(), equalTo(-1));
        assertThat(pagination.getNextPage(), equalTo(-1));
        assertThat(pagination.getOrder(), equalTo("event_date_time"));
        assertThat(pagination.getOrderAscDesc(), equalTo(Pagination.SortOrder.asc));
    }
}