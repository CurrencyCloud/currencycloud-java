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
        api = RestProxyFactory.createProxy(
                CurrencyCloud.class, url, config, new HttpStatusExceptionInterceptor()
        );
    }

    void setAuthToken(String authToken) {
        this.authToken = authToken;
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
    public void authenticate(String loginId, String apiKey) throws CurrencyCloudException {
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

    // todo: test: create retrieve update find

    public Account createAccount(String accountName, Account account) throws CurrencyCloudException {
        return api.createAccount(authToken, accountName,
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
            example = new Account();
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

    // todo: test
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
                join(beneficiary.getBeneficiaryAddress()), // todo
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
                join(beneficiary.getBeneficiaryAddress()), // todo
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

    public Beneficiaries findBeneficiaries(@Nullable Beneficiary beneficiary, @Nullable Pagination pagination)
            throws CurrencyCloudException {
        if (pagination == null) {
            pagination = Pagination.builder().build();
        }
        if (beneficiary == null) {
            beneficiary = Beneficiary.createEmpty();
        }
        return api.findBeneficiaries(
                authToken,
                beneficiary.getBankAccountHolderName(),
                beneficiary.getBeneficiaryCountry(),
                beneficiary.getCurrency(),
                beneficiary.getAccountNumber(),
                beneficiary.getRoutingCodeType1(), // todo
                beneficiary.getRoutingCodeValue1(),  // todo
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
                beneficiary.getBeneficiaryDateOfBirth(),
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
            @Nullable String createdAtFrom,
            @Nullable String createdAtTo,
            @Nullable String updatedAtFrom,
            @Nullable String updatedAtTo,
            @Nullable String partnerBuyAmountFrom,
            @Nullable String partnerBuyAmountTo,
            @Nullable String partnerSellAmountFrom,
            @Nullable String partnerSellAmountTo,
            @Nullable String buyAmountFrom,
            @Nullable String buyAmountTo,
            @Nullable String sellAmountFrom,
            @Nullable String sellAmountTo
    ) throws CurrencyCloudException {
        if (example == null) {
            example = Conversion.createEmpty();
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
                                 payment.getPaymentDate(),
                                 payment.getPaymentType(),
                                 payment.getConversionId(),
                                 payer.getLegalEntityType(),
                                 payer.getCompanyName(),
                                 payer.getFirstName(),
                                 payer.getLastName(),
                                 payer.getCity(),
                                 payer.getPostcode(),
                                 payer.getStateOrProvince(),
                                 payer.getDateOfBirth(),
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
                                 payment.getPaymentDate(),
                                 payment.getPaymentType(),
                                 payment.getConversionId(),
                                 payer.getLegalEntityType(),
                                 payer.getCompanyName(),
                                 payer.getFirstName(),
                                 payer.getLastName(),
                                 payer.getCity(),
                                 payer.getPostcode(),
                                 payer.getStateOrProvince(),
                                 payer.getDateOfBirth(),
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
            example = Payment.createEmpty();
        }
        return api.findPayments(authToken,
                                example.getShortReference(),
                                example.getCurrency(),
                                example.getAmount(),
                                amountFrom,
                                amountTo,
                                example.getStatus(),
                                example.getReason(),
                                paymentDateFrom,
                                paymentDateTo,
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
        return api.detailedRates(authToken, buyCurrency, sellCurrency, fixedSide, amount, conversionDate, onBehalfOf);
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
            @Nullable String settlesAtFrom,
            @Nullable String settlesAtTo,
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
            example = Transaction.createEmpty();
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
                settlesAtFrom,
                settlesAtTo,
                createdAtFrom,
                createdAtTo,
                updatedAtFrom,
                updatedAtTo,
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
                sb.append(", ");
            }
            sb.append(string);
        }
        return sb.toString();
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
