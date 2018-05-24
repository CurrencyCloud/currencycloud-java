package com.currencycloud.client;

import com.currencycloud.client.model.*;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * This is the low-level Currency Cloud HTTP API Java implementation. This interface's methods map directly to
 * the HTTP endpoints, as described in the HTTP API documentation.
 */
@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface CurrencyCloud {

    ///////////////////////////////////////////////////////////////////
    ///// AUTHENTICATE API ////////////////////////////////////////////

    /** API User Login */
    @POST
    @Path("authenticate/api")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @NoAutoAuth
    AuthenticateResponse authenticate(
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("login_id") String loginId,
            @FormParam("api_key") String apiKey
    ) throws ResponseException;

    /** End API session */
    @POST
    @Path("authenticate/close_session")
    @NoAutoAuth
    Object endSession(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent
    ) throws ResponseException;


    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS API ////////////////////////////////////////////////

    /** Create Account */
    @POST
    @Path("accounts/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Account createAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("account_name") String accountName,
            @FormParam("legal_entity_type") String legalEntityType,
            @FormParam("street") String street,
            @FormParam("city") String city,
            @FormParam("postal_code") String postalCode,
            @FormParam("country") String country,
            @Nullable @FormParam("state_or_province") String stateOrProvince,
            @Nullable @FormParam("brand") String brand,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("spread_table") String spreadTable,
            @Nullable @FormParam("identification_type") String identificationType,
            @Nullable @FormParam("identification_value") String identificationValue,
            @Nullable @FormParam("api_trading") Boolean apiTrading,
            @Nullable @FormParam("online_trading") Boolean onlineTrading,
            @Nullable @FormParam("phone_trading") Boolean phoneTrading
    ) throws ResponseException;

    /** Retrieve an Account */
    @GET
    @Path("accounts/{id}")
    Account retrieveAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String accountId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Update an Account */
    @POST
    @Path("accounts/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Account updateAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String accountId,
            @Nullable @FormParam("account_name") String accountName,
            @Nullable @FormParam("legal_entity_type") String legalEntityType,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("street") String street,
            @Nullable @FormParam("city") String city,
            @Nullable @FormParam("state_or_province") String stateOrProvince,
            @Nullable @FormParam("postal_code") String postalCode,
            @Nullable @FormParam("country") String country,
            @Nullable @FormParam("spread_table") String spreadTable,
            @Nullable @FormParam("identification_type") String identificationType,
            @Nullable @FormParam("identification_value") String identificationValue
    ) throws ResponseException;

    /** Find Account */
    @GET
    @Path("accounts/find")
    Accounts findAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("account_name") String accountName,
            @Nullable @QueryParam("legal_entity_type") String legalEntityType,
            @Nullable @QueryParam("brand") String brand,
            @Nullable @QueryParam("your_reference") String yourReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("street") String street,
            @Nullable @QueryParam("city") String city,
            @Nullable @QueryParam("state_or_province") String stateOrProvince,
            @Nullable @QueryParam("postal_code") String postalCode,
            @Nullable @QueryParam("country") String country,
            @Nullable @QueryParam("spread_table") String spreadTable,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Account (logged-in Contact) */
    @GET
    @Path("accounts/current")
    Account currentAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES API ////////////////////////////////////////////////

    /** Find Balances */
    @GET
    @Path("balances/find")
    @Deprecated
    Balances findBalances(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("as_at_date") Date asAtDate,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Find Balances */
    @GET
    @Path("balances/find")
    Balances findBalances(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("as_at_date") Date asAtDate,
            @Nullable @QueryParam("scope") String scope,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Retrieve a Balance */
    @GET
    @Path("balances/{currency}")
    Balance retrieveBalance(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("currency") String currency,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES API ///////////////////////////////////////////

    /** Validate Beneficiary bank details */
    @POST
    @Path("beneficiaries/validate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary validateBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("bank_country") String bankCountry,
            @FormParam("currency") String currency,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("beneficiary_address") String beneficiaryAddress,
            @Nullable @FormParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @FormParam("account_number") String accountNumber,
            @Nullable @FormParam("routing_code_type_1") String routingCodeType1,
            @Nullable @FormParam("routing_code_value_1") String routingCodeValue1,
            @Nullable @FormParam("routing_code_type_2") String routingCodeType2,
            @Nullable @FormParam("routing_code_value_2") String routingCodeValue2,
            @Nullable @FormParam("bic_swift") String bicSwift,
            @Nullable @FormParam("iban") String iban,
            @Nullable @FormParam("bank_address[]") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth") java.sql.Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types[]") List<String> paymentTypes
    ) throws ResponseException;

    /** Create Beneficiary */
    @POST
    @Path("beneficiaries/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary createBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("bank_account_holder_name") String bankAccountHolderName,
            @FormParam("bank_country") String bankCountry,
            @FormParam("currency") String currency,
            @FormParam("name") String name,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("email") String email,
            @Nullable @FormParam("beneficiary_address") String beneficiaryAddress,
            @Nullable @FormParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @FormParam("account_number") String accountNumber,
            @Nullable @FormParam("routing_code_type_1") String routingCodeType1,
            @Nullable @FormParam("routing_code_value_1") String routingCodeValue1,
            @Nullable @FormParam("routing_code_type_2") String routingCodeType2,
            @Nullable @FormParam("routing_code_value_2") String routingCodeValue2,
            @Nullable @FormParam("bic_swift") String bicSwift,
            @Nullable @FormParam("iban") String iban,
            @Nullable @FormParam("default_beneficiary") Boolean defaultBeneficiary,
            @Nullable @FormParam("bank_address[]") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth") java.sql.Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types[]") List<String> paymentTypes
    ) throws ResponseException;

    /** Retrieve a Beneficiary */
    @GET
    @Path("beneficiaries/{id}")
    Beneficiary retrieveBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Update a Beneficiary */
    @POST
    @Path("beneficiaries/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary updateBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String beneficiaryId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("bank_account_holder_name") String bankAccountHolderName,
            @Nullable @FormParam("bank_country") String bankCountry,
            @Nullable @FormParam("currency") String currency,
            @Nullable @FormParam("name") String name,
            @Nullable @FormParam("email") String email,
            @Nullable @FormParam("beneficiary_address") String beneficiaryAddress,
            @Nullable @FormParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @FormParam("account_number") String accountNumber,
            @Nullable @FormParam("routing_code_type_1") String routingCodeType1,
            @Nullable @FormParam("routing_code_value_1") String routingCodeValue1,
            @Nullable @FormParam("routing_code_type_2") String routingCodeType2,
            @Nullable @FormParam("routing_code_value_2") String routingCodeValue2,
            @Nullable @FormParam("bic_swift") String bicSwift,
            @Nullable @FormParam("iban") String iban,
            @Nullable @FormParam("default_beneficiary") Boolean defaultBeneficiary,
            @Nullable @FormParam("bank_address[]") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth") java.sql.Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types[]") List<String> paymentTypes
    ) throws ResponseException;

    /** Find Beneficiaries */
    @GET
    @Path("beneficiaries/find")
    Beneficiaries findBeneficiaries(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("bank_account_holder_name") String bankAccountHolderName,
            @Nullable @QueryParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("account_number") String accountNumber,
            @Nullable @QueryParam("routing_code_type") String routingCodeType,
            @Nullable @QueryParam("routing_code_value") String routingCodeValue,
            @Nullable @QueryParam("payment_types[]") List<String> paymentTypes,
            @Nullable @QueryParam("bic_swift") String bicSwift,
            @Nullable @QueryParam("iban") String iban,
            @Nullable @QueryParam("default_beneficiary") Boolean defaultBeneficiary,
            @Nullable @QueryParam("bank_name") String bankName,
            @Nullable @QueryParam("bank_account_type") String bankAccountType,
            @Nullable @QueryParam("name") String name,
            @Nullable @QueryParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @QueryParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @QueryParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @QueryParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @QueryParam("beneficiary_city") String beneficiaryCity,
            @Nullable @QueryParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @QueryParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @QueryParam("beneficiary_date_of_birth") java.sql.Date beneficiaryDateOfBirth,
            @Nullable @QueryParam("scope") String scope,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Delete Beneficiary */
    @POST
    @Path("beneficiaries/{id}/delete")
    Beneficiary deleteBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS API ////////////////////////////////////////////////

    /** Create Contact */
    @POST
    @Path("contacts/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Contact createContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("account_id") String accountId,
            @FormParam("first_name") String firstName,
            @FormParam("last_name") String lastName,
            @FormParam("email_address") String emailAddress,
            @FormParam("phone_number") String phoneNumber,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("mobile_phone_number") String mobilePhoneNumber,
            @Nullable @FormParam("login_id") String loginId,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("locale") String locale,
            @Nullable @FormParam("timezone") String timezone,
            @Nullable @FormParam("date_of_birth") java.sql.Date dateOfBirth
    ) throws ResponseException;

    /** Retrieve a Contact */
    @GET
    @Path("contacts/{id}")
    Contact retrieveContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String contactId
    ) throws ResponseException;

    /** Update a Contact */
    @POST
    @Path("contacts/{id}")
    Contact updateContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String contactId,
            @Nullable @FormParam("first_name") String firstName,
            @Nullable @FormParam("last_name") String lastName,
            @Nullable @FormParam("email_address") String emailAddress,
            @Nullable @FormParam("phone_number") String phoneNumber,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("mobile_phone_number") String mobilePhoneNumber,
            @Nullable @FormParam("login_id") String loginId,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("locale") String locale,
            @Nullable @FormParam("timezone") String timezone,
            @Nullable @FormParam("date_of_birth") java.sql.Date dateOfBirth
    ) throws ResponseException;

    /** Find Contact */
    @GET
    @Path("contacts/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Contacts findContacts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("account_name") String accountName,
            @Nullable @QueryParam("account_id") String accountId,
            @Nullable @QueryParam("first_name") String firstName,
            @Nullable @QueryParam("last_name") String lastName,
            @Nullable @QueryParam("email_address") String emailAddress,
            @Nullable @QueryParam("your_reference") String yourReference,
            @Nullable @QueryParam("phone_number") String phoneNumber,
            @Nullable @QueryParam("login_id") String loginId,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("locale") String locale,
            @Nullable @QueryParam("timezone") String timezone,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Contact (logged-in Contact) */
    @GET
    @Path("contacts/current")
    Contact currentContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent
    ) throws ResponseException;


    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS API /////////////////////////////////////////////

    /** Create a Conversion */
    @POST
    @Path("conversions/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Deprecated
    Conversion createConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("buy_currency") String buyCurrency,
            @FormParam("sell_currency") String sellCurrency,
            @FormParam("fixed_side") String fixedSide,
            @FormParam("amount") BigDecimal amount,
            @FormParam("term_agreement") Boolean termAgreement,
            @Nullable @FormParam("conversion_date") Date conversionDate,
            @Nullable @FormParam("client_rate") BigDecimal clientRate,
            @Nullable @FormParam("currency_pair") String currencyPair,
            @Nullable @FormParam("client_buy_amount") BigDecimal clientBuyAmount,
            @Nullable @FormParam("client_sell_amount") BigDecimal clientSellAmount,
            @Nullable @FormParam("reason") String reason,
            @Nullable @FormParam("unique_request_id") String uniqueRequestId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Create a Conversion */
    @POST
    @Path("conversions/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Conversion createConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("buy_currency") String buyCurrency,
            @FormParam("sell_currency") String sellCurrency,
            @FormParam("fixed_side") String fixedSide,
            @FormParam("amount") BigDecimal amount,
            @FormParam("term_agreement") Boolean termAgreement,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("conversion_date") Date conversionDate,
            @Nullable @FormParam("client_buy_amount") BigDecimal clientBuyAmount,
            @Nullable @FormParam("client_sell_amount") BigDecimal clientSellAmount,
            @Nullable @FormParam("reason") String reason,
            @Nullable @FormParam("unique_request_id") String uniqueRequestId
    ) throws ResponseException;

    /** Retrieve a Conversion */
    @GET
    @Path("conversions/{id}")
    Conversion retrieveConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String conversionId
    ) throws ResponseException;

    /** Find a Conversion */
    @GET
    @Path("conversions/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Deprecated
    Conversions findConversions(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("partner_status") String partnerStatus,
            @Nullable @QueryParam("buy_currency") String buyCurrency,
            @Nullable @QueryParam("sell_currency") String sellCurrency,
            @Nullable @QueryParam("conversion_ids[]") Collection<String> conversionIds,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("currency_pair") String currencyPair,
            @Nullable @QueryParam("partner_buy_amount_from") BigDecimal partnerBuyAmountFrom,
            @Nullable @QueryParam("partner_buy_amount_to") BigDecimal partnerBuyAmountTo,
            @Nullable @QueryParam("partner_sell_amount_from") BigDecimal partnerSellAmountFrom,
            @Nullable @QueryParam("partner_sell_amount_to") BigDecimal partnerSellAmountTo,
            @Nullable @QueryParam("buy_amount_from") BigDecimal buyAmountFrom,
            @Nullable @QueryParam("buy_amount_to") BigDecimal buyAmountTo,
            @Nullable @QueryParam("sell_amount_from") BigDecimal sellAmountFrom,
            @Nullable @QueryParam("sell_amount_to") BigDecimal sellAmountTo,
            @Nullable @QueryParam("unique_request_id") String uniqueRequestId,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find a Conversion */
    @GET
    @Path("conversions/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Conversions findConversions(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("partner_status") String partnerStatus,
            @Nullable @QueryParam("buy_currency") String buyCurrency,
            @Nullable @QueryParam("sell_currency") String sellCurrency,
            @Nullable @QueryParam("conversion_ids[]") Collection<String> conversionIds,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("conversion_date_from") Date ConversionDateFrom,
            @Nullable @QueryParam("conversion_date_to") Date ConversionDateTo,
            @Nullable @QueryParam("currency_pair") String currencyPair,
            @Nullable @QueryParam("partner_buy_amount_from") BigDecimal partnerBuyAmountFrom,
            @Nullable @QueryParam("partner_buy_amount_to") BigDecimal partnerBuyAmountTo,
            @Nullable @QueryParam("partner_sell_amount_from") BigDecimal partnerSellAmountFrom,
            @Nullable @QueryParam("partner_sell_amount_to") BigDecimal partnerSellAmountTo,
            @Nullable @QueryParam("buy_amount_from") BigDecimal buyAmountFrom,
            @Nullable @QueryParam("buy_amount_to") BigDecimal buyAmountTo,
            @Nullable @QueryParam("sell_amount_from") BigDecimal sellAmountFrom,
            @Nullable @QueryParam("sell_amount_to") BigDecimal sellAmountTo,
            @Nullable @QueryParam("scope") String scope,
            @Nullable @QueryParam("settlement_date_from") Date SettlementDateFrom,
            @Nullable @QueryParam("settlement_date_to") Date SettlementDateTo,
            @Nullable @QueryParam("unique_request_id") String uniqueRequestId,
            @Nullable @QueryParam("bulk_upload_id") String bulkUploadId,
            @Nullable @QueryParam("unallocated_funds") BigDecimal unallocatedFunds,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    ///// IBANS API ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    /** Create an IBAN */
    @POST
    @Path("ibans/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Deprecated
    Iban createIban(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("currency") String currency
    ) throws ResponseException;

    /** Retrieve an IBAN */
    @GET
    @Path("ibans")
    @Deprecated
    Ibans retrieveIbans(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Find IBANs */
    @GET
    @Path("ibans")
    Ibans findIbans(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Retrieve IBAN of Sub-Account(s) */
    @GET
    @Path("ibans/subaccounts/{id}")
    Ibans retrieveSubAccountsIban(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Find IBANs of Sub-Account(s) */
    @GET
    @Path("ibans/subaccounts/find")
    Ibans findSubAccountsIbans(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    ///// PAYERS API ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    /** Retrieve a Payer */
    @GET
    @Path("payers/{id}")
    Payer retrievePayer(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String payerId
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// PAYMENTS API ////////////////////////////////////////////////

    /** Create a Payment */
    @POST
    @Path("payments/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Payment createPayment(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("currency") String currency,
            @FormParam("beneficiary_id") String beneficiaryId,
            @FormParam("amount") BigDecimal amount,
            @FormParam("reason") String reason,
            @FormParam("reference") String reference,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("payment_date") java.sql.Date paymentDate,
            @Nullable @FormParam("payment_type") String paymentType,
            @Nullable @FormParam("conversion_id") String conversionId,
            @Nullable @FormParam("payer_entity_type") String payerEntityType,
            @Nullable @FormParam("payer_company_name") String payerCompanyName,
            @Nullable @FormParam("payer_first_name") String payerFirstName,
            @Nullable @FormParam("payer_last_name") String payerLastName,
            @Nullable @FormParam("payer_address") String payerAddress,
            @Nullable @FormParam("payer_city") String payerCity,
            @Nullable @FormParam("payer_country") String payerCountry,
            @Nullable @FormParam("payer_postcode") String payerPostcode,
            @Nullable @FormParam("payer_state_or_province") String payerStateOrProvince,
            @Nullable @FormParam("payer_date_of_birth") java.sql.Date payerDateOfBirth,
            @Nullable @FormParam("payer_identification_type") String payerIdentificationType,
            @Nullable @FormParam("payer_identification_value") String payerIdentificationValue,
            @Nullable @FormParam("unique_request_id") String uniqueRequestId,
            @Nullable @FormParam("ultimate_beneficiary_name") String ultimateBeneficiaryName
    ) throws ResponseException;

    /** Retrieve a Payment */
    @GET
    @Path("payments/{id}")
    Payment retrievePayment(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Update a Payment */
    @POST
    @Path("payments/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Payment updatePayment(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String paymentId,
            @FormParam("currency") String currency,
            @FormParam("beneficiary_id") String beneficiaryId,
            @FormParam("amount") BigDecimal amount,
            @FormParam("reason") String reason,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("reference") String reference,
            @Nullable @FormParam("payment_date") java.sql.Date paymentDate,
            @Nullable @FormParam("payment_type") String paymentType,
            @Nullable @FormParam("conversion_id") String conversionId,
            @Nullable @FormParam("payer_entity_type") String payerEntityType,
            @Nullable @FormParam("payer_company_name") String payerCompanyName,
            @Nullable @FormParam("payer_first_name") String payerFirstName,
            @Nullable @FormParam("payer_last_name") String payerLastName,
            @Nullable @FormParam("payer_address") String payerAddress,
            @Nullable @FormParam("payer_city") String payerCity,
            @Nullable @FormParam("payer_country") String payerCountry,
            @Nullable @FormParam("payer_postcode") String payerPostcode,
            @Nullable @FormParam("payer_state_or_province") String payerStateOrProvince,
            @Nullable @FormParam("payer_date_of_birth") java.sql.Date payerDateOfBirth,
            @Nullable @FormParam("payer_identification_type") String payerIdentificationType,
            @Nullable @FormParam("payer_identification_value") String payerIdentificationValue
    ) throws ResponseException;

    /** Find Payments */
    @GET
    @Path("payments/find")
    @Deprecated
    Payments findPayments(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("amount") BigDecimal amount,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("reason") String reason,
            @Nullable @QueryParam("payment_date_from") java.sql.Date paymentDateFrom,
            @Nullable @QueryParam("payment_date_to") java.sql.Date paymentDateTo,
            @Nullable @QueryParam("transferred_at_from") Date transferredAtFrom,
            @Nullable @QueryParam("transferred_at_to") Date transferredAtTo,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("beneficiary_id") String beneficiaryId,
            @Nullable @QueryParam("conversion_id") String conversionId,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc,
            @Nullable @QueryParam("unique_request_id") String uniqueRequestId,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find Payments */
    @GET
    @Path("payments/find")
    Payments findPayments(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("amount") BigDecimal amount,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("reason") String reason,
            @Nullable @QueryParam("payment_date_from") java.sql.Date paymentDateFrom,
            @Nullable @QueryParam("payment_date_to") java.sql.Date paymentDateTo,
            @Nullable @QueryParam("transferred_at_from") Date transferredAtFrom,
            @Nullable @QueryParam("transferred_at_to") Date transferredAtTo,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("beneficiary_id") String beneficiaryId,
            @Nullable @QueryParam("conversion_id") String conversionId,
            @Nullable @QueryParam("with_deleted") Boolean withDeleted,
            @Nullable @QueryParam("payment_group_id") String paymentGroupId,
            @Nullable @QueryParam("unique_request_id") String uniqueRequestId,
            @Nullable @QueryParam("scope") String scope,
            @Nullable @QueryParam("bulk_upload_id") String bulkUploadId,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Delete a Payment */
    @POST
    @Path("payments/{id}/delete")
    Payment deletePayment(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String paymentId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Retrieve a Payment Submission*/
    @GET
    @Path("payments/{id}/submission")
    Payment retrievePaymentSubmission(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;


    ///////////////////////////////////////////////////////////////////
    ///// RATES API ///////////////////////////////////////////////////

    /** Retrieve Multiple Rates */
    @GET
    @Path("rates/find")
    Rates findRates(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @QueryParam("currency_pair") Collection<String> currencyPair,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("ignore_invalid_pairs") Boolean ignoreInvalidPairs
    ) throws ResponseException;

    /** Detailed Rates */
    @GET
    @Path("rates/detailed")
    DetailedRate detailedRates(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @QueryParam("buy_currency") String buyCurrency,
            @QueryParam("sell_currency") String sellCurrency,
            @QueryParam("fixed_side") String fixedSide,
            @QueryParam("amount") BigDecimal amount,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("conversion_date") java.sql.Date conversionDate
    ) throws ResponseException;


    ///////////////////////////////////////////////////////////////////
    ///// REFERENCE API ///////////////////////////////////////////////

    /** Beneficiary Required Details */
    @GET
    @Path("reference/beneficiary_required_details")
    BeneficiaryRequiredDetails beneficiaryRequiredDetails(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("bank_account_country") String bankAccountCountry,
            @Nullable @QueryParam("beneficiary_country") String beneficiaryCountry
    ) throws ResponseException;

    /** Available Currencies */
    @GET
    @Path("reference/currencies")
    Currencies currencies(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent
    ) throws ResponseException;

    /** Conversion Dates */
    @GET
    @Path("reference/conversion_dates")
    ConversionDates conversionDates(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @QueryParam("conversion_pair") String conversionPair,
            @Nullable @QueryParam("start_date") Date startDate
    ) throws ResponseException;
    
    /** Payment Dates */
    @GET
    @Path("reference/payment_dates")
    PaymentDates paymentDates(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @QueryParam("currency") String currency,
            @Nullable @QueryParam("start_date") Date startDate
    ) throws ResponseException;

    /** Settlement Accounts */
    @GET
    @Path("reference/settlement_accounts")
    @Deprecated
    SettlementAccounts settlementAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency
    ) throws ResponseException;

    /** Settlement Accounts */
    @GET
    @Path("reference/settlement_accounts")
    SettlementAccounts settlementAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("account_id") String accountId
    ) throws ResponseException;

    /** Payer Required Details */
    @GET
    @Path("reference/payer_required_details")
    PayerRequiredDetails payerRequiredDetails(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @QueryParam("payer_country") String payerCountry,
            @Nullable @QueryParam("payer_entity_type") String payerEntityType,
            @Nullable @QueryParam("payment_type") String paymentType
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// SETTLEMENTS API /////////////////////////////////////////////

    /** Create a Settlement */
    @POST
    @Path("settlements/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Deprecated
    Settlement createSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Create a Settlement */
    @POST
    @Path("settlements/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Settlement createSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf,
            @Nullable @FormParam("type") String type
    ) throws ResponseException;

    /** Retrieve a Settlement */
    @GET
    @Path("settlements/{id}")
    Settlement retrieveSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find Settlements */
    @GET
    @Path("settlements/find")
    Settlements findSettlements(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("released_at_from") Date releasedAtFrom,
            @Nullable @QueryParam("released_at_to") Date releasedAtTo,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Delete a Settlement */
    @POST
    @Path("settlements/{id}/delete")
    Settlement deleteSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String settlementId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Add a Conversion to a Settlement */
    @POST
    @Path("settlements/{id}/add_conversion")
    Settlement addConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String settlementId,
            @FormParam("conversion_id") String conversionId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Remove a Conversion from a Settlement */
    @POST
    @Path("settlements/{id}/remove_conversion")
    Settlement removeConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String settlementId,
            @FormParam("conversion_id") String conversionId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Release a Settlement */
    @POST
    @Path("settlements/{id}/release")
    Settlement releaseSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String settlementId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Unrelease a Settlement */
    @POST
    @Path("settlements/{id}/unrelease")
    Settlement unreleaseSettlement(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String settlementId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;


    ///////////////////////////////////////////////////////////////////
    ///// TRANSACTIONS API ////////////////////////////////////////////
    /** Retrieve a Transaction */
    @GET
    @Path("transactions/{id}")
    Transaction retrieveTransaction(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find Transactions */
    @GET
    @Path("transactions/find")
    @Deprecated
    Transactions findTransactions(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("amount") BigDecimal amount,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("action") String action,
            @Nullable @QueryParam("related_entity_type") String relatedEntityType,
            @Nullable @QueryParam("related_entity_id") String relatedEntityId,
            @Nullable @QueryParam("related_entity_short_reference") String relatedEntityShortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("type") String type,
            @Nullable @QueryParam("reason") String reason,
            @Nullable @QueryParam("settles_at_from") java.sql.Date settlesAtFrom,
            @Nullable @QueryParam("settles_at_to") java.sql.Date settlesAtTo,
            @Nullable @QueryParam("created_at_from") java.sql.Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") java.sql.Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") java.sql.Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") java.sql.Date updatedAtTo,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find Transactions */
    @GET
    @Path("transactions/find")
    Transactions findTransactions(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("amount") BigDecimal amount,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("action") String action,
            @Nullable @QueryParam("related_entity_type") String relatedEntityType,
            @Nullable @QueryParam("related_entity_id") String relatedEntityId,
            @Nullable @QueryParam("related_entity_short_reference") String relatedEntityShortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("type") String type,
            @Nullable @QueryParam("reason") String reason,
            @Nullable @QueryParam("settles_at_from") java.sql.Date settlesAtFrom,
            @Nullable @QueryParam("settles_at_to") java.sql.Date settlesAtTo,
            @Nullable @QueryParam("created_at_from") java.sql.Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") java.sql.Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") java.sql.Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") java.sql.Date updatedAtTo,
            @Nullable @QueryParam("completed_at_from") java.sql.Date completedAtFrom,
            @Nullable @QueryParam("completed_at_to") java.sql.Date completedAtTo,
            @Nullable @QueryParam("beneficiary_id") String beneficiaryId,
            @Nullable @QueryParam("currency_pair") String currencyPair,
            @Nullable @QueryParam("scope") String scope,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    ///////////////////////////////////////////////////////////////////
    ///// TRANSFERS API ////////////////////////////////////////////
    /** Retrieve a Transfer */
    @GET
    @Path("transfers/{id}")
    Transfer retrieveTransfer(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws ResponseException;

    /** Find Transfers */
    @GET
    @Path("transfers/find")
    Transfers findTransfers(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("source_account_id") String sourceAccountId,
            @Nullable @QueryParam("destination_account_id") String destinationAccountId,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("created_at_from") Date createdAtFrom,
            @Nullable @QueryParam("created_at_to") Date createdAtTo,
            @Nullable @QueryParam("updated_at_from") Date updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") Date updatedAtTo,
            @Nullable @QueryParam("completed_at_from") Date completedAtFrom,
            @Nullable @QueryParam("completed_at_to") Date completedAtTo,
            @Nullable @QueryParam("creator_contact_id") String creatorContactId,
            @Nullable @QueryParam("creator_account_id") String creatorAccountId,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    @POST
    @Path("transfers/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Transfer createTransfer(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @FormParam("source_account_id") String sourceAccountId,
            @FormParam("destination_account_id") String destinationAccountId,
            @FormParam("currency") String currency,
            @FormParam("amount") BigDecimal amount,
            @Nullable @FormParam("reason") String reason
    ) throws ResponseException;

    ///// VANS API ///////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////////

    /** Find VANs */
    @GET
    @Path("virtual_accounts")
    VirtualAccounts findVirtualAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Retrieve VAN of Sub-Account(s) */
    @GET
    @Path("virtual_accounts/subaccounts/{id}")
    VirtualAccounts retrieveSubAccountsVirtualAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @PathParam("id") String id,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

    /** Find VANs of Sub-Account(s) */
    @GET
    @Path("virtual_accounts/subaccounts/find")
    VirtualAccounts findSubAccountsVirtualAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @HeaderParam("User-Agent") String userAgent,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws ResponseException;

}
