package com.currencycloud.client.http;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.ForbiddenException;
import com.currencycloud.client.model.*;
import com.currencycloud.examples.CurrencyCloudCookbook;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * This test executes actual http calls to the demo server and shouldn't be run when unit tests are run. Run when
 * needed only.
 */
@Ignore
public class DemoServerTest {

    private static final Logger log = LoggerFactory.getLogger(DemoServerTest.class);

    private final SimpleDateFormat dateFormat;

    private CurrencyCloudClient currencyCloud = new CurrencyCloudClient(
            CurrencyCloudClient.Environment.demo,
            "rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0"
    );

    public DemoServerTest() {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void testCookbook() throws Exception {
        CurrencyCloudCookbook.main();
    }

    /**
     * Test that payment types collection is handled correctly.
     * */
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
            Map<String, List<ErrorMessage>> msgs = e.getErrorMessages();
            List<ErrorMessage> paymentTypesErrors = msgs.get("payment_types");
            if (paymentTypesErrors != null) {
                for (ErrorMessage paymentTypesError : paymentTypesErrors) {
                    if (Arrays.asList("payment_types_type_is_wrong", "payment_types_not_included_in_list")
                            .contains(paymentTypesError.getCode())) {
                        throw new AssertionError(paymentTypesError.getMessage());
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
    public void testAccounts() throws Exception {
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

        account.setCountry("SI");
        account = currencyCloud.updateAccount(account); // todo: permission denied
        log.debug("account = {}", account);
    }

    @Test
    public void testCreateAccount() throws Exception {
        currencyCloud.createAccount(Account.create("New Account xyz", "individual")); // todo: permission denied
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
    public void testContacts() throws Exception {
        String accountId = currencyCloud.currentAccount().getId();
        log.debug("accountId = {}", accountId);

        Contact contact;
        try {
            contact = currencyCloud.createContact(
                    Contact.create(
                            accountId, "John Jr.", "Doe", "jdjr@example.com", "555 555 555 555"
                    )
            );

            log.debug("contact = {}", contact);
        } catch (ForbiddenException e) {
            log.warn("Can't create contact: " + e);
        }

        contact = currencyCloud.currentContact();
        log.debug("Current contact = {}", contact);

        try {
            contact.setPhoneNumber("555 666 777 888");
            contact = currencyCloud.updateContact(contact);
        } catch (ForbiddenException e) {
            log.warn("Can't update contact: " + e);
        }

        contact = currencyCloud.retrieveContact(contact.getId());

        List<Contact> contacts = currencyCloud.findContacts(contact, Pagination.first()).getContacts();
        assertFound(contacts, contact);

        contacts = currencyCloud.findContacts(
                Contact.create(accountId, null, null, null, null),
                Pagination.builder().pages(1, 10).build()
        ).getContacts();
        assertFound(contacts, contact);
    }

    @Test
    public void testConversions() throws Exception {
        Date date = getDate("2015-05-28");
        Conversion conversion = Conversion.create(
                "EUR", "GBP", "buy", date, null, null, null, null
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
                null, null, null, null, null, null, null, null, null, null, null, null
        ).getConversions();

        assertFound(conversions, conversion);

        conversions = currencyCloud.findConversions(
                null, Arrays.asList(conversion.getId(), "invalid-id"),
                null, getDate("2100-01-01"),
                getDate("2015-01-01"), null, null, null, null, null,
                null, null, null, new BigDecimal("10000000.00")
        ).getConversions();

        assertFound(conversions, conversion);
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

        Payment payment = Payment.create(
                "EUR", beneficiary.getId(), new BigDecimal("10000"), "Invoice Payment", "Invoice 1234",
                conversion.getId(), null, "regular"
        );
        payment = currencyCloud.createPayment(payment, null);
        log.debug("Created payment = {}", payment);

        payment = currencyCloud.retrievePayment(payment.getId());
        log.debug("Retrieved payment = {}", payment);

        Date from = getDate("2015-01-01");

        List<Payment> payments = currencyCloud.findPayments(
                payment,
                payment.getAmount(), payment.getAmount().add(new BigDecimal("1.00")), null,
                null, null, null, null, null, from, null, null
        ).getPayments();

        assertFound(payments, payment);

        payments = currencyCloud.findPayments(
                null, new BigDecimal("1.00"), payment.getAmount().add(new BigDecimal("1.00")), null,
                null, null, null, from, null, null, null, null
        ).getPayments();

        assertFound(payments, payment);

        Payer payer = currencyCloud.retrievePayer(payment.getPayerId());

        payment.setReason("A changed reason");
        payer.setCity("A different city.");
        currencyCloud.updatePayment(payment, payer);

        currencyCloud.deletePayment(payment.getId()); // fails: At least one payment should be associated with the conversion

        payments = currencyCloud
                .findPayments(payment, null, null, null, null, null, null, null, null, null, null, null)
                .getPayments();

        assertFound(payments, payment, false);
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

        assertThat(transactions, hasSize(greaterThan(0))); // todo: fails
        Transaction transaction = transactions.get(0);

        transaction = currencyCloud.retrieveTransaction(transaction.getId());
        log.debug("transaction = {}", transaction);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private static <T extends HasId> void assertFound(List<T> ts, T t) {
        assertFound(ts, t, true);
    }

    private static <T extends HasId> void assertFound(List<T> ts, T t, boolean expectFound) {
        boolean found = false;
        for (T c : ts) {
            if (c.getId().equals(t.getId())) {
                found = true;
                break;
            }
        }
        assertThat(found, equalTo(expectFound));
    }

    private Date getDate(String str) throws ParseException {
        synchronized (dateFormat) {
            return dateFormat.parse(str);
        }
    }
}
