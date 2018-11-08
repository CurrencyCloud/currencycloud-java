package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class ReportingTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "334cbfdb9ba9bfb6ffd499b0c6af6b12");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_generate_conversion_report", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanGenerateConversionReport() throws Exception {
        ConversionReport conversionReport = ConversionReport.create();
        conversionReport.setDescription("Conversion test report");
        conversionReport.setBuyCurrency("CAD");
        conversionReport.setSellCurrency("GBP");
        conversionReport.setUniqueRequestId("46aca410-ce74-4303-8f79-e0e95cfd9262");
        ConversionReport report = client.createConversionReport(conversionReport);

        assertThat(report.getId(), equalTo("de5c215d-93e2-4b24-bdc8-bffbcd80c60f"));
        assertThat(report.getShortReference(), equalTo("RP-3934236-BZCXEW"));
        assertThat(report.getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(report.getDescription(),equalTo("Conversion test report"));
        assertThat(report.getReportUrl(), emptyOrNullString());
        assertThat(report.getSearchParams().getBuyCurrency(), equalTo("CAD"));
        assertThat(report.getSearchParams().getSellCurrency(), equalTo("GBP"));
        assertThat(report.getSearchParams().getUniqueRequestId(), equalTo("46aca410-ce74-4303-8f79-e0e95cfd9262"));
        assertThat(report.getSearchParams().getScope(), equalTo("own"));
        assertThat(report.getReportType(), equalTo("conversion"));
        assertThat(report.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(report.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(report.getFailureReason(), emptyOrNullString());
        assertThat(report.getStatus(), equalTo("processing"));
        assertThat(report.getExpirationDate(), nullValue());
        assertThat(report.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

    @Test
    @Betamax(tape = "can_generate_payment_report", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanGeneratePaymentReport() throws Exception {
        PaymentReport paymentReport = PaymentReport.create();
        paymentReport.setDescription("Payment test report");
        paymentReport.setCurrency("EUR");
        paymentReport.setUniqueRequestId("94985dc8-1df3-4f8a-ba23-d00cf284708d");
        paymentReport.setAmountFrom(new BigDecimal("0.00"));
        paymentReport.setAmountTo(new BigDecimal("99999.99"));
        paymentReport.setCreatedAtFrom(new GregorianCalendar(2018, Calendar.JANUARY, 1).getTime());
        paymentReport.setCreatedAtTo(new GregorianCalendar(2018, Calendar.DECEMBER, 31).getTime());
        PaymentReport report =  client.createPaymentReport(paymentReport);

        assertThat(report.getId(), equalTo("39a0ebd1-13b3-4d38-a196-745e5944f169"));
        assertThat(report.getShortReference(), equalTo("RP-6846174-EQFOHU"));
        assertThat(report.getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(report.getDescription(),equalTo("Payment test report"));
        assertThat(report.getReportUrl(), emptyOrNullString());
        assertThat(report.getSearchParams().getCurrency(), equalTo("EUR"));
        assertThat(report.getSearchParams().getAmountFrom(), equalTo(new BigDecimal("0.00")));
        assertThat(report.getSearchParams().getAmountTo(), equalTo(new BigDecimal("99999.99")));
        assertThat(report.getSearchParams().getCreatedAtFrom(), equalTo(parseDate("2018-01-01")));
        assertThat(report.getSearchParams().getCreatedAtTo(), equalTo(parseDate("2018-12-31")));
        assertThat(report.getSearchParams().getScope(), equalTo("own"));
        assertThat(report.getSearchParams().getUniqueRequestId(), equalTo("94985dc8-1df3-4f8a-ba23-d00cf284708d"));
        assertThat(report.getReportType(), equalTo("payment"));
        assertThat(report.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(report.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(report.getFailureReason(), emptyOrNullString());
        assertThat(report.getStatus(), equalTo("processing"));
        assertThat(report.getExpirationDate(), nullValue());
        assertThat(report.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindReportRequests() throws Exception {
        ReportRequests reportRequestsData = client.findReportRequests(null, null);
        List<ReportRequest> reports = reportRequestsData.getReportRequests();
        Pagination pagination = reportRequestsData.getPagination();
        System.out.println("TEST: " + reportRequestsData.toString());

        assertThat(reports, not(nullValue()));
        assertThat(pagination.getTotalEntries(), equalTo(reports.size()));
        assertThat(reports.size(), is(2));
        assertThat(reports.get(0).getId(), equalTo("de5c215d-93e2-4b24-bdc8-bffbcd80c60f"));
        assertThat(reports.get(0).getShortReference(), equalTo("RP-3934236-BZCXEW"));
        assertThat(reports.get(0).getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(reports.get(0).getDescription(),equalTo("Conversion test report"));
        assertThat(reports.get(0).getReportUrl(), not(emptyOrNullString()));
        assertThat(reports.get(0).getSearchParams().getBuyCurrency(), equalTo("CAD"));
        assertThat(reports.get(0).getSearchParams().getSellCurrency(), equalTo("GBP"));
        assertThat(reports.get(0).getSearchParams().getUniqueRequestId(), equalTo("46aca410-ce74-4303-8f79-e0e95cfd9262"));
        assertThat(reports.get(0).getSearchParams().getScope(), equalTo("own"));
        assertThat(reports.get(0).getReportType(), equalTo("conversion"));
        assertThat(reports.get(0).getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(reports.get(0).getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(reports.get(0).getFailureReason(), emptyOrNullString());
        assertThat(reports.get(0).getStatus(), equalTo("completed"));
        assertThat(reports.get(0).getExpirationDate(), equalTo(parseDate("2018-01-31")));
        assertThat(reports.get(0).getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));

        assertThat(reports.get(1).getId(), equalTo("39a0ebd1-13b3-4d38-a196-745e5944f169"));
        assertThat(reports.get(1).getShortReference(), equalTo("RP-6846174-EQFOHU"));
        assertThat(reports.get(1).getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(reports.get(1).getDescription(),equalTo("Payment test report"));
        assertThat(reports.get(1).getReportUrl(), not(emptyOrNullString()));
        assertThat(reports.get(1).getSearchParams().getCurrency(), equalTo("EUR"));
        assertThat(reports.get(1).getSearchParams().getAmountFrom(), equalTo(new BigDecimal("0.00")));
        assertThat(reports.get(1).getSearchParams().getAmountTo(), equalTo(new BigDecimal("99999.99")));
        assertThat(reports.get(1).getSearchParams().getCreatedAtFrom(), equalTo(parseDate("2018-01-01")));
        assertThat(reports.get(1).getSearchParams().getCreatedAtTo(), equalTo(parseDate("2018-12-31")));
        assertThat(reports.get(1).getSearchParams().getScope(), equalTo("own"));
        assertThat(reports.get(1).getSearchParams().getUniqueRequestId(), equalTo("94985dc8-1df3-4f8a-ba23-d00cf284708d"));
        assertThat(reports.get(1).getReportType(), equalTo("payment"));
        assertThat(reports.get(1).getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(reports.get(1).getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(reports.get(1).getFailureReason(), emptyOrNullString());
        assertThat(reports.get(1).getStatus(), equalTo("completed"));
        assertThat(reports.get(1).getExpirationDate(), equalTo(parseDate("2018-01-31")));
        assertThat(reports.get(1).getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveReportRequest() throws Exception {
        ReportRequest report = client.retrieveReportRequests("de5c215d-93e2-4b24-bdc8-bffbcd80c60f");

        assertThat(report.getId(), equalTo("de5c215d-93e2-4b24-bdc8-bffbcd80c60f"));
        assertThat(report.getShortReference(), equalTo("RP-3934236-BZCXEW"));
        assertThat(report.getContactId(), equalTo("590cea0d-0daa-48dc-882b-049107c1471f"));
        assertThat(report.getDescription(),equalTo("Conversion test report"));
        assertThat(report.getReportUrl(), not(emptyOrNullString()));
        assertThat(report.getSearchParams().getBuyCurrency(), equalTo("CAD"));
        assertThat(report.getSearchParams().getSellCurrency(), equalTo("GBP"));
        assertThat(report.getSearchParams().getUniqueRequestId(), equalTo("46aca410-ce74-4303-8f79-e0e95cfd9262"));
        assertThat(report.getSearchParams().getScope(), equalTo("own"));
        assertThat(report.getReportType(), equalTo("conversion"));
        assertThat(report.getAccountId(), equalTo("e277c9f9-679f-454f-8367-274b3ff977ff"));
        assertThat(report.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(report.getFailureReason(), emptyOrNullString());
        assertThat(report.getStatus(), equalTo("completed"));
        assertThat(report.getExpirationDate(), equalTo(parseDate("2018-01-31")));
        assertThat(report.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
    }
}
