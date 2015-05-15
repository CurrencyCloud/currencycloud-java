package com.currencycloud.client;

import com.currencycloud.client.model.*;
import si.mazi.rescu.RestProxyFactory;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class CurrencyCloudClient {

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
        api = RestProxyFactory.createProxy(CurrencyCloud.class, url);
    }

    void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

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
    
    public Accounts findAccounts(@Nullable String accountName, @Nullable String brand, @Nullable String yourReference, @Nullable String status, @Nullable String street, @Nullable String city, @Nullable String stateOrProvince, @Nullable String postalCode, @Nullable String country, @Nullable String spreadTable, @Nullable Integer page, @Nullable Integer perPage, @Nullable String order, @Nullable Pagination.SortOrder orderAscDesc) throws CurrencyCloudException {
        return api.findAccounts(authToken, accountName, brand, yourReference, status, street, city, stateOrProvince, postalCode, country, spreadTable, page, perPage, order, orderAscDesc);
    }

    public Account currentAccount() throws CurrencyCloudException {
        return api.currentAccount(authToken);
    }

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES ////////////////////////////////////////////////////

    // todo: test
    public Balances findBalances(BigDecimal amountFrom, BigDecimal amountTo, Date asAtDate, String order, Integer page, Integer perPage, Pagination.SortOrder orderAscDesc) throws CurrencyCloudException {
        return api.findBalances(authToken, amountFrom, amountTo, asAtDate, order, page, perPage, orderAscDesc);
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
        // todo: convert camelCase in order to underscore_case?
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
