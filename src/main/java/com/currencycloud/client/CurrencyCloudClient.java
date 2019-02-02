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
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

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
    private static final String userAgent = "CurrencyCloudSDK/2.0 Java/1.8.1";

    private final CurrencyCloud api;

    // use threadlocal
    private ThreadLocal<String> onBehalfOf = new ThreadLocal<>();

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

        config.setJacksonObjectMapperFactory(
                new JacksonObjectMapperFactory() {
                    @Override
                    public ObjectMapper createObjectMapper() {
                        return new DefaultJacksonObjectMapperFactory()
                                .createObjectMapper()
                                .configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
                    }

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
    public void onBehalfOfDo(String contactId, Runnable work) throws IllegalArgumentException, IllegalStateException, CurrencyCloudException {
        if (!UUID.matcher(contactId).matches()) {
            throw new IllegalArgumentException("Contact id for onBehalfOf is not a UUID");
        }
        if (getOnBehalfOf() != null) {
            throw new IllegalStateException("Can't nest on-behalf-of calls: " + getOnBehalfOf());
        }
        this.onBehalfOf.set(contactId);
        try {
            work.run();
        } finally {
            this.onBehalfOf.remove();
        }
    }

    String getOnBehalfOf() {
        return onBehalfOf.get();
    }

    ///////////////////////////////////////////////////////////////////
    ///// AUTHENTICATE ////////////////////////////////////////////////

    /**
     * Starts a logged in session
     */
    public void authenticate() throws CurrencyCloudException {
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
                account.getStreet(),
                account.getCity(),
                account.getPostalCode(),
                account.getCountry(),
                account.getStateOrProvince(),
                account.getBrand(),
                account.getYourReference(),
                account.getStatus(),
                account.getSpreadTable(),
                account.getIdentificationType(),
                account.getIdentificationValue(),
                account.getApiTrading(),
                account.getOnlineTrading(),
                account.getPhoneTrading()
        );
    }

    public Account retrieveAccount(String accountId) throws CurrencyCloudException {
        return api.retrieveAccount(
                authToken,
                userAgent,
                accountId,
                getOnBehalfOf()
        );
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
     * @param account     Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Accounts search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Accounts findAccounts(@Nullable Account account, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (account == null) {
            account = Account.create();
        }
        return api.findAccounts(
                authToken,
                userAgent,
                account.getAccountName(),
                account.getLegalEntityType(),
                account.getBrand(),
                account.getYourReference(),
                account.getStatus(),
                account.getStreet(),
                account.getCity(),
                account.getStateOrProvince(),
                account.getPostalCode(),
                account.getCountry(),
                account.getSpreadTable(),
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

    /**
     * @param balance     Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Balances search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Balances findBalances(@Nullable Balance balance, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (balance == null) {
            balance = Balance.create();
        }
        return api.findBalances(
                authToken,
                userAgent,
                getOnBehalfOf(),
                balance.getAmountFrom(),
                balance.getAmountTo(),
                balance.getAsAtDate(),
                balance.getScope(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Balance retrieveBalance(String currency) throws CurrencyCloudException {
        return api.retrieveBalance(
                authToken,
                userAgent,
                currency,
                getOnBehalfOf()
        );
    }

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES ///////////////////////////////////////////////

    public Beneficiary validateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return api.validateBeneficiary(
                authToken,
                userAgent,
                beneficiary.getBankCountry(),
                beneficiary.getCurrency(),
                getOnBehalfOf(),
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
                dateOnly(beneficiary.getBeneficiaryDateOfBirth()),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes()
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
                getOnBehalfOf(),
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
                dateOnly(beneficiary.getBeneficiaryDateOfBirth()),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes()
        );
    }

    public Beneficiary retrieveBeneficiary(String id) throws CurrencyCloudException {
        return api.retrieveBeneficiary(authToken, userAgent, id, getOnBehalfOf());
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
                getOnBehalfOf(),
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
                dateOnly(beneficiary.getBeneficiaryDateOfBirth()),
                beneficiary.getBeneficiaryIdentificationType(),
                beneficiary.getBeneficiaryIdentificationValue(),
                beneficiary.getPaymentTypes()
        );
    }

    /**
     *
     * @param beneficiary Non-null properties will be used for querying. Null values will be ignored.
     *                    Use routingCodeType1 and routingCodeValue1 (the *2 fields are ignored).
     * @param pagination  pagination settings
     * @return            The paginated Beneficiaries search results
     * @throws            CurrencyCloudException When an error occurs
     */
    public Beneficiaries findBeneficiaries(@Nullable Beneficiary beneficiary, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (beneficiary == null) {
            beneficiary = Beneficiary.create();
        }
        return api.findBeneficiaries(
                authToken,
                userAgent,
                getOnBehalfOf(),
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBeneficiaryCountry(),
                beneficiary.getCurrency(),
                beneficiary.getAccountNumber(),
                beneficiary.getRoutingCodeType1(),
                beneficiary.getRoutingCodeValue1(),
                beneficiary.getPaymentTypes(),
                beneficiary.getBicSwift(),
                beneficiary.getIban(),
                beneficiary.getDefaultBeneficiary(),
                beneficiary.getBankName(),
                beneficiary.getBankAccountType(),
                beneficiary.getName(),
                beneficiary.getBeneficiaryEntityType(),
                beneficiary.getBeneficiaryCompanyName(),
                beneficiary.getBeneficiaryFirstName(),
                beneficiary.getBeneficiaryLastName(),
                beneficiary.getBeneficiaryCity(),
                beneficiary.getBeneficiaryPostcode(),
                beneficiary.getBeneficiaryStateOrProvince(),
                dateOnly(beneficiary.getBeneficiaryDateOfBirth()),
                beneficiary.getScope(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Beneficiary deleteBeneficiary(String id) throws CurrencyCloudException {
        return api.deleteBeneficiary(authToken, userAgent, id, getOnBehalfOf());
    }

    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS ////////////////////////////////////////////////////

    public Contact createContact(Contact contact) throws CurrencyCloudException {
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

    public Contact retrieveContact(String contactId) throws CurrencyCloudException {
        return api.retrieveContact(authToken, userAgent, contactId);
    }

    public Contact updateContact(Contact contact) throws CurrencyCloudException {
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

    /**
     * @param contact     Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Contacts search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Contacts findContacts(Contact contact, Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (contact == null) {
            contact = Contact.create();
        }
        return api.findContacts(
                authToken,
                userAgent,
                contact.getAccountName(),
                contact.getAccountId(),
                contact.getFirstName(),
                contact.getLastName(),
                contact.getEmailAddress(),
                contact.getYourReference(),
                contact.getPhoneNumber(),
                contact.getLoginId(),
                contact.getStatus(),
                contact.getLocale(),
                contact.getTimezone(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Contact currentContact() throws CurrencyCloudException {
        return api.currentContact(authToken, userAgent);
    }

    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS /////////////////////////////////////////////////

    public Conversion createConversion(
            Conversion conversion
    ) throws CurrencyCloudException {
        return api.createConversion(
                authToken,
                userAgent,
                conversion.getBuyCurrency(),
                conversion.getSellCurrency(),
                conversion.getFixedSide(),
                conversion.getAmount(),
                conversion.getTermAgreement(),
                getOnBehalfOf(),
                conversion.getConversionDate(),
                conversion.getClientBuyAmount(),
                conversion.getClientSellAmount(),
                conversion.getReason(),
                conversion.getUniqueRequestId()
        );
    }

    public Conversion retrieveConversion(String conversionId) throws CurrencyCloudException {
        return api.retrieveConversion(authToken, userAgent, conversionId);
    }

    /**
     * @param conversion  Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Conversions search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Conversions findConversions(@Nullable Conversion conversion, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (conversion == null) {
            conversion = Conversion.create();
        }
        return api.findConversions(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getShortReference(),
                conversion.getStatus(),
                conversion.getPartnerStatus(),
                conversion.getBuyCurrency(),
                conversion.getSellCurrency(),
                conversion.getConversionIds(),
                conversion.getCreatedAtFrom(),
                conversion.getCreatedAtTo(),
                conversion.getUpdatedAtFrom(),
                conversion.getUpdatedAtTo(),
                conversion.getConversionDateFrom(),
                conversion.getConversionDateTo(),
                conversion.getCurrencyPair(),
                conversion.getPartnerBuyAmountFrom(),
                conversion.getPartnerBuyAmountTo(),
                conversion.getPartnerSellAmountFrom(),
                conversion.getPartnerSellAmountTo(),
                conversion.getBuyAmountFrom(),
                conversion.getBuyAmountTo(),
                conversion.getSellAmountFrom(),
                conversion.getSellAmountTo(),
                conversion.getScope(),
                conversion.getSettlementDateFrom(),
                conversion.getSettlementDateTo(),
                conversion.getUniqueRequestId(),
                conversion.getBulkUploadId(),
                conversion.getUnallocatedFunds(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public ConversionCancellation cancelConversion(ConversionCancellation conversion) throws CurrencyCloudException {
        return api.cancelConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getId(),
                conversion.getNotes());
    }

    public ConversionCancellationQuote quoteCancelConversion(ConversionCancellationQuote conversion) throws CurrencyCloudException {
        return api.quoteCancelConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getId());
    }

    public ConversionDateChange quoteChangeDateConversion(ConversionDateChange conversionDateChange) throws CurrencyCloudException {
        return api.quoteChangeDateConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversionDateChange.getId(),
                conversionDateChange.getNewSettlementDate());
    }

    public ConversionDateChange changeDateConversion(ConversionDateChange conversionDateChange) throws CurrencyCloudException {
        return api.changeDateConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversionDateChange.getId(),
                conversionDateChange.getNewSettlementDate());
    }

    public ConversionDateChangeDetails changeDateDetailsConversion(ConversionDateChangeDetails dateChange) throws CurrencyCloudException {
        return api.changeDateDetailsConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                dateChange.getId());
    }

    public ConversionSplit previewSplitConversion(ConversionSplit conversion) throws CurrencyCloudException {
        return api.previewSplitConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getId(),
                conversion.getAmount());
    }

    public ConversionSplit splitConversion(ConversionSplit conversion) throws CurrencyCloudException {
        return api.splitConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getId(),
                conversion.getAmount());
    }

    public ConversionSplitHistory historySplitConversion(ConversionSplitHistory conversion) throws CurrencyCloudException {
        return api.historySplitConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversion.getId());
    }

    public ConversionProfitAndLosses retrieveProfitAndLossConversion(@Nullable ConversionProfitAndLoss profitAndLoss, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (profitAndLoss == null) {
            profitAndLoss = ConversionProfitAndLoss.create();
        }
        return api.retrieveProfitAndLossConversion(
                authToken,
                userAgent,
                getOnBehalfOf(),
                profitAndLoss.getAccountId(),
                profitAndLoss.getContactId(),
                profitAndLoss.getConversionId(),
                profitAndLoss.getEventType(),
                profitAndLoss.getEventDateTimeFrom(),
                profitAndLoss.getEventDateTimeTo(),
                profitAndLoss.getAmountFrom(),
                profitAndLoss.getAmountTo(),
                profitAndLoss.getCurrency(),
                profitAndLoss.getScope(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    ///////////////////////////////////////////////////////////////////
    ///// IBANS ///////////////////////////////////////////////////////

    /**
     * @param iban        Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Ibans search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Ibans findIbans(@Nullable Iban iban, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (iban == null) {
            iban = Iban.create();
        }
        return api.findIbans(
                authToken,
                userAgent,
                iban.getScope(),
                iban.getCurrency(),
                iban.getAccountId(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    /**
     * @deprecated as of 1.7.4; use generic {@link #findIbans(Iban, Pagination)} instead.
     * */
    @Deprecated
    public Ibans retrieveSubAccountsIban(String id, Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.retrieveSubAccountsIban(
                authToken,
                userAgent,
                id,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc());
    }

    /**
     * @deprecated as of 1.7.4; use generic {@link #findIbans(Iban, Pagination)} instead.
     * */
    @Deprecated
    public Ibans findSubAccountsIbans(@Nullable Iban iban, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findSubAccountsIbans(
                authToken,
                userAgent,
                iban.getCurrency(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc());
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
        return api.createPayment(
                authToken,
                userAgent,
                payment.getCurrency(),
                payment.getBeneficiaryId(),
                payment.getAmount(),
                payment.getReason(),
                payment.getReference(),
                getOnBehalfOf(),
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
                payment.getUltimateBeneficiaryName(),
                payment.getPurposeCode()
        );
    }

    public PaymentAuthorisations authorisePayment(List<String> paymentIds) throws CurrencyCloudException {
         return api.authorisePayment(
                authToken,
                userAgent,
                paymentIds
        );
    }

    /**
     * @deprecated as of 1.5.1; use {@link #retrievePayment(String, Boolean)} instead.
     * */
    public Payment retrievePayment(String id) throws CurrencyCloudException {
        return api.retrievePayment(authToken, userAgent, id, null, getOnBehalfOf());
    }

    public Payment retrievePayment(String id, @Nullable Boolean withDeleted ) throws CurrencyCloudException {
        return api.retrievePayment(authToken, userAgent, id, withDeleted, getOnBehalfOf());
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
                getOnBehalfOf(),
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
                payment.getPayerDetailsSource(),
                payment.getUltimateBeneficiaryName(),
                payment.getPurposeCode()
        );
    }

    /**
     * @param payment     Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Payments search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Payments findPayments(@Nullable Payment payment, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (payment == null) {
            payment = Payment.create();
        }
        return api.findPayments(
                authToken,
                userAgent,
                getOnBehalfOf(),
                payment.getShortReference(),
                payment.getCurrency(),
                payment.getAmount(),
                payment.getAmountFrom(),
                payment.getAmountTo(),
                payment.getStatus(),
                payment.getReason(),
                dateOnly(payment.getPaymentDateFrom()),
                dateOnly(payment.getPaymentDateTo()),
                payment.getTransferredAtFrom(),
                payment.getTransferredAtTo(),
                payment.getCreatedAtFrom(),
                payment.getCreatedAtTo(),
                payment.getUpdatedAtFrom(),
                payment.getUpdatedAtTo(),
                payment.getBeneficiaryId(),
                payment.getConversionId(),
                payment.getWithDeleted(),
                payment.getPaymentGroupId(),
                payment.getUniqueRequestId(),
                payment.getScope(),
                payment.getBulkUploadId(),
                payment.getPurposeCode(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Payment deletePayment(String paymentId) throws CurrencyCloudException {
        return api.deletePayment(
                authToken,
                userAgent,
                paymentId,
                getOnBehalfOf()
        );
    }

    public PaymentSubmission retrievePaymentSubmission(String id) throws CurrencyCloudException {
        return api.retrievePaymentSubmission(
                authToken,
                userAgent,
                id,
                getOnBehalfOf()
        );
    }

    public PaymentConfirmation retrievePaymentConfirmation(String id) throws CurrencyCloudException {
        return api.retrievePaymentConfirmation(
                authToken,
                userAgent,
                id,
                getOnBehalfOf());
    }

    ///////////////////////////////////////////////////////////////////
    ///// RATES ///////////////////////////////////////////////////////

    public Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException {
        return api.findRates(
                authToken,
                userAgent,
                currencyPair,
                getOnBehalfOf(),
                ignoreInvalidPairs
        );
    }

    public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException {
        return api.detailedRates(
                authToken,
                userAgent,
                buyCurrency,
                sellCurrency,
                fixedSide,
                amount,
                getOnBehalfOf(),
                dateOnly(conversionDate)
        );
    }

    ////////////////////////////////////////////////////////////////////
    ///// REPORTS //////////////////////////////////////////////////////

    /**
     * @param reportRequest Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination     pagination settings
     * @return               The paginated ReportRequest search result
     * @throws               CurrencyCloudException When an error occurs
     */
    public ReportRequests findReportRequests(@Nullable ReportRequest reportRequest, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (reportRequest == null) {
            reportRequest = ReportRequest.create();
        }
        return api.findReportRequests(
                authToken,
                userAgent,
                getOnBehalfOf(),
                reportRequest.getShortReference(),
                reportRequest.getDescription(),
                reportRequest.getAccountId(),
                reportRequest.getContactId(),
                reportRequest.getCreatedAtFrom(),
                reportRequest.getCreatedAtTo(),
                reportRequest.getExpirationDateFrom(),
                reportRequest.getExpirationDateTo(),
                reportRequest.getStatus(),
                reportRequest.getReportType(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public ReportRequest retrieveReportRequests(String id) throws CurrencyCloudException {
        return api.retrieveReportRequests(authToken, userAgent, id, getOnBehalfOf());
    }

    public ConversionReport createConversionReport(ConversionReport conversionReport) throws CurrencyCloudException {
        return api.createConversionReport(
                authToken,
                userAgent,
                getOnBehalfOf(),
                conversionReport.getDescription(),
                conversionReport.getBuyCurrency(),
                conversionReport.getSellCurrency(),
                conversionReport.getClientBuyAmountFrom(),
                conversionReport.getClientBuyAmountTo(),
                conversionReport.getClientSellAmountFrom(),
                conversionReport.getClientSellAmountTo(),
                conversionReport.getPartnerBuyAmountFrom(),
                conversionReport.getPartnerBuyAmountTo(),
                conversionReport.getPartnerSellAmountFrom(),
                conversionReport.getPartnerSellAmountTo(),
                conversionReport.getClientStatus(),
                conversionReport.getPartnerStatus(),
                conversionReport.getConversionDateFrom(),
                conversionReport.getConversionDateTo(),
                conversionReport.getSettlementDateFrom(),
                conversionReport.getSettlementDateTo(),
                conversionReport.getCreatedAtFrom(),
                conversionReport.getCreatedAtTo(),
                conversionReport.getUpdatedAtFrom(),
                conversionReport.getUpdatedAtTo(),
                conversionReport.getUniqueRequestId(),
                conversionReport.getScope()
        );
    }

    public PaymentReport createPaymentReport(PaymentReport paymentReport) throws CurrencyCloudException {
        return api.createPaymentReport(
                authToken,
                userAgent,
                getOnBehalfOf(),
                paymentReport.getDescription(),
                paymentReport.getCurrency(),
                paymentReport.getAmountFrom(),
                paymentReport.getAmountTo(),
                paymentReport.getStatus(),
                paymentReport.getPaymentDateFrom(),
                paymentReport.getPaymentDateTo(),
                paymentReport.getTransferredAtFrom(),
                paymentReport.getTransferredAtTo(),
                paymentReport.getCreatedAtFrom(),
                paymentReport.getCreatedAtTo(),
                paymentReport.getUpdatedAtFrom(),
                paymentReport.getUpdatedAtTo(),
                paymentReport.getBeneficiaryId(),
                paymentReport.getConversionId(),
                paymentReport.getWithDeleted(),
                paymentReport.getPaymentGroupId(),
                paymentReport.getUniqueRequestId(),
                paymentReport.getScope()
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

    public List<SettlementAccount> settlementAccounts(@Nullable String currency, @Nullable String accountId) throws CurrencyCloudException {
        return api.settlementAccounts(authToken, userAgent, currency, accountId).getSettlementAccounts();
    }

    public List<PayerRequiredDetail> payerRequiredDetails(String payerCountry, @Nullable String payerEntityType, @Nullable String paymentType) throws CurrencyCloudException {
        return api.payerRequiredDetails(authToken, userAgent, payerCountry, payerEntityType, paymentType).getPayerRequiredDetails();
    }

    public List<PaymentPurposeCode> paymentPurposeCodes(String currency, String bankAccountCountry, @Nullable String entityType) throws CurrencyCloudException {
        return api.paymentPurposeCodes(authToken, userAgent, currency, bankAccountCountry, entityType).getPurposeCodes();
    }

    ///////////////////////////////////////////////////////////////////
    ///// SENDER //////////////////////////////////////////////////////

    public SenderDetails retrieveSenderDetails(String id) throws CurrencyCloudException {
        return api.retrieveSenderDetails(
                authToken,
                userAgent,
                id,
                getOnBehalfOf());
    }

    ///////////////////////////////////////////////////////////////////
    ///// SETTLEMENTS /////////////////////////////////////////////////

    /**
     * @deprecated as of 1.2.3; use {@link #createSettlement(Settlement)} instead.
     * */
    public Settlement createSettlement() throws CurrencyCloudException {
        return api.createSettlement(authToken, userAgent, getOnBehalfOf());
    }

    public Settlement createSettlement(Settlement settlement) throws CurrencyCloudException {
        return api.createSettlement(authToken, userAgent, getOnBehalfOf(), settlement.getType());
    }

    public Settlement retrieveSettlement(String id) throws CurrencyCloudException {
        return api.retrieveSettlement(authToken, userAgent, id, getOnBehalfOf());
    }

    /**
     * @param settlement  Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Settlements search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Settlements findSettlements(@Nullable Settlement settlement, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (settlement == null) {
            settlement = Settlement.create();
        }
        return api.findSettlements(
                authToken,
                userAgent,
                getOnBehalfOf(),
                settlement.getShortReference(),
                settlement.getStatus(),
                settlement.getCreatedAtFrom(),
                settlement.getCreatedAtTo(),
                settlement.getUpdatedAtFrom(),
                settlement.getUpdatedAtTo(),
                settlement.getReleasedAtFrom(),
                settlement.getReleasedAtTo(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Settlement deleteSettlement(String settlementId) throws CurrencyCloudException {
        return api.deleteSettlement(authToken, userAgent, settlementId, getOnBehalfOf());
    }

    public Settlement addConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.addConversion(authToken, userAgent, settlementId, conversionId, getOnBehalfOf());
    }

    public Settlement removeConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return api.removeConversion(authToken, userAgent, settlementId, conversionId, getOnBehalfOf());
    }

    public Settlement releaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.releaseSettlement(authToken, userAgent, settlementId, getOnBehalfOf());
    }

    public Settlement unreleaseSettlement(String settlementId) throws CurrencyCloudException {
        return api.unreleaseSettlement(authToken, userAgent, settlementId, getOnBehalfOf());
    }

    ///////////////////////////////////////////////////////////////////
    ///// TRANSACTIONS ////////////////////////////////////////////////

    public Transaction retrieveTransaction(String id) throws CurrencyCloudException {
        return api.retrieveTransaction(authToken, userAgent, id, getOnBehalfOf());
    }

    /**
     * @param transaction Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Transactions search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Transactions findTransactions(@Nullable Transaction transaction, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (transaction == null) {
            transaction = Transaction.create();
        }
        return api.findTransactions(
                authToken,
                userAgent,
                getOnBehalfOf(),
                transaction.getCurrency(),
                transaction.getAmount(),
                transaction.getAmountFrom(),
                transaction.getAmountTo(),
                transaction.getAction(),
                transaction.getRelatedEntityType(),
                transaction.getRelatedEntityId(),
                transaction.getRelatedEntityShortReference(),
                transaction.getStatus(),
                transaction.getType(),
                transaction.getReason(),
                dateOnly(transaction.getSettlesAtFrom()),
                dateOnly(transaction.getSettlesAtTo()),
                dateOnly(transaction.getCreatedAtFrom()),
                dateOnly(transaction.getCreatedAtTo()),
                dateOnly(transaction.getUpdatedAtFrom()),
                dateOnly(transaction.getUpdatedAtTo()),
                dateOnly(transaction.getCompletedAtFrom()),
                dateOnly(transaction.getCompletedAtTo()),
                transaction.getBeneficiaryId(),
                transaction.getCurrencyPair(),
                transaction.getScope(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    ///////////////////////////////////////////////////////////////////
    ///// TRANSFERS ///////////////////////////////////////////////////

    public Transfer retrieveTransfer(String id) throws CurrencyCloudException {
        return api.retrieveTransfer(authToken, userAgent, id, getOnBehalfOf());
    }

    /**
     * @param transfer    Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination  pagination settings
     * @return            The paginated Transfers search result
     * @throws            CurrencyCloudException When an error occurs
     */
    public Transfers findTransfers(@Nullable Transfer transfer, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (transfer == null) {
            transfer = Transfer.create();
        }
        return api.findTransfers(
                authToken,
                userAgent,
                getOnBehalfOf(),
                transfer.getShortReference(),
                transfer.getSourceAccountId(),
                transfer.getDestinationAccountId(),
                transfer.getStatus(),
                transfer.getCurrency(),
                transfer.getAmountFrom(),
                transfer.getAmountTo(),
                transfer.getCreatedAtFrom(),
                transfer.getCreatedAtTo(),
                transfer.getUpdatedAtFrom(),
                transfer.getUpdatedAtTo(),
                transfer.getCompletedAtFrom(),
                transfer.getCompletedAtTo(),
                transfer.getCreatorContactId(),
                transfer.getCreatorAccountId(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public Transfer createTransfer(Transfer transfer) throws ResponseException {
        return api.createTransfer(
                authToken,
                userAgent,
                transfer.getSourceAccountId(),
                transfer.getDestinationAccountId(),
                transfer.getCurrency(),
                transfer.getAmount(),
                transfer.getReason()
        );
    }

    ///////////////////////////////////////////////////////////////////
    ///// VANS ////////////////////////////////////////////////////////

    /**
     * @param virtualAccount Non-null properties will be used for querying. Null values will be ignored.
     * @param pagination     pagination settings
     * @return               The paginated Ibans search result
     * @throws               CurrencyCloudException When an error occurs
     */
    public VirtualAccounts findVirtualAccounts(@Nullable VirtualAccount virtualAccount, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (virtualAccount == null) {
            virtualAccount = VirtualAccount.create();
        }
        return api.findVirtualAccounts(
                authToken,
                userAgent,
                virtualAccount.getScope(),
                virtualAccount.getAccountId(),
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc()
        );
    }

    public VirtualAccounts retrieveVirtualAccount(@Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.retrieveVirtualAccount(
                authToken,
                userAgent,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc());
    }

    /**
     * @deprecated as of 1.7.4; use generic {@link #findVirtualAccounts(VirtualAccount, Pagination)} instead.
     * */
    @Deprecated
    public VirtualAccounts retrieveSubAccountsVirtualAccount(String id, Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.retrieveSubAccountsVirtualAccount(
                authToken,
                userAgent,
                id,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc());
    }

    /**
     * @deprecated as of 1.7.4; use generic {@link #findVirtualAccounts(VirtualAccount, Pagination)} instead.
     * */
    @Deprecated
    public VirtualAccounts findSubAccountsVirtualAccounts(@Nullable VirtualAccount virtualAccount, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findSubAccountsVirtualAccounts(
                authToken,
                userAgent,
                pagination.getPage(),
                pagination.getPerPage(),
                pagination.getOrder(),
                pagination.getOrderAscDesc());
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
    @SuppressWarnings("unchecked")
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
