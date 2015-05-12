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
            @FormParam("account_number") @Nullable String accountNumber,
            @FormParam("routing_code_type_1") @Nullable String routingCodeType1,
            @FormParam("routing_code_value_1") @Nullable String routingCodeValue1,
            @FormParam("routing_code_type_2") @Nullable String routingCodeType2,
            @FormParam("routing_code_value_2") @Nullable String routingCodeValue2,
            @FormParam("bic_swift") @Nullable String bicSwift,
            @FormParam("iban") @Nullable String iban,
            @FormParam("bank_address") @Nullable List<String> bankAddress,
            @FormParam("bank_name") @Nullable String bankName,
            @FormParam("bank_account_type") @Nullable String bankAccountType,
            @FormParam("beneficiary_entity_type") @Nullable String beneficiaryEntityType,
            @FormParam("beneficiary_company_name") @Nullable String beneficiaryCompanyName,
            @FormParam("beneficiary_first_name") @Nullable String beneficiaryFirstName,
            @FormParam("beneficiary_last_name") @Nullable String beneficiaryLastName,
            @FormParam("beneficiary_city") @Nullable String beneficiaryCity,
            @FormParam("beneficiary_postcode") @Nullable String beneficiaryPostcode,
            @FormParam("beneficiary_state_or_province") @Nullable String beneficiaryStateOrProvince,
            @FormParam("beneficiary_date_of_birth	date") @Nullable Date beneficiaryDateOfBirth,
            @FormParam("beneficiary_identification_type") @Nullable String beneficiaryIdentificationType,
            @FormParam("beneficiary_identification_value") @Nullable String beneficiaryIdentificationValue,
            @FormParam("payment_types") @Nullable List<String> paymentTypes,
            @FormParam("on_behalf_of") @Nullable String onBehalfOf
    ) throws CurrencyCloudException;

    /** Create Beneficiary */
    /** Retrieve a Beneficiary */
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
