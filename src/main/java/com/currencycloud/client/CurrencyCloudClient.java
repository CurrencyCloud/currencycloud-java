package com.currencycloud.client;

import com.currencycloud.client.exception.CurrencyCloudException;
import com.currencycloud.client.model.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.JacksonConfigureListener;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CurrencyCloudClient {

    private static final Pattern UUID = Pattern.compile("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}", Pattern.CASE_INSENSITIVE);

    private final CurrencyCloud api;

    private String onBehalfOf = null;

    private String loginId;
    private String apiKey;
    private String authToken;

    public CurrencyCloudClient(Environment environment, String loginId, String apiKey) {
        this(environment.url, loginId, apiKey);
    }

    CurrencyCloudClient(String url, String loginId, String apiKey) {
        this.loginId = loginId;
        this.apiKey = apiKey;
        ClientConfig config = new ClientConfig();
        config.setJacksonConfigureListener(new JacksonConfigureListener() {
            @Override
            public void configureObjectMapper(ObjectMapper objectMapper) {
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            }
        });

        api = RestProxyFactory.createProxy(
                CurrencyCloud.class, url, config,
                new AutoAuthenticate(this), new HttpStatusExceptionInterceptor(), new ReauthenticateInterceptor(this)
        );
    }

    public String getLoginId() {
        return loginId;
    }

    void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    String getAuthToken() {
        return authToken;
    }


    ///////////////////////////////////////////////////////////////////
    ///// ON BEHALF OF ////////////////////////////////////////////////

    /**
     * Performs the work on behalf of another user.
     *
     * @param contactId contactId of the user
     * @param work      the work to do
     * @throws CurrencyCloudException   if work throws it
     * @throws IllegalStateException    if onBehalfOf is already set (nested call to this method)
     * @throws IllegalArgumentException if onBehalfOf is in illegal format
     */
    public void onBehalfOfDo(String contactId, Runnable work)
            throws IllegalArgumentException, IllegalStateException, CurrencyCloudException {
        if (!UUID.matcher(contactId).matches()) {
            throw new IllegalArgumentException("Contact id for onBehalfOf is not a UUID");
        }
        if (this.onBehalfOf != null) {
            throw new IllegalStateException("Can't nest on-behalf-of calls: " + this.onBehalfOf);
        }
        this.onBehalfOf = contactId;
        try {
            work.run();
        } finally {
            this.onBehalfOf = null;
        }
    }

    String getOnBehalfOf() {
        return onBehalfOf;
    }

    ///////////////////////////////////////////////////////////////////
    ///// AUTHENTICATE ////////////////////////////////////////////////

    /**
     * Starts a logged in session
     */
    void authenticate() throws CurrencyCloudException {
        if (loginId == null || apiKey == null) {
            throw new IllegalArgumentException("Both loginId and apiKey must be set.");
        }
        authToken = null;
        authToken = api.authenticate(loginId, apiKey).getAuthToken();
    }

    /**
     * Ends a logged in session
     */
    public void endSession() throws CurrencyCloudException {
        api.endSession(authToken);
        authToken = null;
    }

    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS ////////////////////////////////////////////////////

    public Account createAccount(Account account) throws CurrencyCloudException {
        return api.createAccount(
                authToken,
                account.getAccountName(),
                account.getLegalEntityType(),
                account.getYourReference(),
                account.getStatus(),
                account.getStreet(),
                account.getCity(),
                account.getStateOrProvince(),
                account.getPostalCode(),
                account.getCountry(),
                account.getSpreadTable(),
                account.getIdentificationType(),
                account.getIdentificationValue()
        );
    }

    public Account retrieveAccount(String accountId) throws CurrencyCloudException {
        return api.retrieveAccount(authToken, accountId, onBehalfOf);
    }

    public Account updateAccount(Account account) throws CurrencyCloudException {
        return api.updateAccount(authToken,
                                 account.getId(),
                                 account.getAccountName(),
                                 account.getLegalEntityType(),
                                 account.getYourReference(),
                                 account.getStatus(),
                                 account.getStreet(),
                                 account.getCity(),
                                 account.getStateOrProvince(),
                                 account.getPostalCode(),
                                 account.getCountry(),
                                 account.getSpreadTable(),
                                 account.getIdentificationType(),
                                 account.getIdentificationValue()
        );
    }

    /**
     * @param example Those properties that are not null in example will be used as filters; null values will be ignored.
     */
    public Accounts findAccounts(@Nullable Account example, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Account.create();
        }
        return api.findAccounts(authToken,
                                example.getAccountName(),
                                example.getBrand(),
                                example.getYourReference(),
                                example.getStatus(),
                                example.getStreet(),
                                example.getCity(),
                                example.getStateOrProvince(),
                                example.getPostalCode(),
                                example.getCountry(),
                                example.getSpreadTable(),
                                pagination.getPage(),
                                pagination.getPerPage(),
                                pagination.getOrder(),
                                pagination.getOrderAscDesc()
        );
    }

    public Account currentAccount() throws CurrencyCloudException {
        return api.currentAccount(authToken);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES ////////////////////////////////////////////////////

    public Balances findBalances(@Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable Date asAtDate, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findBalances(authToken, amountFrom, amountTo, asAtDate, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc());
    }

    public Balance retrieveBalance(String currency) throws CurrencyCloudException {
        return api.retrieveBalance(authToken, currency);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES ///////////////////////////////////////////////

    public Beneficiary validateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return api.validateBeneficiary(
                authToken,
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                beneficiary.getBeneficiaryCountry(),
                beneficiary.getAccountNumber(),
                beneficiary.getRoutingCodeType1(),
                beneficiary.getRoutingCodeValue1(),
                beneficiary.getRoutingCodeType2(),
                beneficiary.getRoutingCodeValue2(),
                beneficiary.getBicSwift(),
                beneficiary.getIban(),
                beneficiary.getBankAddress(),
                beneficiary.getBankName(),
                beneficiary.getBankAccountType(),
                beneficiary.getBeneficiaryEntityType(),
                beneficiary.getBeneficiaryCompanyName(),
                beneficiary.getBeneficiaryFirstName(),
                beneficiary.getBeneficiaryLastName(),
                beneficiary.getBeneficiaryCity(),
                beneficiary.getBeneficiaryPostcode(),
                beneficiary.getBeneficiaryStateOrProvince(),
                beneficiary.getBeneficiaryDateOfBirth(),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes(),
                onBehalfOf
        );
    }

    public Beneficiary createBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return api.createBeneficiary(
                authToken,
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                beneficiary.getName(),
                beneficiary.getEmail(),
                join(beneficiary.getBeneficiaryAddress()),
                beneficiary.getBeneficiaryCountry(),
                beneficiary.getAccountNumber(),
                beneficiary.getRoutingCodeType1(),
                beneficiary.getRoutingCodeValue1(),
                beneficiary.getRoutingCodeType2(),
                beneficiary.getRoutingCodeValue2(),
                beneficiary.getBicSwift(),
                beneficiary.getIban(),
                beneficiary.getDefaultBeneficiary(),
                beneficiary.getBankAddress(),
                beneficiary.getBankName(),
                beneficiary.getBankAccountType(),
                beneficiary.getBeneficiaryEntityType(),
                beneficiary.getBeneficiaryCompanyName(),
                beneficiary.getBeneficiaryFirstName(),
                beneficiary.getBeneficiaryLastName(),
                beneficiary.getBeneficiaryCity(),
                beneficiary.getBeneficiaryPostcode(),
                beneficiary.getBeneficiaryStateOrProvince(),
                beneficiary.getBeneficiaryDateOfBirth(),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes(),
                onBehalfOf
        );
    }

    public Beneficiary retrieveBeneficiary(String id) throws CurrencyCloudException {
        return api.retrieveBeneficiary(authToken, id, onBehalfOf);
    }

    public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return api.updateBeneficiary(
                authToken,
                beneficiary.getId(),
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                beneficiary.getName(),
                beneficiary.getEmail(),
                join(beneficiary.getBeneficiaryAddress()),
                beneficiary.getBeneficiaryCountry(),
                beneficiary.getAccountNumber(),
                beneficiary.getRoutingCodeType1(),
                beneficiary.getRoutingCodeValue1(),
                beneficiary.getRoutingCodeType2(),
                beneficiary.getRoutingCodeValue2(),
                beneficiary.getBicSwift(),
                beneficiary.getIban(),
                beneficiary.getDefaultBeneficiary(),
                beneficiary.getBankAddress(),
                beneficiary.getBankName(),
                beneficiary.getBankAccountType(),
                beneficiary.getBeneficiaryEntityType(),
                beneficiary.getBeneficiaryCompanyName(),
                beneficiary.getBeneficiaryFirstName(),
                beneficiary.getBeneficiaryLastName(),
                beneficiary.getBeneficiaryCity(),
                beneficiary.getBeneficiaryPostcode(),
                beneficiary.getBeneficiaryStateOrProvince(),
                beneficiary.getBeneficiaryDateOfBirth(),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes(),
                onBehalfOf
        );
    }

    /**
     *
     * @param example    The non-null properties of the example will be used for querying.
     *                   Use routingCodeType1 and routingCodeValue1 (the *2 fields are ignored).
     * @param pagination pagination settings
     * @return           The paginated Beneficiaries search results
     * @throws CurrencyCloudException When an error occurs
     */
    public Beneficiaries findBeneficiaries(@Nullable Beneficiary example, @Nullable Pagination pagination)
            throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Beneficiary.create();
        }
        return api.findBeneficiaries(
                authToken,
                example.getBankAccountHolderName(),
                example.getBeneficiaryCountry(),
                example.getCurrency(),
                example.getAccountNumber(),
                example.getRoutingCodeType1(),
                example.getRoutingCodeValue1(),
                example.getPaymentTypes(),
                example.getBicSwift(),
                example.getIban(),
                example.getDefaultBeneficiary(),
                example.getBankName(),
                example.getBankAccountType(),
                example.getName(),
                example.getBeneficiaryEntityType(),
                example.getBeneficiaryCompanyName(),
                example.getBeneficiaryFirstName(),
                example.getBeneficiaryLastName(),
                example.getBeneficiaryCity(),
                example.getBeneficiaryPostcode(),
                example.getBeneficiaryStateOrProvince(),
                example.getBeneficiaryDateOfBirth(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc(),
                onBehalfOf
        );
    }

    public Beneficiary firstBeneficiary(@Nullable Beneficiary beneficiary) throws CurrencyCloudException {
        return findBeneficiaries(beneficiary, Pagination.first()).getBeneficiaries().iterator().next();
    }

    public Beneficiary deleteBeneficiary(String id) throws CurrencyCloudException {
        return api.deleteBeneficiary(authToken, id, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS ////////////////////////////////////////////////////

    public void createResetToken(@Nullable String loginId) throws ResponseException {
        api.createResetToken(authToken, loginId);
    }

    public Contact createContact(Contact contact) throws ResponseException {
        return api.createContact(
                authToken,
                contact.getAccountId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmailAddress(),
                contact.getPhoneNumber(),
                contact.getYourReference(),
                contact.getMobilePhoneNumber(),
                contact.getLoginId(),
                contact.getStatus(),
                contact.getLocale(),
                contact.getTimezone(),
                dateOnly(contact.getDateOfBirth())
        );
    }

    public Contact retrieveContact(String contactId) throws ResponseException {
        return api.retrieveContact(authToken, contactId);
    }

    public Contact updateContact(Contact contact) throws ResponseException {
        return api.updateContact(
                authToken,
                contact.getId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmailAddress(),
                contact.getPhoneNumber(),
                contact.getYourReference(),
                contact.getMobilePhoneNumber(),
                contact.getLoginId(),
                contact.getStatus(),
                contact.getLocale(),
                contact.getTimezone(),
                dateOnly(contact.getDateOfBirth())
        );
    }

    public Contacts findContacts(Contact example, Pagination pagination) throws ResponseException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Contact.create();
        }
        return api.findContacts(
                authToken,
                example.getAccountName(),
                example.getAccountId(),
                example.getFirstName(),
                example.getLastName(),
                example.getEmailAddress(),
                example.getYourReference(),
                example.getPhoneNumber(),
                example.getLoginId(),
                example.getStatus(),
                example.getLocale(),
                example.getTimezone(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Contact currentContact() throws ResponseException {
        return api.currentContact(authToken);
    }


    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS /////////////////////////////////////////////////

    public Conversion createConversion(
            Conversion conversion,
            BigDecimal amount,
            String reason,
            Boolean termAgreement
    ) throws CurrencyCloudException {
        return api.createConversion(
                authToken,
                conversion.getBuyCurrency(),
                conversion.getSellCurrency(),
                conversion.getFixedSide(),
                amount,
                reason,
                termAgreement,
                conversion.getConversionDate(),
                conversion.getClientRate(),
                conversion.getCurrencyPair(),
                conversion.getClientBuyAmount(),
                conversion.getClientSellAmount(),
                onBehalfOf
        );
    }

    public Conversion retrieveConversion(String conversionId) throws CurrencyCloudException {
        return api.retrieveConversion(authToken, conversionId);
    }

    public Conversions findConversions(
            @Nullable Conversion example,
            @Nullable Collection<String> conversionIds,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable BigDecimal partnerBuyAmountFrom,
            @Nullable BigDecimal partnerBuyAmountTo,
            @Nullable BigDecimal partnerSellAmountFrom,
            @Nullable BigDecimal partnerSellAmountTo,
            @Nullable BigDecimal buyAmountFrom,
            @Nullable BigDecimal buyAmountTo,
            @Nullable BigDecimal sellAmountFrom,
            @Nullable BigDecimal sellAmountTo
    ) throws CurrencyCloudException {
        if (example == null) {
            example = Conversion.create();
        }
        return api.findConversions(
                authToken,
                example.getShortReference(),
                example.getStatus(),
                example.getPartnerStatus(),
                example.getBuyCurrency(),
                example.getSellCurrency(),
                conversionIds,
                createdAtFrom,
                createdAtTo,
                updatedAtFrom,
                updatedAtTo,
                example.getCurrencyPair(),
                partnerBuyAmountFrom,
                partnerBuyAmountTo,
                partnerSellAmountFrom,
                partnerSellAmountTo,
                buyAmountFrom,
                buyAmountTo,
                sellAmountFrom,
                sellAmountTo,
                onBehalfOf
        );
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYERS ///////////////////////////////////////////////////////

    public Payer retrievePayer(String payerId) throws CurrencyCloudException {
        return api.retrievePayer(authToken, payerId);
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYMENTS ////////////////////////////////////////////////////

    public Payment createPayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        if (payer == null) {
            payer = Payer.create();
        }
        return api.createPayment(authToken,
                                 payment.getCurrency(),
                                 payment.getBeneficiaryId(),
                                 payment.getAmount(),
                                 payment.getReason(),
                                 payment.getReference(),
                                 dateOnly(payment.getPaymentDate()),
                                 payment.getPaymentType(),
                                 payment.getConversionId(),
                                 payer.getLegalEntityType(),
                                 payer.getCompanyName(),
                                 payer.getFirstName(),
                                 payer.getLastName(),
                                 payer.getCity(),
                                 payer.getPostcode(),
                                 payer.getStateOrProvince(),
                                 dateOnly(payer.getDateOfBirth()),
                                 payer.getIdentificationType(),
                                 payer.getIdentificationValue(),
                                 onBehalfOf
        );
    }

    public Payment retrievePayment(String id) throws CurrencyCloudException {
        return api.retrievePayment(authToken, id, onBehalfOf);
    }

    public Payment updatePayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        if (payer == null) {
            payer = Payer.create();
        }
        return api.updatePayment(authToken,
                                 payment.getId(),
                                 payment.getCurrency(),
                                 payment.getBeneficiaryId(),
                                 payment.getAmount(),
                                 payment.getReason(),
                                 payment.getReference(),
                                 dateOnly(payment.getPaymentDate()),
                                 payment.getPaymentType(),
                                 payment.getConversionId(),
                                 payer.getLegalEntityType(),
                                 payer.getCompanyName(),
                                 payer.getFirstName(),
                                 payer.getLastName(),
                                 payer.getCity(),
                                 payer.getPostcode(),
                                 payer.getStateOrProvince(),
                                 dateOnly(payer.getDateOfBirth()),
                                 payer.getIdentificationType(),
                                 payer.getIdentificationValue(),
                                 onBehalfOf
        );
    }

    public Payments findPayments(@Nullable Payment example,
                                 @Nullable BigDecimal amountFrom,
                                 @Nullable BigDecimal amountTo,
                                 @Nullable Date paymentDateFrom,
                                 @Nullable Date paymentDateTo,
                                 @Nullable Date transferredAtFrom,
                                 @Nullable Date transferredAtTo,
                                 @Nullable Date createdAtFrom,
                                 @Nullable Date createdAtTo,
                                 @Nullable Date updatedAtFrom,
                                 @Nullable Date updatedAtTo,
                                 @Nullable Pagination pagination
    ) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Payment.create();
        }
        return api.findPayments(authToken,
                                example.getShortReference(),
                                example.getCurrency(),
                                example.getAmount(),
                                amountFrom,
                                amountTo,
                                example.getStatus(),
                                example.getReason(),
                                dateOnly(paymentDateFrom),
                                dateOnly(paymentDateTo),
                                transferredAtFrom,
                                transferredAtTo,
                                createdAtFrom,
                                createdAtTo,
                                updatedAtFrom,
                                updatedAtTo,
                                example.getBeneficiaryId(),
                                example.getConversionId(),
                                pagination.getPage(),
                                pagination.getPerPage(),
                                pagination.getOrder(),
                                pagination.getOrderAscDesc(),
                                onBehalfOf
        );
    }

    public Payment deletePayment(String paymentId) throws CurrencyCloudException {
        return api.deletePayment(authToken, paymentId, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// RATES ///////////////////////////////////////////////////////

    public Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException {
        return api.findRates(authToken, currencyPair, ignoreInvalidPairs, onBehalfOf);
    }

    public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException {
        return api.detailedRates(authToken, buyCurrency, sellCurrency, fixedSide, amount, dateOnly(conversionDate), onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// REFERENCE ///////////////////////////////////////////////////

    public List<Map<String, String>> beneficiaryRequiredDetails(@Nullable String currency, @Nullable String bankAccountCountry, @Nullable String beneficiaryCountry) throws CurrencyCloudException {
        return api.beneficiaryRequiredDetails(authToken, currency, bankAccountCountry, beneficiaryCountry).getDetails();
    }

    public List<Currency> currencies() throws CurrencyCloudException {
        return api.currencies(authToken).getCurrencies();
    }

    public ConversionDates conversionDates(String conversionPair, @Nullable Date startDate) throws CurrencyCloudException {
        return api.conversionDates(authToken, conversionPair, startDate);
    }

    public List<SettlementAccount> settlementAccounts(@Nullable String currency) throws CurrencyCloudException {
        return api.settlementAccounts(authToken, currency).getSettlementAccounts();
    }

    ///////////////////////////////////////////////////////////////////
    ///// SETTLEMENTS /////////////////////////////////////////////////

    public Settlement createSettlement() throws CurrencyCloudException {
        return api.createSettlement(authToken, onBehalfOf);
    }

    public Settlement retrieveSettlement(String id) throws CurrencyCloudException {
        return api.retrieveSettlement(authToken, id, onBehalfOf);
    }

    public Settlements findSettlements(
            @Nullable String shortReference,
            @Nullable String status,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable Date releasedAtFrom,
            @Nullable Date releasedAtTo,
            @Nullable Pagination pagination
    ) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findSettlements(
                authToken,
                shortReference,
                status,
                createdAtFrom,
                createdAtTo,
                updatedAtFrom,
                updatedAtTo,
                releasedAtFrom,
                releasedAtTo,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc(),
                onBehalfOf
        );
    }

    public Settlement deleteSettlement(String settlementId) throws CurrencyCloudException {
        return api.deleteSettlement(authToken, settlementId, onBehalfOf);
    }

    public Settlement addConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.addConversion(authToken, settlementId, conversionId, onBehalfOf);
    }

    public Settlement removeConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.removeConversion(authToken, settlementId, conversionId, onBehalfOf);
    }

    public Settlement releaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.releaseSettlement(authToken, settlementId, onBehalfOf);
    }

    public Settlement unreleaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.unreleaseSettlement(authToken, settlementId, onBehalfOf);
    }


    ///////////////////////////////////////////////////////////////////
    ///// TRANSACTIONS ////////////////////////////////////////////////

    public Transaction retrieveTransaction(String id) throws CurrencyCloudException {
        return api.retrieveTransaction(authToken, id, onBehalfOf);
    }

    public Transactions findTransactions(
            @Nullable Transaction example,
            @Nullable BigDecimal amountFrom,
            @Nullable BigDecimal amountTo,
            @Nullable Date settlesAtFrom,
            @Nullable Date settlesAtTo,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable Pagination pagination
    ) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Transaction.create();
        }
        return api.findTransactions(
                authToken,
                example.getCurrency(),
                example.getAmount(),
                amountFrom,
                amountTo,
                example.getAction(),
                example.getRelatedEntityType(),
                example.getRelatedEntityId(),
                example.getRelatedEntityShortReference(),
                example.getStatus(),
                example.getType(),
                example.getReason(),
                dateOnly(settlesAtFrom),
                dateOnly(settlesAtTo),
                dateOnly(createdAtFrom),
                dateOnly(createdAtTo),
                dateOnly(updatedAtFrom),
                dateOnly(updatedAtTo),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc(),
                onBehalfOf
        );
    }
    

    ///////////////////////////////////////////////////////////////////

    static String join(List<String> strings) {
        if (strings == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String string : strings) {
            if (sb.length() > 0) {
                sb.append("\r\n");
            }
            sb.append(string);
        }
        return sb.toString();
    }

    @Nullable
    private static java.sql.Date dateOnly(@Nullable Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    public enum Environment {
        production("https://api.thecurrencycloud.com"),
        demo("https://devapi.thecurrencycloud.com");
        private final String url;

        Environment(String url) {
            this.url = url;
        }
    }

}
