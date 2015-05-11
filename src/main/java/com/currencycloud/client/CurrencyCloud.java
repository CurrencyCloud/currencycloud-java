package com.currencycloud.client;

import com.currencycloud.client.model.AuthenticateResponse;
import com.currencycloud.client.model.CurrencyCloudException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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

    /** End API session
     * @param authToken*/
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
