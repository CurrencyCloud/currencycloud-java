package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.CurrencyCloudException;
import com.currencycloud.client.model.ErrorMessage;
import org.junit.Test;
import org.testng.Assert;

@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "UnnecessaryBoxing"})
public class ErrorTest extends BetamaxTestSupport {

    private String loginId = "rjnienaber@gmail.com";
    private String apiKey = "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0";

    @Test
    @Betamax(tape = "contains_full_details_for_api_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testContainsFullDetailsForApiError() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "ef0fd50fca1fb14c1fab3a8436b9ecb57528f0";
        CurrencyCloudException error = testFailedLogin("auth_invalid_user_login_details", 400);
        Assert.assertEquals(error.getErrorMessages().get("api_key").size(), 1);
        Assert.assertEquals(error.getErrorMessages().get("api_key").get(0).getCode(), "api_key_length_is_invalid");
        Assert.assertEquals(error.getErrorMessages().get("api_key").get(0).getMessage(), "api_key should be 64 character(s) long");
        Assert.assertEquals(error.getErrorMessages().get("api_key").get(0).getParams().get("length"), new Integer(64));
    }

    @Test
    @Betamax(tape = "is_raised_on_a_bad_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnABadRequest() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "ef0fd50fca1fb14c1fab3a8436b9ecb57528f0";
        CurrencyCloudException error = testFailedLogin("auth_invalid_user_login_details", 400);
        Assert.assertEquals(error.getErrorMessages().get("api_key").size(), 1);
        ErrorMessage errorMessage = error.getErrorMessages().get("api_key").get(0);
        Assert.assertEquals(errorMessage.getCode(), "api_key_length_is_invalid");
        Assert.assertEquals(errorMessage.getMessage(), "api_key should be 64 character(s) long");
        Assert.assertEquals(errorMessage.getParams().get("length"), new Integer(64));
    }

    @Test
    @Betamax(tape = "is_raised_on_a_forbidden_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAForbiddenRequest() throws Exception {
        CurrencyCloudException error = testFailedLogin("auth_failed", 403);
/*
        // todo: use hamcrest or similar assertion library
        expect(e.code).to eq('auth_failed')
        expect(e.raw_response).to_not be_nil
        expect(e.status_code).to eq(403)
        expect(e.messages.length).to eq(1)

        error_message = e.messages[0]
        expect(error_message.field).to eq('username')
        expect(error_message.code).to eq('invalid_supplied_credentials')
        expect(error_message.message).to eq('Authentication failed with the supplied credentials')
        expect(error_message.params).to be_empty
*/
    }

    @Test
    @Betamax(tape = "is_raised_on_an_internal_server_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAnInternalServerError() throws Exception {
        CurrencyCloudException error = testFailedLogin("internal_application_error", 500);
/*
        expect(error.code).to eq('internal_application_error')
        expect(error.raw_response).to_not be_nil
        expect(error.status_code).to eq(500)
        expect(error.messages.length).to eq(1)

        error_message = error.messages[0]
        expect(error_message.field).to eq('base')
        expect(error_message.code).to eq('internal_application_error')
        expect(error_message.message).to eq('A general application error occurred')
        expect(error_message.params).to include("request_id" => 2771875643610572878)
*/
    }

    @Test
    @Betamax(tape = "is_raised_on_incorrect_authentication_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnIncorrectAuthenticationDetails() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "efb5ae2af84978b7a37f18dd61c8bbe139b403009faea83484405a3dcb64c4d8";
        CurrencyCloudException e = testFailedLogin("auth_failed", 401);
        Assert.assertEquals(e.getErrorMessages().get("username").size(), 1);
        Assert.assertEquals(e.getErrorMessages().get("username").get(0).getCode(), "invalid_supplied_credentials");
        Assert.assertEquals(e.getErrorMessages().get("username").get(0).getMessage(), "Authentication failed with the supplied credentials");
        Assert.assertTrue(e.getErrorMessages().get("username").get(0).getParams().isEmpty());
    }

    @Test
    @Betamax(tape = "is_raised_when_too_many_requests_have_been_issued", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedWhenTooManyRequestsHaveBeenIssued() throws Exception {
        loginId = "rjnienaber@gmail.com2";
        CurrencyCloudException error = testFailedLogin("too_many_requests", 429);
/*
        expect(error.code).to eq('too_many_requests')
        expect(error.raw_response).to_not be_nil
        expect(error.status_code).to eq(429)
        expect(error.messages.length).to eq(1)

        error_message = error.messages[0]
        expect(error_message.field).to eq('base')
        expect(error_message.code).to eq('too_many_requests')
        expect(error_message.message).to eq('Too many requests have been made to the api. Please refer to the Developer Center for more information')
        expect(error_message.params).to be_empty
*/
    }

    // todo: handling of timout errors

    // todo: handling of "resource not found"

    private CurrencyCloudException testFailedLogin(String errorCode, int httpStatusCode) {
        try {
            client.authenticate(loginId, apiKey);
            throw new AssertionError("Should have failed");
        } catch (CurrencyCloudException e) {
            Assert.assertEquals(e.getHttpStatusCode(), httpStatusCode);
            Assert.assertEquals(e.getErrorCode(), errorCode);
            Assert.assertEquals(e.getErrorMessages().size(), 1);
            return e;
        }
    }
}