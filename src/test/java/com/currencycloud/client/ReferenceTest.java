package com.currencycloud.client;

import com.currencycloud.client.model.BankDetails;
import com.currencycloud.client.model.ConversionDates;
import com.currencycloud.client.model.Currency;
import com.currencycloud.client.model.PayerRequiredDetail;
import com.currencycloud.client.model.PaymentDates;
import com.currencycloud.client.model.PaymentFeeRule;
import com.currencycloud.client.model.PaymentPurposeCode;
import com.currencycloud.client.model.SettlementAccount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anEmptyMap;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

public class ReferenceTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "6f5f99d1b860fc47e8a186e3dce0d3f9");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testCanRetrieveBeneficiaryRequiredDetails() {
        List<Map<String, String>> details = client.beneficiaryRequiredDetails("GBP", "GB", "GB");
        assertThat(details, not(empty()));

        Map<String, String> detail = details.iterator().next();
        assertThat(detail.get("payment_type"), equalTo("priority"));
        assertThat(detail.get("payment_type"), equalTo("priority"));
        assertThat(detail.get("beneficiary_entity_type"), equalTo("individual"));
        assertThat(detail.get("beneficiary_address"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_city"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_country"), equalTo("^[A-z]{2}$"));
        assertThat(detail.get("beneficiary_first_name"), equalTo("^.{1,255}"));
        assertThat(detail.get("beneficiary_last_name"), equalTo("^.{1,255}"));
        assertThat(detail.get("acct_number"), equalTo("^[0-9A-Z]{1,50}$"));
        assertThat(detail.get("sort_code"), equalTo("^\\d{6}$"));
    }

    @Test
    public void testCanRetrieveConversionDates() {
        ConversionDates dates = client.conversionDates("GBPUSD", null);

        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        assertThat(dates.getInvalidConversionDates().size(), equalTo(242));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2020-11-11")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("Veterans' Day"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getNextDayConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2020-11-12")));
        assertThat(dates.getFirstConversionCutoffDatetime(), equalTo(parseDateTime("2020-11-10T15:30:00+00:00")));
        assertThat(dates.getOptimizeLiquidityConversionDate(), equalTo(parseDate("2020-11-12")));
    }

    @Test
    public void testCanRetrieveConversionDatesWithDateFormat() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 1); // Year, Month (0-based), Day
        ConversionDates dates = client.conversionDates("GBPUSD", calendar.getTime());
        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        assertThat(dates.getInvalidConversionDates().size(), equalTo(242));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2020-11-11")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("Veterans' Day"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getNextDayConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2020-11-12")));
        assertThat(dates.getFirstConversionCutoffDatetime(), equalTo(parseDateTime("2020-11-10T15:30:00+00:00")));
        assertThat(dates.getOptimizeLiquidityConversionDate(), equalTo(parseDate("2020-11-12")));
    }

    @Test
    public void testCanRetrievePaymentDates() {
        PaymentDates dates = client.paymentDates("GBP", null);

        assertThat(dates.getInvalidPaymentDates(), not(anEmptyMap()));
        Date invalidPaymentDate = dates.getInvalidPaymentDates().keySet().iterator().next();
        assertThat(invalidPaymentDate, equalTo(parseDate("2017-01-14")));
        assertThat(dates.getInvalidPaymentDates().get(invalidPaymentDate), equalTo("No trading on Saturday"));
        assertThat(dates.getFirstPaymentDate(), equalTo(parseDate("2017-01-16")));
    }

    @Test
    public void testCanRetrieveCurrencies() {
        List<Currency> currencies = client.currencies();
        assertThat(currencies, not(empty()));

        Currency currency = currencies.iterator().next();
        assertThat(currency.getCode(), equalTo("AED"));
        assertThat(currency.getName(), equalTo("United Arab Emirates Dirham"));
        assertThat(currency.getDecimalPlaces(), equalTo(2));
        assertThat(currency.getOnlineTrading(), is(true));
        assertThat(currency.getCanBuy(), is(true));
        assertThat(currency.getCanSell(), is(true));
    }

    @Test
    public void testCanRetrieveSettlementAccounts() {
        List<SettlementAccount> settlementAccounts = client.settlementAccounts("GBP", null);
        assertThat(settlementAccounts, not(empty()));

        SettlementAccount settlementAccount = settlementAccounts.iterator().next();
        assertThat(settlementAccount.getBankAccountHolderName(), equalTo("The Currency Cloud GBP - Client Seg A/C"));
        assertThat(settlementAccount.getBankAddress(), empty());
    }

    @Test
    public void testCanRetrievePayerDetails() {
        List<PayerRequiredDetail> requiredDetails = client.payerRequiredDetails("GB", null, null);
        assertThat(requiredDetails, not(empty()));
        assertThat(requiredDetails.size(), equalTo(4));

        PayerRequiredDetail payerDetail = requiredDetails.iterator().next();
        assertThat(payerDetail.getPayerEntityType(), equalTo("company"));
        assertThat(payerDetail.getPaymentType(), equalTo("priority"));
        assertThat(payerDetail.getRequiredFields().size(), equalTo(5));
        assertThat(payerDetail.getPayerIdentificationType(), equalTo("incorporation_number"));
    }

    @Test
    public void testCanRetrievePaymentPurposeCodes() {
        List<PaymentPurposeCode> purposeCodeData = client.paymentPurposeCodes("INR", "IN", null);
        assertThat(purposeCodeData, not(empty()));
        assertThat(purposeCodeData.size(), equalTo(55));

        PaymentPurposeCode paymentPurposeCode = purposeCodeData.iterator().next();
        assertThat(paymentPurposeCode.getCurrency(), equalTo("INR"));
        assertThat(paymentPurposeCode.getEntityType(), equalTo("company"));
        assertThat(paymentPurposeCode.getPurposeCode(), equalTo("property_purchase"));
        assertThat(paymentPurposeCode.getPurposeDescription(), equalTo("Purchase of residential property"));
    }

    @Test
    public void testCanRetrieveBankDetails() {
        final BankDetails bankDetails = client.bankDetails("iban", "GB19TCCL00997901654515");
        assertThat(bankDetails.getIdentifierType(), equalTo("iban"));
        assertThat(bankDetails.getIdentifierValue(), equalTo("GB19TCCL00997901654515"));
        assertThat(bankDetails.getAccountNumber(), equalTo("GB19TCCL00997901654515"));
        assertThat(bankDetails.getBicSwift(), equalTo("TCCLGB22XXX"));
        assertThat(bankDetails.getBankName(), equalTo("THE CURRENCY CLOUD LIMITED"));
        assertThat(bankDetails.getBankBranch(), equalTo(""));
        assertThat(bankDetails.getBankAddress(), equalTo("12 STEWARD STREET  THE STEWARD BUILDING FLOOR 0"));
        assertThat(bankDetails.getBankCity(), equalTo("LONDON"));
        assertThat(bankDetails.getBankState(), equalTo("LONDON"));
        assertThat(bankDetails.getBankPostCode(), equalTo("E1 6FQ"));
        assertThat(bankDetails.getBankCountry(), equalTo("UNITED KINGDOM"));
        assertThat(bankDetails.getBankCountryISO(), equalTo("GB"));
        assertThat(bankDetails.getCurrency(), nullValue());
    }

    @Test
    public void testCanRetrievePaymentFeeRules() {
        final List<PaymentFeeRule> paymentFeeRules1 = client.paymentFeeRules(null, null, null);
        assertThat(paymentFeeRules1.size(), equalTo(3));
        final PaymentFeeRule feeRule1_1 = paymentFeeRules1.get(0);
        assertThat(feeRule1_1.getChargeType(), equalTo("shared"));
        assertThat(feeRule1_1.getFeeAmount(), equalTo(new BigDecimal("2.00")));
        assertThat(feeRule1_1.getFeeCurrency(), equalTo("AED"));
        assertThat(feeRule1_1.getPaymentType(), equalTo("priority"));
        assertThat(feeRule1_1.getPaymentFeeId(), equalTo("8b6facb0-be00-012f-3971-24003ab3f236"));
        assertThat(feeRule1_1.getPaymentFeeName(), equalTo("Name1"));
        final PaymentFeeRule feeRule1_2 = paymentFeeRules1.get(1);
        assertThat(feeRule1_2.getChargeType(), equalTo("shared"));
        assertThat(feeRule1_2.getFeeAmount(), equalTo(new BigDecimal("12.00")));
        assertThat(feeRule1_2.getFeeCurrency(), equalTo("USD"));
        assertThat(feeRule1_2.getPaymentType(), equalTo("regular"));
        assertThat(feeRule1_2.getPaymentFeeId(), equalTo("b75a0b91-be00-012f-3975-24003ab3f236"));
        assertThat(feeRule1_2.getPaymentFeeName(), equalTo("Name2"));
        final PaymentFeeRule feeRule1_3 = paymentFeeRules1.get(2);
        assertThat(feeRule1_3.getChargeType(), equalTo("ours"));
        assertThat(feeRule1_3.getFeeAmount(), equalTo(new BigDecimal("5.25")));
        assertThat(feeRule1_3.getFeeCurrency(), equalTo("GBP"));
        assertThat(feeRule1_3.getPaymentType(), equalTo("priority"));
        assertThat(feeRule1_3.getPaymentFeeId(), equalTo("60a3c841-be23-012f-3afb-24003ab3f236"));
        assertThat(feeRule1_3.getPaymentFeeName(), equalTo("Name3"));


        final List<PaymentFeeRule> paymentFeeRules2 = client.paymentFeeRules(null, "regular", null);
        assertThat(paymentFeeRules2.size(), equalTo(1));
        final PaymentFeeRule feeRule2_1 = paymentFeeRules2.get(0);
        assertThat(feeRule2_1.getChargeType(), equalTo("shared"));
        assertThat(feeRule2_1.getFeeAmount(), equalTo(new BigDecimal("12.00")));
        assertThat(feeRule2_1.getFeeCurrency(), equalTo("USD"));
        assertThat(feeRule2_1.getPaymentType(), equalTo("regular"));
        assertThat(feeRule2_1.getPaymentFeeId(), equalTo("b75a0b91-be00-012f-3975-24003ab3f236"));
        assertThat(feeRule2_1.getPaymentFeeName(), equalTo("Name2"));

        final List<PaymentFeeRule> paymentFeeRules3 = client.paymentFeeRules(null, null, "ours");
        assertThat(paymentFeeRules3.size(), equalTo(1));
        final PaymentFeeRule feeRule3_1 = paymentFeeRules3.get(0);
        assertThat(feeRule3_1.getChargeType(), equalTo("ours"));
        assertThat(feeRule3_1.getFeeAmount(), equalTo(new BigDecimal("5.25")));
        assertThat(feeRule3_1.getFeeCurrency(), equalTo("GBP"));
        assertThat(feeRule3_1.getPaymentType(), equalTo("priority"));
        assertThat(feeRule3_1.getPaymentFeeId(), equalTo("60a3c841-be23-012f-3afb-24003ab3f236"));
        assertThat(feeRule3_1.getPaymentFeeName(), equalTo("Name3"));
    }

    @Test
    public void testCanRetrieveConversionDatesOfflineTrading() {
        ConversionDates dates = client.conversionDates("USDGBP", null);

        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        assertThat(dates.getInvalidConversionDates().size(), equalTo(17));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2020-11-21")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("No trading on Saturday"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2020-11-18")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2020-11-20")));
        assertThat(dates.getFirstConversionCutoffDatetime(), equalTo(parseDateTime("2020-11-18T15:50:00+00:00")));
        assertThat(dates.getOptimizeLiquidityConversionDate(), equalTo(parseDate("2020-11-23")));
        assertThat(dates.getNextDayConversionDate(), equalTo(parseDate("2020-11-18")));
        assertThat(dates.getOfflineConversionDates().size(), equalTo(1));
        assertThat(dates.getOfflineConversionDates().get(0), equalTo(parseDate("2020-11-23")));
    }

    @Test
    public void testCanRetrieveConversionDatesOnBehalfOf() {
        AtomicReference<ConversionDates> value = new AtomicReference<>();
        client.onBehalfOfDo("c6ece846-6df1-461d-acaa-b42a6aa74045", () -> {
            value.set(client.conversionDates("GBPUSD", null));
                });
        ConversionDates dates = value.get();
        assertThat(dates.getInvalidConversionDates(), not(anEmptyMap()));
        assertThat(dates.getInvalidConversionDates().size(), equalTo(242));
        Date invalidConversionDate = dates.getInvalidConversionDates().keySet().iterator().next();
        assertThat(invalidConversionDate, equalTo(parseDate("2020-11-11")));
        assertThat(dates.getInvalidConversionDates().get(invalidConversionDate), equalTo("Veterans' Day"));
        assertThat(dates.getFirstConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getNextDayConversionDate(), equalTo(parseDate("2020-11-10")));
        assertThat(dates.getDefaultConversionDate(), equalTo(parseDate("2020-11-12")));
        assertThat(dates.getFirstConversionCutoffDatetime(), equalTo(parseDateTime("2020-11-10T15:30:00+00:00")));
        assertThat(dates.getOptimizeLiquidityConversionDate(), equalTo(parseDate("2020-11-12")));
    }
}
