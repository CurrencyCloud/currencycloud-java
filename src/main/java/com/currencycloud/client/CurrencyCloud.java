package com.currencycloud.client;

import com.currencycloud.client.model.AuthenticateResponse;
import com.currencycloud.client.model.Beneficiary;
import com.currencycloud.client.model.CurrencyCloudException;

import javax.annotation.Nullable;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
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
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    Object endSession(
            @HeaderParam("X-Auth-Token") String authToken
    ) throws CurrencyCloudException;


    ///////////////////////////////////////////////////////////////////
    ///// ACCOUNTS API ////////////////////////////////////////////////
    /** Create Account */
    /** Retrieve an Account */
    /** Update an Account */
    /** Find Account */
    /** Account (logged-in Contact) */
    /** MANDATORY_DOCUMENTS API */
    /** Mandatory Documents */
    /** Updates an existing Mandatory Document */


    ///////////////////////////////////////////////////////////////////
    ///// BALANCES API ////////////////////////////////////////////////
    /** Find Balances */
    /** Retrieve a Balance */


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
            @PathParam("id") String id,
            @Nullable @FormParam("on_behalf_of") String onBehalfOf
    ) throws CurrencyCloudException;

    /** Update a Beneficiary */
    /** Find Beneficiaries */
    /** Delete Beneficiary */
    ///////////////////////////////////////////////////////////////////
    ///// CONTACTS API ////////////////////////////////////////////////
    /** Create reset token and send email notification */
    /** Create Contact */
    /** Retrieve a Contact */
    /** Update a Contact */
    /** Find Contact */
    /** Contact (logged-in Contact) */
    ///////////////////////////////////////////////////////////////////
    ///// CONVERSIONS API /////////////////////////////////////////////
    /** Create a Conversion */
    /** Retrieve a Conversion */
    /** Find a Conversion */
    ///////////////////////////////////////////////////////////////////
    ///// PAYERS API ///////////////////////////////////////////////////
    /** Retrieve a Payer */
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
