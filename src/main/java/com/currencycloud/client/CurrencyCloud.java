package com.currencycloud.client;

import com.currencycloud.client.model.*;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Path("v2")
@Produces(MediaType.APPLICATION_JSON)
public interface CurrencyCloud {

    ///////////////////////////////////////////////////////////////////
    ///// AUTHENTICATE API ////////////////////////////////////////////

    /** API User Login */
    @POST
    @Path("authenticate/api")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    AuthenticateResponse authenticate(
            @FormParam("login_id") String loginId,
            @FormParam("api_key") String apiKey
    ) throws CurrencyCloudException;

    /** End API session */
    @POST
    @Path("authenticate/close_session")
    Object endSession(
            @HeaderParam("X-Auth-Token") String authToken
    ) throws CurrencyCloudException;


    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS API ////////////////////////////////////////////////

    /** Create Account */
    @POST
    @Path("accounts/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Account createAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @FormParam("account_name") String accountName,
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
    ) throws CurrencyCloudException;

    /** Retrieve an Account */
    @GET
    @Path("accounts/{id}")
    Account retrieveAccount(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String accountId,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Update an Account */
    @POST
    @Path("accounts/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Account updateAccount(
            @HeaderParam("X-Auth-Token") String authToken,
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
    ) throws CurrencyCloudException;

    /** Find Account */
    @GET
    @Path("accounts/find")
    Accounts findAccounts(
            @HeaderParam("X-Auth-Token") String authToken,
            @Nullable @FormParam("account_name") String accountName,
            @Nullable @FormParam("brand") String brand,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("street") String street,
            @Nullable @FormParam("city") String city,
            @Nullable @FormParam("state_or_province") String stateOrProvince,
            @Nullable @FormParam("postal_code") String postalCode,
            @Nullable @FormParam("country") String country,
            @Nullable @FormParam("spread_table") String spreadTable,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws CurrencyCloudException;

    /** Account (logged-in Contact) */
    @GET
    @Path("accounts/current")
    Account currentAccount(
            @HeaderParam("X-Auth-Token") String authToken
    ) throws CurrencyCloudException;

    ///////////////////////////////////////////////////////////////////
    ///// BALANCES API ////////////////////////////////////////////////

    /** Find Balances */
    @GET
    @Path("balances/find")
    Balances findBalances(
            @HeaderParam("X-Auth-Token") String authToken,
            @Nullable @QueryParam("amount_from") BigDecimal amountFrom,
            @Nullable @QueryParam("amount_to") BigDecimal amountTo,
            @Nullable @QueryParam("as_at_date") Date asAtDate,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc
    ) throws CurrencyCloudException;

    /** Retrieve a Balance */
    @GET
    @Path("balances/{currency}")
    Balance findBalance(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("currency") String currency
    ) throws CurrencyCloudException;

    ///////////////////////////////////////////////////////////////////
    ///// BENEFICIARIES API ///////////////////////////////////////////

    /** Validate Beneficiary bank details */
    @POST
    @Path("beneficiaries/validate")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary validateBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @FormParam("bank_country") String bankCountry,
            @FormParam("currency") String currency,
            @FormParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @FormParam("account_number") String accountNumber,
            @Nullable @FormParam("routing_code_type_1") String routingCodeType1,
            @Nullable @FormParam("routing_code_value_1") String routingCodeValue1,
            @Nullable @FormParam("routing_code_type_2") String routingCodeType2,
            @Nullable @FormParam("routing_code_value_2") String routingCodeValue2,
            @Nullable @FormParam("bic_swift") String bicSwift,
            @Nullable @FormParam("iban") String iban,
            @Nullable @FormParam("bank_address") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth	date") Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types") List<String> paymentTypes,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Create Beneficiary */
    @POST
    @Path("beneficiaries/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary createBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @FormParam("bank_account_holder_name") String bankAccountHolderName,
            @FormParam("bank_country") String bankCountry,
            @FormParam("currency") String currency,
            @FormParam("name") String name,
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
            @Nullable @FormParam("bank_address") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth	date") Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types") List<String> paymentTypes,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Retrieve a Beneficiary */
    @GET
    @Path("beneficiaries/{id}")
    Beneficiary retrieveBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Update a Beneficiary */
    @POST
    @Path("beneficiaries/{id}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Beneficiary updateBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken, @PathParam("id") String beneficiaryId,
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
            @Nullable @FormParam("bank_address") List<String> bankAddress,
            @Nullable @FormParam("bank_name") String bankName,
            @Nullable @FormParam("bank_account_type") String bankAccountType,
            @Nullable @FormParam("beneficiary_entity_type") String beneficiaryEntityType,
            @Nullable @FormParam("beneficiary_company_name") String beneficiaryCompanyName,
            @Nullable @FormParam("beneficiary_first_name") String beneficiaryFirstName,
            @Nullable @FormParam("beneficiary_last_name") String beneficiaryLastName,
            @Nullable @FormParam("beneficiary_city") String beneficiaryCity,
            @Nullable @FormParam("beneficiary_postcode") String beneficiaryPostcode,
            @Nullable @FormParam("beneficiary_state_or_province") String beneficiaryStateOrProvince,
            @Nullable @FormParam("beneficiary_date_of_birth	date") Date beneficiaryDateOfBirth,
            @Nullable @FormParam("beneficiary_identification_type") String beneficiaryIdentificationType,
            @Nullable @FormParam("beneficiary_identification_value") String beneficiaryIdentificationValue,
            @Nullable @FormParam("payment_types") List<String> paymentTypes,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Find Beneficiaries */
    @GET
    @Path("beneficiaries/find")
    Beneficiaries findBeneficiaries(
            @HeaderParam("X-Auth-Token") String authToken,
            @Nullable @QueryParam("bank_account_holder_name") String bankAccountHolderName,
            @Nullable @QueryParam("beneficiary_country") String beneficiaryCountry,
            @Nullable @QueryParam("currency") String currency,
            @Nullable @QueryParam("account_number") String accountNumber,
            @Nullable @QueryParam("routing_code_type") String routingCodeType,
            @Nullable @QueryParam("routing_code_value") String routingCodeValue,
            @Nullable @QueryParam("payment_types") String paymentTypes,
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
            @Nullable @QueryParam("beneficiary_date_of_birth") Date beneficiaryDateOfBirth,
            @Nullable @QueryParam("page") Integer page,
            @Nullable @QueryParam("per_page") Integer perPage,
            @Nullable @QueryParam("order") String order,
            @Nullable @QueryParam("order_asc_desc") Pagination.SortOrder orderAscDesc,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Delete Beneficiary */
    @POST
    @Path("beneficiaries/{id}/delete")
    Beneficiary deleteBeneficiary(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String id,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS API ////////////////////////////////////////////////

    /** Create reset token and send email notification */
    @POST
    @Path("contacts/reset_token/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Object createResetToken(
            @HeaderParam("X-Auth-Token") String authToken,
            @Nullable @FormParam("login_id") String loginId
    );

    /** Create Contact */
    @POST
    @Path("contacts/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Contact createContact(
            @HeaderParam("X-Auth-Token") String authToken,
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
            @Nullable @FormParam("date_of_birth") Date dateOfBirth
    );

    /** Retrieve a Contact */
    @GET
    @Path("contacts/{id}")
    Contact retrieveContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String contactId
    ) throws CurrencyCloudException;

    /** Update a Contact */
    @POST
    @Path("contacts/{id}")
    Contact updateContact(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String contactId,
            @Nullable @FormParam("first_name") String firstName,
            @Nullable @FormParam("last_name") String lastName,
            @Nullable @FormParam("email_address") String emailAddress,
            @Nullable @FormParam("phone_number") String phoneNumber,
            @Nullable @FormParam("your_reference") String yourReference,
            @Nullable @FormParam("mobile_phone_number") String mobilePhoneNumber,
            @Nullable @FormParam("login_id") String login_id,
            @Nullable @FormParam("status") String status,
            @Nullable @FormParam("locale") String locale,
            @Nullable @FormParam("timezone") String timezone,
            @Nullable @FormParam("date_of_birth") Date dateOfBirth
    ) throws CurrencyCloudException;

    /** Find Contact */
    @GET
    @Path("contacts/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Contacts findContacts(
            @HeaderParam("X-Auth-Token") String authToken,
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
    ) throws CurrencyCloudException;

    /** Contact (logged-in Contact) */
    @GET
    @Path("contacts/current")
    Contact currentContact(
            @HeaderParam("X-Auth-Token") String authToken
    ) throws CurrencyCloudException;


    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS API /////////////////////////////////////////////

    /** Create a Conversion */
    @POST
    @Path("conversions/create")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Conversion createConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @FormParam("buy_currency") String buyCurrency,
            @FormParam("sell_currency") String sellCurrency,
            @FormParam("fixed_side") String fixedSide,
            @FormParam("amount") BigDecimal amount,
            @FormParam("reason") String reason,
            @FormParam("term_agreement") Boolean termAgreement,
            @Nullable @FormParam("conversion_date") Date conversionDate,
            @Nullable @FormParam("client_rate") BigDecimal clientRate,
            @Nullable @FormParam("currency_pair") String currencyPair,
            @Nullable @FormParam("client_buy_amount") BigDecimal clientBuyAmount,
            @Nullable @FormParam("client_sell_amount") BigDecimal clientSellAmount,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    );

    /** Retrieve a Conversion */
    @GET
    @Path("conversions/{id}")
    Conversion retrieveConversion(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String conversionId
    ) throws CurrencyCloudException;

    /** Find a Conversion */
    @GET
    @Path("conversions/find")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Conversions findConversions(
            @HeaderParam("X-Auth-Token") String authToken,
            @Nullable @QueryParam("short_reference") String shortReference,
            @Nullable @QueryParam("status") String status,
            @Nullable @QueryParam("partner_status") String partnerStatus,
            @Nullable @QueryParam("buy_currency") String buyCurrency,
            @Nullable @QueryParam("sell_currency") String sellCurrency,
            @Nullable @QueryParam("conversion_ids") String conversionIds,
            @Nullable @QueryParam("created_at_from") String createdAtFrom,
            @Nullable @QueryParam("created_at_to") String createdAtTo,
            @Nullable @QueryParam("updated_at_from") String updatedAtFrom,
            @Nullable @QueryParam("updated_at_to") String updatedAtTo,
            @Nullable @QueryParam("currency_pair") String currencyPair,
            @Nullable @QueryParam("partner_buy_amount_from") String partnerBuyAmountFrom,
            @Nullable @QueryParam("partner_buy_amount_to") String partnerBuyAmountTo,
            @Nullable @QueryParam("partner_sell_amount_from") String partnerSellAmountFrom,
            @Nullable @QueryParam("partner_sell_amount_to") String partnerSellAmountTo,
            @Nullable @QueryParam("buy_amount_from") String buyAmountFrom,
            @Nullable @QueryParam("buy_amount_to") String buyAmountTo,
            @Nullable @QueryParam("sell_amount_from") String sellAmountFrom,
            @Nullable @QueryParam("sell_amount_to") String sellAmountTo,
            @Nullable @QueryParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    ///////////////////////////////////////////////////////////////////
    ///// PAYERS API ///////////////////////////////////////////////////

    /** Retrieve a Payer */
    @GET
    @Path("payers/{id}")
    Payer retrievePayer(
            @HeaderParam("X-Auth-Token") String authToken,
            @PathParam("id") String payerId
    ) throws CurrencyCloudException;

    ///////////////////////////////////////////////////////////////////
    ///// PAYMENTS API ////////////////////////////////////////////////
    /** Create a Payment */
    /** Retrieve a Payment */
    /** Update a Payment */
    /** Find a Payment */
    /** Delete a Payment */
    ///////////////////////////////////////////////////////////////////
    ///// RATES API ///////////////////////////////////////////////////
    /** Retrieve Multiple Rates */
    /** Detailed Rates */
    ///////////////////////////////////////////////////////////////////
    ///// REFERENCE API ///////////////////////////////////////////////
    /** Beneficiary Required Details */
    /** Available Currencies */
    /** Conversion Dates */
    /** Settlement Accounts */
    ///////////////////////////////////////////////////////////////////
    ///// SETTLEMENTS API /////////////////////////////////////////////
    /** Create a Settlement */
    /** Retrieve a Settlement */
    /** Find Settlements */
    /** Delete a Settlement */
    /** Add a Conversion to a Settlement */
    /** Remove a Conversion from a Settlement */
    /** Release a Settlement */
    /** Unrelease a Settlement */
    ///////////////////////////////////////////////////////////////////
    ///// TRANSACTIONS API ////////////////////////////////////////////
    /** Retrieve a Transaction */
    /** Find Transactions */
}
