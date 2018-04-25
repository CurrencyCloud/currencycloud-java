package com.currencycloud.client.http;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.ForbiddenException;
import com.currencycloud.client.exception.NotFoundException;
import com.currencycloud.client.model.*;
import com.currencycloud.examples.CurrencyCloudCookbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.fail;

/**
 * This is an integration test that executes actual http calls to the demo server
 * and shouldn't be run when unit tests are run.
 * Run when needed only. Replace LOGIN_ID, API_KEY and SOME_UUID with appropiate values
 */
@Ignore
public class DemoServerTest {

    private static final Logger log = LoggerFactory.getLogger(DemoServerTest.class);
    private static final Random RND = new Random();
    private static final String SOME_UUID = "deadbeef-dead-beef-dead-beefdeadbeef";
    private static final String LOGIN_ID = "development@currencycloud.com";
    private static final String API_KEY = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
    private static CurrencyCloudClient currencyCloud = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo, LOGIN_ID, API_KEY);
    private final SimpleDateFormat dateFormat;

    public DemoServerTest() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    private static <T extends Entity> void assertFound(List<T> ts, T t) {
        assertFound(ts, t, true);
    }

    private static <T extends Entity> void assertFound(List<T> ts, T t, boolean expectFound) {
        boolean found = contains(ts, t);
        assertThat(found, equalTo(expectFound));
    }

    private static <T extends Entity> boolean contains(List<T> ts, T t) {
        boolean found = false;
        for (T c : ts) {
            if (c.getId().equals(t.getId())) {
                found = true;
                break;
            }
        }
        return found;
    }

    private static String randomString() {
        return new BigInteger(32, RND).toString(32);
    }

    private static BigDecimal randomAmount() {
        return new BigDecimal(RND.nextInt(800000)).movePointLeft(2);
    }

    @Test
    public void testCookbook() throws Exception {
        CurrencyCloudCookbook.runCookBook(LOGIN_ID, API_KEY);
    }

    /** Test that payment types collection is handled correctly. */
    @Test
    public void testPaymentTypes() throws Exception {
        try {
            Beneficiary beneficiary = Beneficiary.create();
            beneficiary.setBankCountry("GB");
            beneficiary.setCurrency("GBP");
            beneficiary.setBeneficiaryCountry("GB");
            beneficiary.setBankAddress(Arrays.asList("Trafalgar Square", "London", "UK"));
            beneficiary.setBankName("Acme Bank");
            beneficiary.setPaymentTypes(Arrays.asList("priority", "regular"));
            currencyCloud.validateBeneficiary(beneficiary);

            assertThat("Should fail.", false);

        } catch (ApiException e) {
            log.info(e.toString());
            List<ErrorMessage> msgs = e.getErrors();
            for (ErrorMessage error : msgs) {
                if ("payment_types".equals(error.getField())) {
                    if (Arrays.asList("payment_types_type_is_wrong", "payment_types_not_included_in_list")
                            .contains(error.getCode())) {
                        throw new AssertionError(error.getMessage());
                    }
                }
            }
        }
    }

    /** Test that payment types collection is handled correctly. */
    @Test
    public void testAddress() throws Exception {
        List<String> paymentTypes = Arrays.asList("priority", "regular");
        Beneficiary beneficiary = Beneficiary.create("John W Doe", "DE", "EUR", "John Doe");
        beneficiary.setBeneficiaryAddress(Collections.singletonList("Hamburg, GE"));
        beneficiary.setBeneficiaryCountry("DE");
        beneficiary.setBicSwift("COBADEFF");
        beneficiary.setIban("DE89370400440532013000");
        beneficiary.setBeneficiaryEntityType("individual");
        beneficiary.setBeneficiaryCompanyName("ACME Ltd.");
        beneficiary.setBeneficiaryFirstName("John");
        beneficiary.setBeneficiaryLastName("Doe");
        beneficiary.setBeneficiaryCity("Hamburg");
        beneficiary.setBankAddress(Arrays.asList("Trafalgar Square", "London", "UK"));
        beneficiary.setBankName("Acme Bank");
        beneficiary.setPaymentTypes(paymentTypes);
        beneficiary = currencyCloud.createBeneficiary(beneficiary);

        assertThat(beneficiary.getPaymentTypes(), is(equalTo(paymentTypes)));
    }

    @Test
    public void testCurrentAccount() throws Exception {
        Accounts accounts = currencyCloud.findAccounts(null, null);
        log.debug("Accounts = {}", accounts);

        Account account = currencyCloud.currentAccount();
        log.debug("Current account = {}", account);

        String myAccId = account.getId();
        account = currencyCloud.retrieveAccount(myAccId);
        assertThat(account.getId(), equalTo(myAccId));

        boolean found = false;
        for (Account a : accounts.getAccounts()) {
            if (Objects.equals(a.getId(), myAccId)) {
                found = true;
            }
        }

        assertThat("Current account not found among the ones listed.", found);
    }

    @Test
    public void testFindNoAccountsWithABadExample() throws Exception {
        Account badExample = Account.create("No such account", "individual", "No street", "No city", "DE");
        badExample.setIdentificationValue("1111111");
        badExample.setIdentificationType("drivers_licence");
        badExample.setBrand("Brand");
        badExample.setPostalCode("XYZ");
        badExample.setShortReference("1234");
        badExample.setYourReference("mine");
        badExample.setSpreadTable("st");
        badExample.setStatus("disabled");
        badExample.setStreet("Weitstrasse 1");
        badExample.setCity("Hamburg");
        badExample.setStateOrProvince("Western region");
        Accounts accounts = currencyCloud.findAccounts(badExample, null);

        assertThat(accounts.getAccounts(), hasSize(0));
    }

    @Test
    public void testCreateUpdateAccount() throws Exception {
        Account account = currencyCloud.createAccount(Account.create("New Account xyz", "individual" , " 12 Steward St", "London", "GB"));

        assertThat(account.getYourReference(), is(nullValue()));

        account.setYourReference("a");
        account = currencyCloud.updateAccount(account);

        assertThat(account.getYourReference(), equalTo("a"));

        account.setStatus("disabled");
        try {
            currencyCloud.updateAccount(account);
            fail("Updating account status should fail");
        } catch (ForbiddenException ignored) {
            // This always happens with current permissions.
        }
    }

    @Test
    public void testBalances() throws Exception {
        Balance balanceCondition = Balance.create();
        balanceCondition.setAmountFrom(new BigDecimal("0.00"));
        balanceCondition.setAmountTo(new BigDecimal("1000000000.00"));
        balanceCondition.setAsAtDate(getDate("2018-03-23"));
        Pagination paginationCondition = Pagination.builder().pages(1, 20).build();
        Balances balances = currencyCloud.findBalances(balanceCondition, paginationCondition);
        Pagination pagination = balances.getPagination();

        assertThat(pagination.getPerPage(), equalTo(20));
        assertThat(pagination.getPage(), equalTo(1));
    }

    @Test
    public void testCurrentContact() throws Exception {
        Contact contact = currencyCloud.currentContact();
        log.debug("Current contact = {}", contact);
    }

    @Test
    public void testContacts() throws Exception {
        String accountId = currencyCloud.currentAccount().getId();
        log.debug("accountId = {}", accountId);

        String Contact_First_Name = randomString();
        String Contact_Last_Name = randomString();
        String Contact_email_id = randomString() + "development@curencycloud.com";

        Contact contact = currencyCloud.createContact(
                Contact.create(accountId, Contact_First_Name, Contact_Last_Name, Contact_email_id, "+1 (646) 593 8724")
        );

        log.debug("contact = {}", contact);
        assertThat(contact.getMobilePhoneNumber(), is(nullValue()));

        String newPhoneNumber = "+44 (0)20 3326 8173";
        contact.setMobilePhoneNumber(newPhoneNumber);
        contact = currencyCloud.updateContact(contact);

        assertThat(contact.getMobilePhoneNumber(), equalTo(newPhoneNumber));

        contact = currencyCloud.retrieveContact(contact.getId());

        List<Contact> contacts = currencyCloud.findContacts(contact, Pagination.first()).getContacts();
        assertFound(contacts, contact);

        contacts = currencyCloud.findContacts(
                Contact.create(accountId, Contact_First_Name, Contact_Last_Name, Contact_email_id, null),
                Pagination.builder().pages(1, 10).build()
        ).getContacts();
        assertFound(contacts, contact);
    }

    @Test
    public void testConversions() throws Exception {
        // Today + 7 days:
        Date date = getDate(dateFormat.format(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)));
        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("EUR");
        conversion.setSellCurrency("GBP");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal("10000.00"));
        conversion.setReason("Invoice Payment");
        conversion.setTermAgreement(true);

        conversion = currencyCloud.createConversion(conversion);

        log.debug("conversion = {}", conversion);

        assertThat(conversion.getFixedSide(), equalTo("buy"));
        assertThat(conversion.getCurrencyPair(), equalTo("EURGBP"));

        conversion = currencyCloud.retrieveConversion(conversion.getId());

        assertThat(conversion.getFixedSide(), equalTo("buy"));
        assertThat(conversion.getCurrencyPair(), equalTo("EURGBP"));

        Conversion conversionCondition1 = Conversion.create();
        conversionCondition1.setId(conversion.getId());
        List<Conversion> conversions = currencyCloud.findConversions(conversionCondition1, null).getConversions();

        assertFound(conversions, conversion);

        Conversion conversionCondition2 = Conversion.create();
        conversionCondition2.setConversionIds(Arrays.asList(conversion.getId(), "invalid-id"));
        conversionCondition2.setCreatedAtTo(getDate("2100-01-01"));
        conversionCondition2.setUpdatedAtFrom(getDate("2015-01-01"));
        conversionCondition2.setSellAmountTo(new BigDecimal("10000000.00"));
        conversions = currencyCloud.findConversions(conversionCondition2, null).getConversions();

        assertFound(conversions, conversion);
    }

    @Test
    public void testFindConversions() throws Exception {
        Conversion conversionCondition = Conversion.create();
        conversionCondition.setShortReference("ref");
        conversionCondition.setStatus("awaiting_funds");
        conversionCondition.setPartnerStatus("funds_sent");
        conversionCondition.setBuyCurrency("GBP");
        conversionCondition.setSellCurrency("USD");
        conversionCondition.setCurrencyPair("EURMXN");
        conversionCondition.setConversionIds(Collections.<String>emptyList());
        conversionCondition.setCreatedAtFrom(new Date());
        conversionCondition.setCreatedAtTo(new Date());
        conversionCondition.setUpdatedAtFrom(new Date());
        conversionCondition.setUpdatedAtTo(new Date());
        conversionCondition.setPartnerBuyAmountFrom(new BigDecimal("1.00"));
        conversionCondition.setPartnerBuyAmountTo(new BigDecimal("100000.00"));
        conversionCondition.setPartnerSellAmountFrom(new BigDecimal("1.00"));
        conversionCondition.setPartnerSellAmountTo( new BigDecimal("100000.00"));
        conversionCondition.setBuyAmountFrom(new BigDecimal("1.00"));
        conversionCondition.setBuyAmountTo(new BigDecimal("100000.00"));
        conversionCondition.setSellAmountFrom(new BigDecimal("1.00"));
        conversionCondition.setSellAmountTo(new BigDecimal("100000.00"));
        currencyCloud.findConversions(conversionCondition, null);
    }

    @Test
    public void testPaymentsPayers() throws Exception {
        Beneficiary beneficiary = Beneficiary.create("Acme GmbH", "DE", "EUR", "John Doe");
        beneficiary.setBicSwift("COBADEFF");
        beneficiary.setIban("DE89370400440532013000");
        beneficiary = currencyCloud.createBeneficiary(beneficiary);
        log.debug("beneficiary = {}", beneficiary);

        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("EUR");
        conversion.setSellCurrency("GBP");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal("10000.00"));
        conversion.setReason("Invoice Payment");
        conversion.setTermAgreement(true);

        conversion = currencyCloud.createConversion(conversion);
        log.debug("conversion = {}", conversion);

        BigDecimal amount = randomAmount();
        Payment payment = Payment.create(
                "EUR", beneficiary.getId(), amount, "Invoice Payment", "Invoice 1234",
                null, "regular", conversion.getId(), null
        );

        List<String> payerAddress = new ArrayList<String>();
        payerAddress.add("Payer Address Line 1");
        payerAddress.add("Payer Address Line 2");
        Payer payer = Payer.create(
                "individual",
                "Test Payer Company",
                "Test Payer First Name",
                "Test Payer Last Name",
                payerAddress,
                "Paris",
                "FR",
                new Date());
        payment = currencyCloud.createPayment(payment, payer);
        log.debug("Created payment = {}", payment);

        payment = currencyCloud.retrievePayment(payment.getId());
        log.debug("Retrieved payment = {}", payment);

        Date from = getDate("2015-01-01");

        payment.setAmountFrom(amount.subtract(BigDecimal.ONE));
        payment.setAmountTo(payment.getAmount().add(BigDecimal.ONE));
        payment.setUpdatedAtFrom(from);
        List<Payment> payments = currencyCloud.findPayments(payment, null).getPayments();

        assertFound(payments, payment);

        Payment paymentCondition = Payment.create();
        paymentCondition.setAmountFrom(amount.subtract(BigDecimal.ONE));
        paymentCondition.setAmountTo(payment.getAmount().add(BigDecimal.ONE));
        paymentCondition.setCreatedAtTo(from);
        payments = currencyCloud.findPayments(paymentCondition, null).getPayments();

        assertFound(payments, payment);

        payer = currencyCloud.retrievePayer(payment.getPayerId());
        payment.setReason("A changed reason");
        payer.setCity("A different city.");
        currencyCloud.updatePayment(payment, payer);

        Payment payment2 = Payment.create(
                "EUR", beneficiary.getId(), randomAmount(), "Invoice Payment 2", "Invoice 2234",
                null, "regular", conversion.getId(), null
        );
        payment2 = currencyCloud.createPayment(payment2, payer);
        log.debug("Created payment2 = {}", payment2);

        currencyCloud.deletePayment(payment.getId());
        payments = currencyCloud.findPayments(payment, null).getPayments();

        assertFound(payments, payment, false);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Test
    public void testFindPaymentsByExample() throws Exception {
        Payment payment = Payment.create();
        payment.setCurrency("USD");
        payment.setBeneficiaryId(SOME_UUID);
        payment.setAmount(new BigDecimal("12.44"));
        payment.setReason("Some reason");
        payment.setConversionId(SOME_UUID);
        payment.setShortReference("asdf");
        payment.setStatus("ready_to_send");
        payment.setAmountFrom(BigDecimal.ONE);
        payment.setAmountTo(new BigDecimal("1000000.00"));
        payment.setPaymentDateFrom(new Date());
        payment.setPaymentDateTo(new Date());
        payment.setTransferredAtFrom(new Date());
        payment.setTransferredAtTo(new Date());
        payment.setCreatedAtFrom(new Date());
        payment.setCreatedAtTo(new Date());
        payment.setUpdatedAtFrom(new Date());
        payment.setUpdatedAtTo(new Date());
        currencyCloud.findPayments(payment, Pagination.first()).getPayments();
    }

    @Test
    public void testSettlements() throws Exception {
        Date to = getDate("2115-01-01");

        Settlement settlement = currencyCloud.createSettlement(Settlement.create());
        Settlement settlementCondition = currencyCloud.createSettlement(Settlement.create());
        settlementCondition.setShortReference(settlement.getShortReference());

        List<Settlement> settlements = currencyCloud.findSettlements(settlementCondition, null).getSettlements();
        assertFound(settlements, settlement);
        assertThat(settlement.getStatus(), equalTo("open"));

        Conversion conversion = Conversion.create();
        conversion.setBuyCurrency("EUR");
        conversion.setSellCurrency("GBP");
        conversion.setFixedSide("buy");
        conversion.setAmount(new BigDecimal("10000.00"));
        conversion.setReason("Invoice Payment");
        conversion.setTermAgreement(true);

        conversion = currencyCloud.createConversion(conversion);
        log.debug("conversion = {}", conversion);

        settlement = currencyCloud.addConversion(settlement.getId(), conversion.getId());
        assertThat(settlement.getStatus(), equalTo("open"));

        settlementCondition.setShortReference(settlement.getShortReference());
        settlements = currencyCloud.findSettlements(settlementCondition, null).getSettlements();
        assertFound(settlements, settlement);

        settlement = currencyCloud.releaseSettlement(settlement.getId());
        assertThat(settlement.getStatus(), equalTo("released"));

        settlementCondition.setShortReference(settlement.getShortReference());
        settlements = currencyCloud.findSettlements(settlementCondition, null).getSettlements();
        assertFound(settlements, settlement);

        settlement = currencyCloud.unreleaseSettlement(settlement.getId());
        assertThat(settlement.getStatus(), equalTo("open"));

        settlement = currencyCloud.removeConversion(settlement.getId(), conversion.getId());
        assertThat(settlement.getStatus(), equalTo("open"));

        settlement = currencyCloud.deleteSettlement(settlement.getId());
        assertThat(settlement.getStatus(), equalTo("open"));

        try {
            currencyCloud.retrieveSettlement(settlement.getId());
            fail("Shouldn't be able to retrieve a deleted settlement.");
        } catch (NotFoundException e) {
            assertThat(e.getErrorCode(), equalTo("settlement_not_found"));
        }
    }

    @Test
    public void testTransactions() throws Exception {
        Date from = getDate("2015-01-01");
        Date to = getDate("2115-01-01");
        Transaction transactionCondition = Transaction.create();
        transactionCondition.setAmountFrom(new BigDecimal("0.00"));
        transactionCondition.setAmountTo(new BigDecimal("1000000000.00"));
        transactionCondition.setSettlesAtFrom(from);
        transactionCondition.setSettlesAtTo(to);
        transactionCondition.setCreatedAtFrom(from);
        transactionCondition.setCreatedAtTo(to);
        transactionCondition.setUpdatedAtFrom(from);
        transactionCondition.setUpdatedAtTo(to);

        List<Transaction> transactions = currencyCloud.findTransactions(transactionCondition, Pagination.builder().pages(1, 10).build()).getTransactions();
        log.debug("transactions = {}", transactions);

        // todo: we never get any transactions here
//        assertThat(transactions, hasSize(greaterThan(0)));
//        Transaction transaction = transactions.get(0);

//        transaction = currencyCloud.retrieveTransaction(transaction.getId());
//        log.debug("transaction = {}", transaction);
    }

    @Test
    public void testFindTransactionsByExample() throws Exception {
        Date now = new Date();
        Transaction transactionCondition = Transaction.create();
        transactionCondition.setCurrency("GBP");
        transactionCondition.setAmount(new BigDecimal("123.45"));
        transactionCondition.setAction("payment_failure");
        transactionCondition.setRelatedEntityType("inbound_funds");
        transactionCondition.setRelatedEntityId(SOME_UUID);
        transactionCondition.setRelatedEntityShortReference("ref");
        transactionCondition.setStatus("pending");
        transactionCondition.setType("debit");
        transactionCondition.setReason("Because");
        transactionCondition.setAmountFrom(new BigDecimal("0.00"));
        transactionCondition.setAmountTo(new BigDecimal("1000000000.00"));
        transactionCondition.setSettlesAtFrom(now);
        transactionCondition.setSettlesAtTo(now);
        transactionCondition.setCreatedAtFrom(now);
        transactionCondition.setCreatedAtTo(now);
        transactionCondition.setUpdatedAtFrom(now);
        transactionCondition.setUpdatedAtTo(now);
        currencyCloud.findTransactions(transactionCondition, Pagination.builder().pages(1, 10).build());
    }

    @Test
    public void testTransactionRetrieve() throws Exception {
        try {
            currencyCloud.retrieveTransaction("c5a990eb-d4d7-482f-bfb1-ffffffffffff");
//        } catch (NotFoundException e) {
        } catch (ForbiddenException e) {
            assertThat(e.getErrorCode(), equalTo("transaction_not_found"));
        }
    }

    private Date getDate(String str) throws ParseException {
        synchronized (dateFormat) {
            return dateFormat.parse(str);
        }
    }
}
