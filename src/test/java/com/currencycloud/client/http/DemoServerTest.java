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
 * Run when needed only.
 */
@Ignore
public class DemoServerTest {

    private static final Logger log = LoggerFactory.getLogger(DemoServerTest.class);
    private static final Random RND = new Random();
    private static final String SOME_UUID = "385f0e80-1ffd-4d9c-8a64-11237bdb9284";
    private static final String LOGIN_ID = "rjnienaber@gmail.com";
    private static final String API_KEY = "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0";

    private final SimpleDateFormat dateFormat;

    private static CurrencyCloudClient currencyCloud = new CurrencyCloudClient(
            CurrencyCloudClient.Environment.demo,
            LOGIN_ID, API_KEY
    );

    public DemoServerTest() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testCookbook() throws Exception {
        CurrencyCloudCookbook.runCookBook(LOGIN_ID, API_KEY);
    }

    /** Test that payment types collection is handled correctly. */
    @Test
    public void testPaymentTypes() throws Exception {
        try {
            Beneficiary beneficiary = Beneficiary.createForValidate("GB", "GBP", "GB"); // todo: does the createForValidate method make sense?
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
        Account badExample = Account.create("No such account", "individual");
        badExample.setCountry("DE");
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
        Account account = currencyCloud.createAccount(Account.create("New Account xyz", "individual" , " Test Street", "London", "GB"));

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
        Balances balances = currencyCloud.findBalances(
                new BigDecimal("0.00"),
                new BigDecimal("1000000000.00"),
                null,
//                getDate("2015-05-28"), // Non-null date causes null pagination to be returned.
                Pagination.builder().pages(1, 20).build()
        );
        Pagination pagination = balances.getPagination();
        assertThat(pagination.getPerPage(), equalTo(20));
        assertThat(pagination.getPage(), equalTo(1));

//        assertThat(balances.getBalances(), hasSize(greaterThan(0)));
    }

    @Test
    public void testCreateResetToken() throws Exception {
        currencyCloud.createResetToken(currencyCloud.getLoginId());
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
        String Contact_email_id = randomString() + "+jdjr@example.com";

        Contact contact = currencyCloud.createContact(
                Contact.create(accountId, Contact_First_Name, Contact_Last_Name, Contact_email_id, "555 555 555 555")
        );

        log.debug("contact = {}", contact);
        assertThat(contact.getMobilePhoneNumber(), is(nullValue()));

        String newPhoneNumber = "555 666 777 888";
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
        Conversion conversion = Conversion.create(
                "EUR", "GBP", "buy", date, null, null, null, null, null
        );
        conversion = currencyCloud.createConversion(conversion, new BigDecimal("10000.00"), "Invoice Payment", true);

        log.debug("conversion = {}", conversion);

        assertThat(conversion.getFixedSide(), equalTo("buy"));
        assertThat(conversion.getCurrencyPair(), equalTo("EURGBP"));

        conversion = currencyCloud.retrieveConversion(conversion.getId());

        assertThat(conversion.getFixedSide(), equalTo("buy"));
        assertThat(conversion.getCurrencyPair(), equalTo("EURGBP"));

        List<Conversion> conversions = currencyCloud.findConversions(
                null, Collections.singleton(conversion.getId()),
                null, null, null, null, null, null, null, null, null, null, null, null, null
        ).getConversions();

        assertFound(conversions, conversion);

        conversions = currencyCloud.findConversions(
                null, Arrays.asList(conversion.getId(), "invalid-id"),
                null, getDate("2100-01-01"),
                getDate("2015-01-01"), null, null, null, null, null,
                null, null, null, new BigDecimal("10000000.00"), null
        ).getConversions();

        assertFound(conversions, conversion);
    }

    @Test
    public void testFindConversions() throws Exception {
        currencyCloud.findConversions(
                Conversion.createExample(
                        "ref", "awaiting_funds", "funds_sent", "GBP", "USD", "EURMXN", null
                ),
                Collections.<String>emptyList(),
                new Date(),
                new Date(),
                new Date(),
                new Date(),
                new BigDecimal("1.00"),
                new BigDecimal("100000.00"),
                new BigDecimal("1.00"),
                new BigDecimal("100000.00"),
                new BigDecimal("1.00"),
                new BigDecimal("100000.00"),
                new BigDecimal("1.00"),
                new BigDecimal("100000.00"),
                null
        );
    }

    @Test
    public void testPaymentsPayers() throws Exception {
        Beneficiary beneficiary = Beneficiary.create("Acme GmbH", "DE", "EUR", "John Doe");
        beneficiary.setBicSwift("COBADEFF");
        beneficiary.setIban("DE89370400440532013000");
        beneficiary = currencyCloud.createBeneficiary(beneficiary);
        log.debug("beneficiary = {}", beneficiary);

        Conversion conversion = Conversion.create("EUR", "GBP", "buy");
        conversion = currencyCloud.createConversion(conversion, new BigDecimal("10000.00"), "Invoice Payment", true);
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

        List<Payment> payments = currencyCloud.findPayments(
                payment,
                amount.subtract(BigDecimal.ONE), payment.getAmount().add(BigDecimal.ONE), null,
                null, null, null, null, null, from, null, null, null
        ).getPayments();

        assertFound(payments, payment);

        payments = currencyCloud.findPayments(
                null,
                amount.subtract(BigDecimal.ONE), payment.getAmount().add(BigDecimal.ONE), null,
                null, null, null, from, null, null, null, null, null
        ).getPayments();

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

        payments = currencyCloud
                .findPayments(payment, null, null, null, null, null, null, null, null, null, null, null, null)
                .getPayments();

        assertFound(payments, payment, false);
    }

    @Test
    public void testFindPaymentsByExample() throws Exception {
        currencyCloud.findPayments(
                Payment.createExample("USD",
                                      SOME_UUID, new BigDecimal("12.44"), "Some reason",
                                      SOME_UUID, "asdf", "ready_to_send", null),
                BigDecimal.ONE, new BigDecimal("1000000.00"), new Date(),
                new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), new Date(), Pagination.first(), null
        ).getPayments();
    }

    @Test
    public void testSettlements() throws Exception {
        Date to = getDate("2115-01-01");

        Settlement settlement = currencyCloud.createSettlement();

        List<Settlement> settlements = currencyCloud.findSettlements(
                settlement.getShortReference(), null, null, to, null, to, null, null, null
        ).getSettlements();
        assertFound(settlements, settlement);
        assertThat(settlement.getStatus(), equalTo("open"));

        Conversion conversion = currencyCloud.createConversion(
                Conversion.create("EUR", "GBP", "buy"),
                new BigDecimal("10000.00"), "Invoice Payment", true
        );
        log.debug("conversion = {}", conversion);

        settlement = currencyCloud.addConversion(settlement.getId(), conversion.getId());
        assertThat(settlement.getStatus(), equalTo("open"));

        settlements = currencyCloud.findSettlements(
                settlement.getShortReference(), null, null, to, null, to, null, null, null
        ).getSettlements();
        assertFound(settlements, settlement);

        settlement = currencyCloud.releaseSettlement(settlement.getId());
        assertThat(settlement.getStatus(), equalTo("released"));
        settlements = currencyCloud.findSettlements(
                settlement.getShortReference(), null, null, to, null, to, null, to, null
        ).getSettlements();
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
        List<Transaction> transactions = currencyCloud.findTransactions(
                null, new BigDecimal("0.00"), new BigDecimal("1000000000.00"),
                from, to,
                from, to,
                from, to,
                Pagination.builder().pages(1, 10).build()
        ).getTransactions();
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
        currencyCloud.findTransactions(
                Transaction.createExample(
                        "GBP", new BigDecimal("123.45"), "payment_failure", "inbound_funds", SOME_UUID, "ref",
                        "pending", "debit", "Because"
                ),
                new BigDecimal("0.00"), new BigDecimal("1000000000.00"),
                now, now,
                now, now,
                now, now,
                Pagination.builder().pages(1, 10).build()
        );
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    private Date getDate(String str) throws ParseException {
        synchronized (dateFormat) {
            return dateFormat.parse(str);
        }
    }

    private static String randomString() {
        return new BigInteger(32, RND).toString(32);
    }

    private static BigDecimal randomAmount() {
        return new BigDecimal(RND.nextInt(800000)).movePointLeft(2);
    }
}
