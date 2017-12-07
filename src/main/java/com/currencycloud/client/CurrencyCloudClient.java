package com.currencycloud.client;

import com.currencycloud.client.dirty.ModificationTracker;
import com.currencycloud.client.dirty.ModifiedValueProvider;
import com.currencycloud.client.exception.CurrencyCloudException;
import com.currencycloud.client.model.*;
import com.currencycloud.client.model.Currency;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.JacksonConfigureListener;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

/**
 * This is the high-lever entry point to the Currency Cloud API. It provides access to the HTTP API while providing
 * support for the following features:
 *
 * <ul>
 *     <li>Automatic lazy authentication and session keeping</li>
 *     <li>Automatic re-authentication after session timeouts</li>
 *     <li>Calling methods on behalf of other users</li>
 *     <li>Making certain client-side checks/validations</li>
 * </ul>
 *
 */
public class CurrencyCloudClient {

    private static final Logger log = LoggerFactory.getLogger(CurrencyCloudClient.class);
    private static final Pattern UUID = Pattern.compile(
            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}",
            Pattern.CASE_INSENSITIVE
    );
    private static final String userAgent = "CurrencyCloudSDK/2.0 Java/0.7.6";


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
        config.setJacksonConfigureListener(
                new JacksonConfigureListener() {
                    @Override
                    public void configureObjectMapper(ObjectMapper objectMapper) {
                        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    }
                }
        );

        api = RestProxyFactory.createProxy(
                CurrencyCloud.class, url, config,
                new AutoAuthenticator(this), new ExceptionTransformer(), new Reauthenticator(this)
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
        authToken = api.authenticate(userAgent, loginId, apiKey).getAuthToken();
    }

    /**
     * Ends a logged in session
     */
    public void endSession() throws CurrencyCloudException {
        api.endSession(userAgent, authToken);
        authToken = null;
    }

    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS ////////////////////////////////////////////////////

    public Account createAccount(Account account) throws CurrencyCloudException {
        return api.createAccount(
                authToken,
                userAgent,
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
        return api.retrieveAccount(authToken, userAgent, accountId, onBehalfOf);
    }

    public Account updateAccount(Account account) throws CurrencyCloudException {
        try {
            account = wrapIfDirty(account, Account.class);
        } catch (NoChangeException e) {
            return account;
        }
        return api.updateAccount(
                authToken,
                userAgent,
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
        return api.findAccounts(
                authToken,
                userAgent,
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
        return api.currentAccount(authToken, userAgent);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES ////////////////////////////////////////////////////

    public Balances findBalances(@Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable Date asAtDate, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findBalances(
                authToken,
                userAgent,
                amountFrom,
                amountTo,
                asAtDate,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Balance retrieveBalance(String currency) throws CurrencyCloudException {
        return api.retrieveBalance(authToken, userAgent, currency);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES ///////////////////////////////////////////////

    public Beneficiary validateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return api.validateBeneficiary(
                authToken,
                userAgent,
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                Utils.join(beneficiary.getBeneficiaryAddress(), "\r\n"),
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
                userAgent,
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                beneficiary.getName(),
                beneficiary.getEmail(),
                Utils.join(beneficiary.getBeneficiaryAddress(), "\r\n"),
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
        return api.retrieveBeneficiary(authToken, userAgent, id, onBehalfOf);
    }

    public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        try {
            beneficiary = wrapIfDirty(beneficiary, Beneficiary.class);
        } catch (NoChangeException e) {
            return beneficiary;
        }
        return api.updateBeneficiary(
                authToken,
                userAgent,
                beneficiary.getId(),
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                beneficiary.getName(),
                beneficiary.getEmail(),
                Utils.join(beneficiary.getBeneficiaryAddress(), "\r\n"),
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
                userAgent,
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
        return api.deleteBeneficiary(authToken, userAgent, id, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS ////////////////////////////////////////////////////

    public void createResetToken(@Nullable String loginId) throws ResponseException {
        api.createResetToken(authToken, userAgent, loginId);
    }

    public Contact createContact(Contact contact) throws ResponseException {
        return api.createContact(
                authToken,
                userAgent,
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
        return api.retrieveContact(authToken, userAgent, contactId);
    }

    public Contact updateContact(Contact contact) throws ResponseException {
        try {
            contact = wrapIfDirty(contact, Contact.class);
        } catch (NoChangeException e) {
            return contact;
        }
        return api.updateContact(
                authToken,
                userAgent,
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
                userAgent,
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
        return api.currentContact(authToken, userAgent);
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
                userAgent,
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
                conversion.getUniqueRequestId(),
                onBehalfOf
        );
    }

    public Conversion retrieveConversion(String conversionId) throws CurrencyCloudException {
        return api.retrieveConversion(authToken, userAgent, conversionId);
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
            @Nullable BigDecimal sellAmountTo,
            @Nullable String uniqueRequestId
    ) throws CurrencyCloudException {
        if (example == null) {
            example = Conversion.create();
        }
        return api.findConversions(
                authToken,
                userAgent,
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
                uniqueRequestId,
                onBehalfOf
        );
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYERS ///////////////////////////////////////////////////////

    public Payer retrievePayer(String payerId) throws CurrencyCloudException {
        return api.retrievePayer(authToken, userAgent, payerId);
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYMENTS ////////////////////////////////////////////////////

    public Payment createPayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        if (payer == null) {
            payer = Payer.create();
        }
        return api.createPayment(authToken,
                                 userAgent,
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
                                 flattenList(payer.getAddress()),
                                 payer.getCity(),
                                 payer.getCountry(),
                                 payer.getPostcode(),
                                 payer.getStateOrProvince(),
                                 dateOnly(payer.getDateOfBirth()),
                                 payer.getIdentificationType(),
                                 payer.getIdentificationValue(),
                                 payment.getUniqueRequestId(),
                                 onBehalfOf
        );
    }

    public Payment retrievePayment(String id) throws CurrencyCloudException {
        return api.retrievePayment(authToken, userAgent, id, onBehalfOf);
    }

    public Payment updatePayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        if (payer == null) {
            payer = Payer.create();
        }
        try {
            payment = wrapIfDirty(payment, Payment.class);
            payer = wrapIfDirty(payer, Payer.class);
        } catch (NoChangeException e) {
            return payment;
        }
        return api.updatePayment(
                authToken,
                userAgent,
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
                flattenList(payer.getAddress()),
                payer.getCity(),
                payer.getCountry(),
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
                                 @Nullable Pagination pagination,
                                 @Nullable String uniqueRequestId
    ) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (example == null) {
            example = Payment.create();
        }
        return api.findPayments(authToken,
                                userAgent,
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
                                uniqueRequestId,
                                onBehalfOf
        );
    }

    public Payment deletePayment(String paymentId) throws CurrencyCloudException {
        return api.deletePayment(authToken, userAgent, paymentId, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// RATES ///////////////////////////////////////////////////////

    public Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException {
        return api.findRates(authToken, userAgent, currencyPair, ignoreInvalidPairs, onBehalfOf);
    }

    public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException {
        return api.detailedRates(
                authToken,
                userAgent,
                buyCurrency,
                sellCurrency,
                fixedSide,
                amount,
                dateOnly(conversionDate),
                onBehalfOf
        );
    }

    ///////////////////////////////////////////////////////////////////
    ///// REFERENCE ///////////////////////////////////////////////////

    public List<Map<String, String>> beneficiaryRequiredDetails(@Nullable String currency, @Nullable String bankAccountCountry, @Nullable String beneficiaryCountry) throws CurrencyCloudException {
        return api.beneficiaryRequiredDetails(authToken, userAgent, currency, bankAccountCountry, beneficiaryCountry).getDetails();
    }

    public List<Currency> currencies() throws CurrencyCloudException {
        return api.currencies(authToken, userAgent).getCurrencies();
    }

    public ConversionDates conversionDates(String conversionPair, @Nullable Date startDate) throws CurrencyCloudException {
        return api.conversionDates(authToken, userAgent, conversionPair, startDate);
    }
    
    public PaymentDates paymentDates(String currency, @Nullable Date startDate) throws CurrencyCloudException {
        return api.paymentDates(authToken, userAgent, currency, startDate);
    }

    public List<SettlementAccount> settlementAccounts(@Nullable String currency) throws CurrencyCloudException {
        return api.settlementAccounts(authToken, userAgent, currency).getSettlementAccounts();
    }

    ///////////////////////////////////////////////////////////////////
    ///// SETTLEMENTS /////////////////////////////////////////////////

    public Settlement createSettlement() throws CurrencyCloudException {
        return api.createSettlement(authToken, userAgent, onBehalfOf);
    }

    public Settlement retrieveSettlement(String id) throws CurrencyCloudException {
        return api.retrieveSettlement(authToken, userAgent, id, onBehalfOf);
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
                userAgent,
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
        return api.deleteSettlement(authToken, userAgent, settlementId, onBehalfOf);
    }

    public Settlement addConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.addConversion(authToken, userAgent, settlementId, conversionId, onBehalfOf);
    }

    public Settlement removeConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.removeConversion(authToken, userAgent, settlementId, conversionId, onBehalfOf);
    }

    public Settlement releaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.releaseSettlement(authToken, userAgent, settlementId, onBehalfOf);
    }

    public Settlement unreleaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.unreleaseSettlement(authToken, userAgent, settlementId, onBehalfOf);
    }


    ///////////////////////////////////////////////////////////////////
    ///// TRANSACTIONS ////////////////////////////////////////////////

    public Transaction retrieveTransaction(String id) throws CurrencyCloudException {
        return api.retrieveTransaction(authToken, userAgent, id, onBehalfOf);
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
                userAgent,
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

    @Nullable
    private static java.sql.Date dateOnly(@Nullable Date date) {
        return date == null ? null : new java.sql.Date(date.getTime());
    }

    /**
     * @param updated The object to be checked for dirty properties
     * @return The collection of updated properties (may be empty), or null if dirty checking is not enabled for this object.
     */
    @Nullable
    private static Map<String, Object> getDirtyProperties(Object updated) {
        if (updated instanceof Factory) {
            Factory proxy = (Factory) updated;
            for (Callback callback : proxy.getCallbacks()) {
                if (callback instanceof ModificationTracker) {
                    ModificationTracker ModificationTracker = (ModificationTracker) callback;
                    return ModificationTracker.getDirtyProperties();
                }
            }
        }
        log.warn("Can't check if the object is dirty because it was not obtained from the client: {}", updated);
        return null;
    }

    /**
     * @return A proxy object whose getters will return null for unchanged entity's properties
     * (and new values for changed properties), or the entity itself if the entity was not
     * enhanced for dirty checking.
     * @throws com.currencycloud.client.CurrencyCloudClient.NoChangeException if the entity was dirty-checked
     * but there were no changes.
     */
    static <E extends Entity> E wrapIfDirty(E entity, Class<E> entityClass) throws NoChangeException {
        if (entity != null) {
            Map<String, Object> values = getDirtyProperties(entity);
            if (values != null) {
                if (values.isEmpty()) {
                    throw new NoChangeException();
                }
                values = new HashMap<>(values);
                values.put("id", entity.getId());
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(entityClass);
                enhancer.setCallback(new ModifiedValueProvider(values));
                return (E) enhancer.create();
            }
        }
        return entity;
    }

    public enum Environment {
        production("https://api.currencycloud.com"),
        demo("https://devapi.currencycloud.com");
        private final String url;

        Environment(String url) {
            this.url = url;
        }
    }

    private static class NoChangeException extends Exception { }

    /**
     * Flattens list of string objects into a comma separated string in Java 1.7 and earlier.
     * If the list is empty then null is returned.
     *
     * @param stringArray List of strings
     * @return a flattened comma separated string object.
     */
    private static String flattenList(List<String> stringArray) {
        if (stringArray!= null && stringArray.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            String resultString;
            for (String string : stringArray) {
                stringBuilder.append(string);
                stringBuilder.append(",");
            }
            resultString = stringBuilder.toString();
            resultString = resultString.substring(0, resultString.length() - ",".length());
            return resultString;
        } else {
            return null;
        }
    }
}
