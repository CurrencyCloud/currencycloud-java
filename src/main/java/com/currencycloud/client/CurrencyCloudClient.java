package com.currencycloud.client;

import com.currencycloud.client.dirty.ModificationTracker;
import com.currencycloud.client.dirty.ModifiedValueProvider;
import com.currencycloud.client.exception.CurrencyCloudException;
import com.currencycloud.client.exception.UnexpectedException;
import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.AccountPaymentChargesSetting;
import com.currencycloud.client.model.AccountPaymentChargesSettings;
import com.currencycloud.client.model.Accounts;
import com.currencycloud.client.model.Balance;
import com.currencycloud.client.model.Balances;
import com.currencycloud.client.model.BankDetails;
import com.currencycloud.client.model.Beneficiaries;
import com.currencycloud.client.model.Beneficiary;
import com.currencycloud.client.model.BeneficiaryAccountVerification;
import com.currencycloud.client.model.BeneficiaryAccountVerificationRequest;
import com.currencycloud.client.model.Contact;
import com.currencycloud.client.model.Contacts;
import com.currencycloud.client.model.Conversion;
import com.currencycloud.client.model.ConversionCancellation;
import com.currencycloud.client.model.ConversionCancellationQuote;
import com.currencycloud.client.model.ConversionDateChange;
import com.currencycloud.client.model.ConversionDateChangeDetails;
import com.currencycloud.client.model.ConversionDates;
import com.currencycloud.client.model.ConversionProfitAndLoss;
import com.currencycloud.client.model.ConversionProfitAndLosses;
import com.currencycloud.client.model.ConversionReport;
import com.currencycloud.client.model.ConversionSplit;
import com.currencycloud.client.model.ConversionSplitHistory;
import com.currencycloud.client.model.Conversions;
import com.currencycloud.client.model.Currency;
import com.currencycloud.client.model.DetailedRate;
import com.currencycloud.client.model.Entity;
import com.currencycloud.client.model.FundingAccounts;
import com.currencycloud.client.model.Iban;
import com.currencycloud.client.model.Ibans;
import com.currencycloud.client.model.MarginBalanceTopUp;
import com.currencycloud.client.model.Pagination;
import com.currencycloud.client.model.Payer;
import com.currencycloud.client.model.PayerRequiredDetail;
import com.currencycloud.client.model.Payment;
import com.currencycloud.client.model.PaymentAuthorisations;
import com.currencycloud.client.model.PaymentConfirmation;
import com.currencycloud.client.model.PaymentDates;
import com.currencycloud.client.model.PaymentDeliveryDate;
import com.currencycloud.client.model.PaymentFeeAssignment;
import com.currencycloud.client.model.PaymentFeeRule;
import com.currencycloud.client.model.PaymentFeeUnassignment;
import com.currencycloud.client.model.PaymentFees;
import com.currencycloud.client.model.PaymentPurposeCode;
import com.currencycloud.client.model.PaymentReport;
import com.currencycloud.client.model.PaymentSubmission;
import com.currencycloud.client.model.PaymentSubmissionInfo;
import com.currencycloud.client.model.PaymentTrackingInfo;
import com.currencycloud.client.model.PaymentValidationResult;
import com.currencycloud.client.model.Payments;
import com.currencycloud.client.model.QuotePaymentFee;
import com.currencycloud.client.model.Rates;
import com.currencycloud.client.model.ReportRequest;
import com.currencycloud.client.model.ReportRequests;
import com.currencycloud.client.model.ResponseException;
import com.currencycloud.client.model.SenderDetails;
import com.currencycloud.client.model.SettlementAccount;
import com.currencycloud.client.model.TermsAndConditionsAcceptance;
import com.currencycloud.client.model.Transaction;
import com.currencycloud.client.model.Transactions;
import com.currencycloud.client.model.Transfer;
import com.currencycloud.client.model.Transfers;
import com.currencycloud.client.model.VirtualAccount;
import com.currencycloud.client.model.VirtualAccounts;
import com.currencycloud.client.model.WithdrawalAccountFunds;
import com.currencycloud.client.model.WithdrawalAccounts;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static net.bytebuddy.matcher.ElementMatchers.any;

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
 */
public class CurrencyCloudClient {

  private static final Logger log = LoggerFactory.getLogger(CurrencyCloudClient.class);
  private static final Pattern UUID = Pattern.compile(
      "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}",
      Pattern.CASE_INSENSITIVE
  );
  private static final String userAgent = "CurrencyCloudSDK/2.0 Java/6.2.0";

  private final CurrencyCloud api;

  // use threadlocal
  private ThreadLocal<String> onBehalfOf = new ThreadLocal<>();

  private String loginId;
  private String apiKey;
  private String authToken;

  public static class HttpClientConfiguration {
    private final int httpConnTimeout;
    private final int httpReadTimeout;

    private HttpClientConfiguration(int httpConnTimeout, int httpReadTimeout) {
      this.httpConnTimeout = httpConnTimeout;
      this.httpReadTimeout = httpReadTimeout;
    }

    public int getHttpConnTimeout() {
      return httpConnTimeout;
    }

    public int getHttpReadTimeout() {
      return httpReadTimeout;
    }

    public static class HttpClientConfigurationBuilder {
      private int httpConnTimeout = 30000;
      private int httpReadTimeout = 30000;

      /**
       * Set connection timeout for the HTTP client in milliseconds.
       *
       * @param httpConnTimeout
       * @return HttpClientConfigurationBuilder
       */
      public HttpClientConfigurationBuilder httpConnTimeout(final int httpConnTimeout) {
        this.httpConnTimeout = httpConnTimeout;
        return this;
      }

      /**
       * Set read timeout for the HTTP client in milliseconds.
       *
       * @param httpReadTimeout
       * @return HttpClientConfigurationBuilder
       */
      public HttpClientConfigurationBuilder httpReadTimeout(final int httpReadTimeout) {
        this.httpReadTimeout = httpReadTimeout;
        return this;
      }

      /**
       * Creates the HTTP client configuration with the provided values or with default values where no value set.
       *
       * @return HttpClientConfiguration
       */
      public HttpClientConfiguration build() {
        return new HttpClientConfiguration(this.httpConnTimeout, this.httpReadTimeout);
      }
    }

    public static HttpClientConfigurationBuilder builder() {
      return new HttpClientConfigurationBuilder();
    }
  }

  public CurrencyCloudClient(Environment environment, String loginId, String apiKey, HttpClientConfiguration httpClientConfiguration) {
    this(environment.url, loginId, apiKey, httpClientConfiguration);
  }

  public CurrencyCloudClient(Environment environment, String loginId, String apiKey) {
    this(environment.url, loginId, apiKey, HttpClientConfiguration.builder().build());
  }

  CurrencyCloudClient(String url, String loginId, String apiKey, HttpClientConfiguration httpClientConfiguration) {
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

    config.setHttpConnTimeout(httpClientConfiguration.getHttpConnTimeout());
    config.setHttpReadTimeout(httpClientConfiguration.getHttpReadTimeout());

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
    api.endSession(authToken, userAgent);
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
        account.getPhoneTrading(),
        account.getTermsAndConditionsAccepted()
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
   * @param account    Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Accounts search result
   * @throws CurrencyCloudException When an error occurs
   */
  public Accounts findAccounts(@Nullable Account account, @Nullable Pagination pagination) throws CurrencyCloudException {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }
    if (account == null) {
      account = Account.create();
    }
    return api.findAccountsPost(
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
        account.getBankAccountVerified(),
        pagination.getPage(),
        pagination.getPerPage(),
        pagination.getOrder(),
        pagination.getOrderAscDesc()
    );
  }

  public Account currentAccount() throws CurrencyCloudException {
    return api.currentAccount(authToken, userAgent);
  }

  public AccountPaymentChargesSettings retrieveAccountsPaymentChargeSettings(final String accountId) throws CurrencyCloudException {
    return api.retrieveAccountsPaymentChargeSettings(
        authToken,
        userAgent,
        accountId
    );
  }

  public AccountPaymentChargesSetting updateAccountsPaymentChargeSetting(final AccountPaymentChargesSetting chargeSettings) throws CurrencyCloudException {
    return api.updateAccountsPaymentChargeSettings(
        authToken,
        userAgent,
        chargeSettings.getAccountId(),
        chargeSettings.getChargeSettingsId(),
        chargeSettings.isEnabled(),
        chargeSettings.isDefault()
    );
  }

  ///////////////////////////////////////////////////////////////////
  ///// BALANCES ////////////////////////////////////////////////////

  /**
   * @param balance    Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Balances search result
   * @throws CurrencyCloudException When an error occurs
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

  public MarginBalanceTopUp topUpMarginBalance(final String currency, final BigDecimal amount) throws CurrencyCloudException {
    return api.topUpMarginBalance(
        authToken,
        userAgent,
        currency,
        amount,
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
        beneficiary.getBeneficiaryExternalReference(),
        beneficiary.getPaymentTypes(),
        beneficiary.getCompanyWebsite(),
        beneficiary.getBusinessNature()
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
        beneficiary.getBeneficiaryExternalReference(),
        beneficiary.getPaymentTypes(),
        beneficiary.getCompanyWebsite(),
        beneficiary.getBusinessNature()
    );
  }

  /**
   * @param beneficiary Non-null properties will be used for querying. Null values will be ignored.
   *                    Use routingCodeType1 and routingCodeValue1 (the *2 fields are ignored).
   * @param pagination  pagination settings
   * @return The paginated Beneficiaries search results
   * @throws CurrencyCloudException When an error occurs
   */
  public Beneficiaries findBeneficiaries(@Nullable Beneficiary beneficiary, @Nullable Pagination pagination) throws CurrencyCloudException {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }
    if (beneficiary == null) {
      beneficiary = Beneficiary.create();
    }
    return api.findBeneficiariesPost(
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
        beneficiary.getBeneficiaryExternalReference(),
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

  public BeneficiaryAccountVerification verifyAccount(BeneficiaryAccountVerificationRequest beneficiary) throws CurrencyCloudException {
      return api.verifyAccount(
        authToken,
        userAgent,
        beneficiary.getBankCountry(),
        beneficiary.getAccountNumber(),
        beneficiary.getRoutingCodeValue1(),
        beneficiary.getBeneficiaryEntityType(),
        beneficiary.getBeneficiaryCompanyName(),
        beneficiary.getBeneficiaryFirstName(),
        beneficiary.getBeneficiaryLastName(),
        beneficiary.getSecondaryReferenceData());
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
   * @param contact    Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Contacts search result
   * @throws CurrencyCloudException When an error occurs
   */
  @Deprecated
  public Contacts findContacts(Contact contact, Pagination pagination) throws CurrencyCloudException {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }
    if (contact == null) {
      contact = Contact.create();
    }
    return api.findContactsPost(
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
        conversion.getUniqueRequestId(),
        conversion.getConversionDatePreference()
    );
  }

  public Conversion retrieveConversion(String conversionId) throws CurrencyCloudException {
    return api.retrieveConversion(authToken, userAgent, conversionId);
  }

  /**
   * @param conversion Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Conversions search result
   * @throws CurrencyCloudException When an error occurs
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
        conversion.getId(),
        conversion.getNotes());
  }

  public ConversionCancellationQuote quoteCancelConversion(ConversionCancellationQuote conversion) throws CurrencyCloudException {
    return api.quoteCancelConversion(
        authToken,
        userAgent,
        conversion.getId());
  }

  public ConversionDateChange quoteChangeDateConversion(ConversionDateChange conversionDateChange) throws CurrencyCloudException {
    return api.quoteChangeDateConversion(
        authToken,
        userAgent,
        conversionDateChange.getId(),
        conversionDateChange.getNewSettlementDate());
  }

  public ConversionDateChange changeDateConversion(ConversionDateChange conversionDateChange) throws CurrencyCloudException {
    return api.changeDateConversion(
        authToken,
        userAgent,
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
        conversion.getId(),
        conversion.getAmount());
  }

  public ConversionSplit splitConversion(ConversionSplit conversion) throws CurrencyCloudException {
    return api.splitConversion(
        authToken,
        userAgent,
        conversion.getId(),
        conversion.getAmount());
  }

  public ConversionSplitHistory historySplitConversion(ConversionSplitHistory conversion) throws CurrencyCloudException {
    return api.historySplitConversion(
        authToken,
        userAgent,
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
  ///// FUNDING ///////////////////////////////////////////////////////

  /**
   * @param currency    Current of funding Account.
   * @param accountId   Id of Account which the funding Account applies to.
   * @param paymentType Type of payments funding account is used for.
   * @param pagination  pagination settings
   * @return The paginated Accounts search result
   * @throws CurrencyCloudException When an error occurs
   */
  public FundingAccounts findFundingAccounts(String currency, @Nullable String accountId, @Nullable String paymentType,
                                             @Nullable Pagination pagination) throws CurrencyCloudException {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }
    return api.findFundingAccounts(
        authToken,
        userAgent,
        currency,
        accountId,
        paymentType,
        getOnBehalfOf(),
        pagination.getPage(),
        pagination.getPerPage(),
        pagination.getOrder(),
        pagination.getOrderAscDesc()
    );
  }

  ///////////////////////////////////////////////////////////////////
  ///// IBANS ///////////////////////////////////////////////////////

  /**
   * @param iban       Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Ibans search result
   * @throws CurrencyCloudException When an error occurs
   * @deprecated This call has been disabled in the backend. Retained for backwards compatibility. It will be removed in
   * a future release
   */
  @Deprecated
  public Ibans findIbans(@Nullable Iban iban, @Nullable Pagination pagination) throws CurrencyCloudException {
    throw new UnexpectedException("findIbans is no longer available", new UnsupportedOperationException());
  }

  ///////////////////////////////////////////////////////////////////
  ///// PAYERS ///////////////////////////////////////////////////////

  public Payer retrievePayer(String payerId) throws CurrencyCloudException {
    return api.retrievePayer(authToken, userAgent, payerId);
  }

  ///////////////////////////////////////////////////////////////////
  ///// PAYMENTS ////////////////////////////////////////////////////

  public Payment createPayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
    return createPayment(payment, payer, null, null, null);
  }
  public Payment createPayment(Payment payment, @Nullable Payer payer, @Nullable Boolean scaOptIn, @Nullable String scaId, @Nullable String scaToken) throws CurrencyCloudException {
    if (payer == null) {
      payer = Payer.create();
    }
    return api.createPayment(
        authToken,
        userAgent,
        scaOptIn,
        scaId,
        scaToken,
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
        payment.getPurposeCode(),
        payment.getChargeType(),
        payment.getFeeAmount(),
        payment.getFeeCurrency(),
        dateOnly(payment.getInvoiceDate()),
        payment.getInvoiceNumber()
    );
  }

  public PaymentValidationResult validatePayment(Payment payment, @Nullable Payer payer, @Nullable Boolean scaForceSms) throws CurrencyCloudException {
    return validatePayment(payment, payer, scaForceSms, null, null);
  }

  public PaymentValidationResult validatePayment(Payment payment, @Nullable Payer payer, @Nullable Boolean scaForceSms, @Nullable Boolean scaOptIn) throws CurrencyCloudException {
    return validatePayment(payment, payer, scaForceSms, scaOptIn, null);
  }

  public PaymentValidationResult validatePayment(Payment payment, @Nullable Payer payer, @Nullable Boolean scaForceSms, @Nullable Boolean scaOptIn, @Nullable Boolean scaToAuthenticatedUser) throws CurrencyCloudException {
    if (payer == null) {
      payer = Payer.create();
    }
    return api.validatePayment(
        authToken,
        userAgent,
        scaForceSms,
        scaOptIn,
        scaToAuthenticatedUser,
        payment.getCurrency(),
        payment.getBeneficiaryId(),
        payment.getAmount(),
        payment.getReason(),
        payment.getReference(),
        payment.getId(),
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
        payment.getPurposeCode(),
        payment.getChargeType(),
        payment.getFeeAmount(),
        payment.getFeeCurrency(),
        dateOnly(payment.getInvoiceDate()),
        payment.getInvoiceNumber()
    );
  }

  public PaymentAuthorisations authorisePayment(List<String> paymentIds) throws CurrencyCloudException {
    return api.authorisePayment(
        authToken,
        userAgent,
        paymentIds
    );
  }

  public Payment retrievePayment(String id, @Nullable Boolean withDeleted) throws CurrencyCloudException {
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
        payment.getPurposeCode(),
        payment.getChargeType(),
        payment.getFeeAmount(),
        payment.getFeeCurrency(),
        dateOnly(payment.getInvoiceDate()),
        payment.getInvoiceNumber()
    );
  }

  /**
   * @param payment    Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Payments search result
   * @throws CurrencyCloudException When an error occurs
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
        payment.getChargeType(),
        payment.getFeeAmount(),
        payment.getFeeCurrency(),
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

  public PaymentSubmissionInfo retrievePaymentSubmissionInfo(String id) throws CurrencyCloudException {
    return api.retrievePaymentSubmissionInfo(
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

  public PaymentDeliveryDate getPaymentDeliveryDate(Date paymentDate, String paymentType, String currency, String bankCountry) throws CurrencyCloudException {
    return api.getPaymentDeliveryDate(
        authToken,
        userAgent,
        dateOnly(paymentDate),
        paymentType,
        currency,
        bankCountry);
  }

  public QuotePaymentFee getQuotePaymentFee(@Nullable String accountId, String paymentCurrency, String paymentDestinationCountry, String paymentType, @Nullable String chargeType) throws CurrencyCloudException {
    return api.getQuotePaymentFee(
        authToken,
        userAgent,
        accountId,
        paymentCurrency,
        paymentDestinationCountry,
        paymentType,
        chargeType);
  }

  public PaymentFees getPaymentFees(@Nullable Pagination pagination) {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }

    return api.getPaymentFees(authToken, userAgent, pagination.getPage(), pagination.getPerPage(), pagination.getOrder(), pagination.getOrderAscDesc());
  }

  public PaymentFeeAssignment assignPaymentFee(String paymentFeeId,
                                               String accountId) {

    return api.assignPaymentFee(authToken, userAgent, paymentFeeId, accountId);
  }

  public PaymentFeeUnassignment unassignPaymentFee(String accountId) {

    return api.unassignPaymentFee(authToken, userAgent, accountId);
  }

  public PaymentTrackingInfo getPaymentTrackingInfo(final String id) throws CurrencyCloudException {
    return api.getPaymentTrackingInfo(authToken, userAgent, id);
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

  public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate, @Nullable String conversionDatePreference) throws CurrencyCloudException {
    return api.detailedRates(
        authToken,
        userAgent,
        buyCurrency,
        sellCurrency,
        fixedSide,
        amount,
        getOnBehalfOf(),
        dateOnly(conversionDate),
        conversionDatePreference
    );
  }

  ////////////////////////////////////////////////////////////////////
  ///// REPORTS //////////////////////////////////////////////////////

  /**
   * @param reportRequest Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination    pagination settings
   * @return The paginated ReportRequest search result
   * @throws CurrencyCloudException When an error occurs
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

  public BankDetails bankDetails(String identifierType, String identifierValue) throws CurrencyCloudException {
    return api.bankDetailsPost(authToken, userAgent, identifierType, identifierValue);
  }

  public List<Map<String, String>> beneficiaryRequiredDetails(String currency, String bankAccountCountry, String beneficiaryCountry) throws CurrencyCloudException {
    return api.beneficiaryRequiredDetails(authToken, userAgent, currency, bankAccountCountry, beneficiaryCountry).getDetails();
  }

  public List<Currency> currencies() throws CurrencyCloudException {
    return api.currencies(authToken, userAgent).getCurrencies();
  }

  public ConversionDates conversionDates(String conversionPair, @Nullable Date startDate) throws CurrencyCloudException {
    return api.conversionDates(authToken, userAgent, conversionPair, dateOnly(startDate), getOnBehalfOf());
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

  public List<PaymentFeeRule> paymentFeeRules(@Nullable String accountId, @Nullable String paymentType, @Nullable String chargeType) throws CurrencyCloudException {
    return api.paymentFeeRules(authToken, userAgent, accountId, paymentType, chargeType).getPaymentFeeRules();
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
  ///// TERMS AND CONDITIONS //////////////////////////////////////////////////////

  public TermsAndConditionsAcceptance acceptTermsAndConditions(String type, String version, String referenceType, String referenceId, String firstName, String lastName, String email) throws CurrencyCloudException {
    return api.acceptTermsAndConditions(
            authToken,
            userAgent,
            type,
            version,
            referenceType,
            referenceId,
            firstName,
            lastName,
            email);
  }

  ///////////////////////////////////////////////////////////////////
  ///// TRANSACTIONS ////////////////////////////////////////////////

  public Transaction retrieveTransaction(String id) throws CurrencyCloudException {
    return api.retrieveTransaction(authToken, userAgent, id, getOnBehalfOf());
  }

  /**
   * @param transaction Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination  pagination settings
   * @return The paginated Transactions search result
   * @throws CurrencyCloudException When an error occurs
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
   * @param transfer   Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination pagination settings
   * @return The paginated Transfers search result
   * @throws CurrencyCloudException When an error occurs
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
        transfer.getUniqueRequestId(),
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
        transfer.getReason(),
        transfer.getUniqueRequestId()
    );
  }

  public Transfer cancelTransfer(String id) throws ResponseException {
    return api.cancelTransfer(
        authToken,
        userAgent,
        id
    );
  }

  ///////////////////////////////////////////////////////////////////
  ///// VANS ////////////////////////////////////////////////////////

  /**
   * @param virtualAccount Non-null properties will be used for querying. Null values will be ignored.
   * @param pagination     pagination settings
   * @return The paginated Ibans search result
   * @throws CurrencyCloudException When an error occurs
   * @deprecated This call has been disabled in the backend. Retained for backwards compatibility. It will be removed in
   * future release
   */
  @Deprecated
  public VirtualAccounts findVirtualAccounts(@Nullable VirtualAccount virtualAccount, @Nullable Pagination pagination) throws CurrencyCloudException {
    throw new UnexpectedException("findVirtualAccounts is no longer available", new UnsupportedOperationException());
  }

  ///////////////////////////////////////////////////////////////////

  ///////////////////////////////////////////////////////////////////
  ///// Withdrawal Accounts /////////////////////////////////////////

  /**
   * Finds Withdrawal Accounts matching the accountId. If the account Id is omitted the withdrawal accounts
   * for the house account and all sub-accounts are returned
   *
   * @param accountId  account Id
   * @param pagination pagination settings
   * @return The paginated withdrawal accounts search result
   * @throws CurrencyCloudException When an error occurs
   */
  public WithdrawalAccounts findWithdrawalAccounts(@Nullable String accountId, @Nullable Pagination pagination) throws CurrencyCloudException {
    if (pagination == null) {
      pagination = Pagination.builder().build();
    }

    return api.findWithdrawalAccounts(
        authToken,
        userAgent,
        accountId,
        pagination.getPage(),
        pagination.getPerPage(),
        pagination.getOrder(),
        pagination.getOrderAscDesc()
    );
  }

  /**
   * Submits an ACH pull request from a specific withdrawal account.
   * The funds will be pulled into the account the specified withdrawal account is related to
   *
   * @param withdrawalAccountId The withdrawal account ID to pull the funds from
   * @param reference           The reference that appears on the statement
   * @param amount              The amount of funds to pull in USD
   * @return Withdrawal Account Pulled Funds Details
   * @throws CurrencyCloudException When an error occurs
   */
  public WithdrawalAccountFunds withdrawalAccountsPullFunds(String withdrawalAccountId,
                                                            String reference, BigDecimal amount
  ) throws CurrencyCloudException {

    return api.withdrawalAccountsPullFunds(
        authToken,
        userAgent,
        withdrawalAccountId,
        reference,
        amount
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
    try {
      Field field = updated.getClass().getField("modificationTracker");
      ModificationTracker modificationTracker = (ModificationTracker) field.get(updated);
      return modificationTracker.getDirtyProperties();
    }catch (ReflectiveOperationException reflectiveOperationException){
      //ModificationTracker not attached to updated entity
      log.warn("Can't check if the object is dirty because it was not obtained from the client: {}", updated);
      return null;
    }
  }

  /**
   * @return A proxy object whose getters will return null for unchanged entity's properties
   * (and new values for changed properties), or the entity itself if the entity was not
   * enhanced for dirty checking.
   * @throws com.currencycloud.client.CurrencyCloudClient.NoChangeException if the entity was dirty-checked
   *                                                                        but there were no changes.
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

        try {
          return new ByteBuddy()
                  .subclass(entityClass)
                  .method(any())
                  .intercept(MethodDelegation.to(new ModifiedValueProvider(values)))
                  .make()
                  .load(entityClass.getClassLoader())
                          .getLoaded()
                          .getDeclaredConstructor()
                          .newInstance();
        }catch (ReflectiveOperationException reflectiveOperationExceptiontion){
          log.error("Unable to create proxy for {}", entityClass.getSimpleName());
          log.error(reflectiveOperationExceptiontion.getMessage(), reflectiveOperationExceptiontion);
        }
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

  private static class NoChangeException extends Exception {
  }

  /**
   * Flattens list of string objects into a comma separated string in Java 1.7 and earlier.
   * If the list is empty then null is returned.
   *
   * @param stringArray List of strings
   * @return a flattened comma separated string object.
   */
  private static String flattenList(List<String> stringArray) {
    if (stringArray != null && stringArray.size() > 0) {
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
