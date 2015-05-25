package com.currencycloud.client;

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

    private String authToken;

    public CurrencyCloudClient() {
        this(Environment.production);
    }

    public CurrencyCloudClient(Environment environment) {
        this(environment.url);
    }

    CurrencyCloudClient(String url) {
        ClientConfig config = new ClientConfig();
        config.setJacksonConfigureListener(new JacksonConfigureListener() {
            @Override
            public void configureObjectMapper(ObjectMapper objectMapper) {
                objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            }
        });
        api = RestProxyFactory.createProxy(CurrencyCloud.class, url, config);
    }

    void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    ///////////////////////////////////////////////////////////////////
    ///// ON BEHALF OF ////////////////////////////////////////////////

    /**
     * Performs the work on behalf of another user.
     *
     * @param contactId  contactId of the user
     * @param work       the work to do
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

    /** Starts a logged in session */
    public void authenticate(String loginId, String apiKey) throws CurrencyCloudException {
        authToken = api.authenticate(loginId, apiKey).getAuthToken();
    }

    /** Ends a logged in session */
    public void endSession() throws CurrencyCloudException {
        api.endSession(authToken);
        authToken = null;
    }

    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS ////////////////////////////////////////////////////

    // todo: test: create retrieve update find

    public Account createAccount(String accountName, @Nullable String legalEntityType, @Nullable String yourReference, @Nullable String status, @Nullable String street, @Nullable String city, @Nullable String stateOrProvince, @Nullable String postalCode, @Nullable String country, @Nullable String spreadTable, @Nullable String identificationType, @Nullable String identificationValue) throws CurrencyCloudException {
        return api.createAccount(authToken, accountName, legalEntityType, yourReference, status, street, city, stateOrProvince, postalCode, country, spreadTable, identificationType, identificationValue);
    }

    public Account retrieveAccount(String accountId) throws CurrencyCloudException {
        return api.retrieveAccount(authToken, accountId, onBehalfOf);
    }
    
    public Account updateAccount(String accountId, @Nullable String accountName, @Nullable String legalEntityType, @Nullable String yourReference, @Nullable String status, @Nullable String street, @Nullable String city, @Nullable String stateOrProvince, @Nullable String postalCode, @Nullable String country, @Nullable String spreadTable, @Nullable String identificationType, @Nullable String identificationValue) throws CurrencyCloudException {
        return api.updateAccount(authToken, accountId, accountName, legalEntityType, yourReference, status, street, city, stateOrProvince, postalCode, country, spreadTable, identificationType, identificationValue);
    }
    
    public Accounts findAccounts(@Nullable String accountName, @Nullable String brand, @Nullable String yourReference, @Nullable String status, @Nullable String street, @Nullable String city, @Nullable String stateOrProvince, @Nullable String postalCode, @Nullable String country, @Nullable String spreadTable, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findAccounts(authToken, accountName, brand, yourReference, status, street, city, stateOrProvince, postalCode, country, spreadTable, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc());
    }

    public Account currentAccount() throws CurrencyCloudException {
        return api.currentAccount(authToken);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES ////////////////////////////////////////////////////

    // todo: test
    public Balances findBalances(@Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable Date asAtDate, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findBalances(authToken, amountFrom, amountTo, asAtDate, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc());
    }

    public Balance findBalance(String currency) throws CurrencyCloudException {
        return api.findBalance(authToken, currency);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES ///////////////////////////////////////////////

    public Beneficiary validateBeneficiary(String bankCountry, String currency, String beneficiaryCountry, @Nullable String accountNumber, @Nullable String routingCodeType1, @Nullable String routingCodeValue1, @Nullable String routingCodeType2, @Nullable String routingCodeValue2, @Nullable String bicSwift, @Nullable String iban, @Nullable List<String> bankAddress, @Nullable String bankName, @Nullable String bankAccountType, @Nullable String beneficiaryEntityType, @Nullable String beneficiaryCompanyName, @Nullable String beneficiaryFirstName, @Nullable String beneficiaryLastName, @Nullable String beneficiaryCity, @Nullable String beneficiaryPostcode, @Nullable String beneficiaryStateOrProvince, @Nullable Date beneficiaryDateOfBirth, @Nullable String beneficiaryIdentificationType, @Nullable String beneficiaryIdentificationValue, @Nullable List<String> paymentTypes)
            throws CurrencyCloudException {
        return api.validateBeneficiary(authToken, bankCountry, currency, beneficiaryCountry, accountNumber, routingCodeType1, routingCodeValue1, routingCodeType2, routingCodeValue2, bicSwift, iban, bankAddress, bankName, bankAccountType, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, beneficiaryIdentificationType, beneficiaryIdentificationValue, paymentTypes, onBehalfOf);
    }

    public Beneficiary createBeneficiary(String bankAccountHolderName, String bankCountry, String currency, String name, @Nullable String email, @Nullable String beneficiaryAddress, @Nullable String beneficiaryCountry, @Nullable String accountNumber, @Nullable String routingCodeType1, @Nullable String routingCodeValue1, @Nullable String routingCodeType2, @Nullable String routingCodeValue2, @Nullable String bicSwift, @Nullable String iban, @Nullable Boolean defaultBeneficiary, @Nullable List<String> bankAddress, @Nullable String bankName, @Nullable String bankAccountType, @Nullable String beneficiaryEntityType, @Nullable String beneficiaryCompanyName, @Nullable String beneficiaryFirstName, @Nullable String beneficiaryLastName, @Nullable String beneficiaryCity, @Nullable String beneficiaryPostcode, @Nullable String beneficiaryStateOrProvince, @Nullable Date beneficiaryDateOfBirth, @Nullable String beneficiaryIdentificationType, @Nullable String beneficiaryIdentificationValue, @Nullable List<String> paymentTypes)
            throws CurrencyCloudException {
        return api.createBeneficiary(authToken, bankAccountHolderName, bankCountry, currency, name, email, beneficiaryAddress, beneficiaryCountry, accountNumber, routingCodeType1, routingCodeValue1, routingCodeType2, routingCodeValue2, bicSwift, iban, defaultBeneficiary, bankAddress, bankName, bankAccountType, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, beneficiaryIdentificationType, beneficiaryIdentificationValue, paymentTypes, onBehalfOf);
    }

    public Beneficiary retrieveBeneficiary(String id) throws CurrencyCloudException {
        return api.retrieveBeneficiary(authToken, id, onBehalfOf);
    }

    public Beneficiary updateBeneficiary(String beneficiaryId, @Nullable String bankAccountHolderName, @Nullable String bankCountry, @Nullable String currency, @Nullable String name, @Nullable String email, @Nullable String beneficiaryAddress, @Nullable String beneficiaryCountry, @Nullable String accountNumber, @Nullable String routingCodeType1, @Nullable String routingCodeValue1, @Nullable String routingCodeType2, @Nullable String routingCodeValue2, @Nullable String bicSwift, @Nullable String iban, @Nullable Boolean defaultBeneficiary, @Nullable List<String> bankAddress, @Nullable String bankName, @Nullable String bankAccountType, @Nullable String beneficiaryEntityType, @Nullable String beneficiaryCompanyName, @Nullable String beneficiaryFirstName, @Nullable String beneficiaryLastName, @Nullable String beneficiaryCity, @Nullable String beneficiaryPostcode, @Nullable String beneficiaryStateOrProvince, @Nullable Date beneficiaryDateOfBirth, @Nullable String beneficiaryIdentificationType, @Nullable String beneficiaryIdentificationValue, @Nullable List<String> paymentTypes)
            throws CurrencyCloudException {
        return api.updateBeneficiary(authToken, beneficiaryId, bankAccountHolderName, bankCountry, currency, name, email, beneficiaryAddress, beneficiaryCountry, accountNumber, routingCodeType1, routingCodeValue1, routingCodeType2, routingCodeValue2, bicSwift, iban, defaultBeneficiary, bankAddress, bankName, bankAccountType, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, beneficiaryIdentificationType, beneficiaryIdentificationValue, paymentTypes, onBehalfOf);
    }

    public Beneficiaries findBeneficiaries()
            throws CurrencyCloudException {
        return api.findBeneficiaries(
                authToken, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null, null, null, null, null, null, null, null, onBehalfOf);
    }

    public Beneficiaries findBeneficiaries(@Nullable String bankAccountHolderName, @Nullable String beneficiaryCountry, @Nullable String currency, @Nullable String accountNumber, @Nullable String routingCodeType, @Nullable String routingCodeValue, @Nullable String paymentTypes, @Nullable String bicSwift, @Nullable String iban, @Nullable Boolean defaultBeneficiary, @Nullable String bankName, @Nullable String bankAccountType, @Nullable String name, @Nullable String beneficiaryEntityType, @Nullable String beneficiaryCompanyName, @Nullable String beneficiaryFirstName, @Nullable String beneficiaryLastName, @Nullable String beneficiaryCity, @Nullable String beneficiaryPostcode, @Nullable String beneficiaryStateOrProvince, @Nullable Date beneficiaryDateOfBirth, @Nullable Pagination pagination)
            throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findBeneficiaries(authToken, bankAccountHolderName, beneficiaryCountry, currency, accountNumber, routingCodeType, routingCodeValue, paymentTypes, bicSwift, iban, defaultBeneficiary, bankName, bankAccountType, name, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc(), onBehalfOf);
    }

    public Beneficiary firstBeneficiary(@Nullable String bankAccountHolderName, @Nullable String beneficiaryCountry, @Nullable String currency, @Nullable String accountNumber, @Nullable String routingCodeType, @Nullable String routingCodeValue, @Nullable String paymentTypes, @Nullable String bicSwift, @Nullable String iban, @Nullable Boolean defaultBeneficiary, @Nullable String bankName, @Nullable String bankAccountType, @Nullable String name, @Nullable String beneficiaryEntityType, @Nullable String beneficiaryCompanyName, @Nullable String beneficiaryFirstName, @Nullable String beneficiaryLastName, @Nullable String beneficiaryCity, @Nullable String beneficiaryPostcode, @Nullable String beneficiaryStateOrProvince, @Nullable Date beneficiaryDateOfBirth) {
        return findBeneficiaries(bankAccountHolderName, beneficiaryCountry, currency, accountNumber, routingCodeType, routingCodeValue, paymentTypes, bicSwift, iban, defaultBeneficiary, bankName, bankAccountType, name, beneficiaryEntityType, beneficiaryCompanyName, beneficiaryFirstName, beneficiaryLastName, beneficiaryCity, beneficiaryPostcode, beneficiaryStateOrProvince, beneficiaryDateOfBirth, Pagination.first()).getBeneficiaries().iterator().next();
    }

    public Beneficiary deleteBeneficiary(String id) throws CurrencyCloudException {
        return api.deleteBeneficiary(authToken, id, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS /////////////////////////////////////////////////

    public Conversions findConversions(@Nullable String shortReference, @Nullable String status, @Nullable String partnerStatus, @Nullable String buyCurrency, @Nullable String sellCurrency, @Nullable String conversionIds, @Nullable String createdAtFrom, @Nullable String createdAtTo, @Nullable String updatedAtFrom, @Nullable String updatedAtTo, @Nullable String currencyPair, @Nullable String partnerBuyAmountFrom, @Nullable String partnerBuyAmountTo, @Nullable String partnerSellAmountFrom, @Nullable String partnerSellAmountTo, @Nullable String buyAmountFrom, @Nullable String buyAmountTo, @Nullable String sellAmountFrom, @Nullable String sellAmountTo) throws CurrencyCloudException {
        return api.findConversions(authToken, shortReference, status, partnerStatus, buyCurrency, sellCurrency, conversionIds, createdAtFrom, createdAtTo, updatedAtFrom, updatedAtTo, currencyPair, partnerBuyAmountFrom, partnerBuyAmountTo, partnerSellAmountFrom, partnerSellAmountTo, buyAmountFrom, buyAmountTo, sellAmountFrom, sellAmountTo, onBehalfOf);
    }

    public Conversion retrieveConversion(String conversionId) throws CurrencyCloudException {
        return api.retrieveConversion(authToken, conversionId);
    }

    public Conversion createConversion(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, String reason, Boolean termAgreement, @Nullable Date conversionDate, @Nullable BigDecimal clientRate, @Nullable String currencyPair, @Nullable BigDecimal clientBuyAmount, @Nullable BigDecimal clientSellAmount) {
        return api.createConversion(authToken, buyCurrency, sellCurrency, fixedSide, amount, reason, termAgreement, conversionDate, clientRate, currencyPair, clientBuyAmount, clientSellAmount, onBehalfOf);
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYERS ///////////////////////////////////////////////////////

    public Payer retrievePayer(String payerId) throws CurrencyCloudException {
        return api.retrievePayer(authToken, payerId);
    }


    ///////////////////////////////////////////////////////////////////
    ///// PAYMENTS ////////////////////////////////////////////////////

    public Payment deletePayment(String paymentId) throws CurrencyCloudException {
        return api.deletePayment(authToken, paymentId, onBehalfOf);
    }

    public Payments findPayments(@Nullable String shortReference, @Nullable String currency, @Nullable BigDecimal amount, @Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable String status, @Nullable String reason, @Nullable Date paymentDateFrom, @Nullable Date paymentDateTo, @Nullable Date transferredAtFrom, @Nullable Date transferredAtTo, @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, @Nullable String beneficiaryId, @Nullable String conversionId, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findPayments(authToken, shortReference, currency, amount, amountFrom, amountTo, status, reason, paymentDateFrom, paymentDateTo, transferredAtFrom, transferredAtTo, createdAtFrom, createdAtTo, updatedAtFrom, updatedAtTo, beneficiaryId, conversionId, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc(), onBehalfOf);
    }

    public Payment updatePayment(String paymentId, String currency, String beneficiaryId, String amount, String reason, @Nullable String reference, @Nullable String paymentDate, @Nullable String paymentType, @Nullable String conversionId, @Nullable String payerEntityType, @Nullable String payerCompanyName, @Nullable String payerFirstName, @Nullable String payerLastName, @Nullable String payerCity, @Nullable String payerPostcode, @Nullable String payerStateOrProvince, @Nullable Date payerDateOfBirth, @Nullable String payerIdentificationType, @Nullable String payerIdentificationValue, @Nullable String onBehalfOf) throws CurrencyCloudException {
        return api.updatePayment(authToken, paymentId, currency, beneficiaryId, amount, reason, reference, paymentDate, paymentType, conversionId, payerEntityType, payerCompanyName, payerFirstName, payerLastName, payerCity, payerPostcode, payerStateOrProvince, payerDateOfBirth, payerIdentificationType, payerIdentificationValue, onBehalfOf);
    }

    public Payment retrievePayment(String id) throws CurrencyCloudException {
        return api.retrievePayment(authToken, id, onBehalfOf);
    }

    public Payment createPayment(String currency, String beneficiaryId, BigDecimal amount, String reason, String reference, @Nullable String paymentDate, @Nullable String paymentType, @Nullable String conversionId, @Nullable String payerEntityType, @Nullable String payerCompanyName, @Nullable String payerFirstName, @Nullable String payerLastName, @Nullable String payerCity, @Nullable String payerPostcode, @Nullable String payerStateOrProvince, @Nullable Date payerDateOfBirth, @Nullable String payerIdentificationType, @Nullable String payerIdentificationValue, @Nullable String onBehalfOf) throws CurrencyCloudException {
        return api.createPayment(authToken, currency, beneficiaryId, amount, reason, reference, paymentDate, paymentType, conversionId, payerEntityType, payerCompanyName, payerFirstName, payerLastName, payerCity, payerPostcode, payerStateOrProvince, payerDateOfBirth, payerIdentificationType, payerIdentificationValue, onBehalfOf);
    }

    ///////////////////////////////////////////////////////////////////
    ///// RATES ///////////////////////////////////////////////////////

    public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException {
        return api.detailedRates(authToken, buyCurrency, sellCurrency, fixedSide, amount, conversionDate, onBehalfOf);
    }

    public Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException {
        return api.findRates(authToken, currencyPair, ignoreInvalidPairs, onBehalfOf);
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

    public Settlements findSettlements(@Nullable String shortReference, @Nullable String status, @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, @Nullable Date releasedAtFrom, @Nullable Date releasedAtTo, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findSettlements(authToken, shortReference, status, createdAtFrom, createdAtTo, updatedAtFrom, updatedAtTo, releasedAtFrom, releasedAtTo, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc(), onBehalfOf);
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

    public Transactions findTransactions(@Nullable String currency, @Nullable BigDecimal amount, @Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable String action, @Nullable String relatedEntityType, @Nullable String relatedEntityId, @Nullable String relatedEntityShortReference, @Nullable String status, @Nullable String type, @Nullable String reason, @Nullable String settlesAtFrom, @Nullable String settlesAtTo, @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, @Nullable Pagination pagination) throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        return api.findTransactions(authToken, currency, amount, amountFrom, amountTo, action, relatedEntityType, relatedEntityId, relatedEntityShortReference, status, type, reason, settlesAtFrom, settlesAtTo, createdAtFrom, createdAtTo, updatedAtFrom, updatedAtTo, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc(), onBehalfOf);
    }


    ///////////////////////////////////////////////////////////////////

    public enum Environment {
        production("https://api.thecurrencycloud.com"),
        demo("https://devapi.thecurrencycloud.com")
        ;
        private final String url;

        Environment(String url) {
            this.url = url;
        }
    }
}
