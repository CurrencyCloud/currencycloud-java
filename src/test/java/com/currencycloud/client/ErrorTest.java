package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.CurrencyCloudException;
import org.junit.Test;
import org.testng.Assert;

@SuppressWarnings("ThrowableResultOfMethodCallIgnored")
public class ErrorTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "contains_full_details_for_api_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testContainsFullDetailsForApiError() throws Exception {
        CurrencyCloudException e = testFailedLogin("non-existent-login-id", "ef0fd50fca1fb14c1fab3a8436b9ecb57528f0", "auth_invalid_user_login_details", 400);
        Assert.assertEquals(e.getErrorMessages().get("api_key").size(), 1);
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getCode(), "api_key_length_is_invalid");
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getMessage(), "api_key should be 64 character(s) long");
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getParams().get("length"), new Integer(64));
    }

    @Test
    @Betamax(tape = "is_raised_on_a_bad_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnABadRequest() throws Exception {
        CurrencyCloudException e = testFailedLogin("non-existent-login-id", "ef0fd50fca1fb14c1fab3a8436b9ecb57528f0", "auth_invalid_user_login_details", 400);
        Assert.assertEquals(e.getErrorMessages().get("api_key").size(), 1);
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getCode(), "api_key_length_is_invalid");
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getMessage(), "api_key should be 64 character(s) long");
        Assert.assertEquals(e.getErrorMessages().get("api_key").get(0).getParams().get("length"), new Integer(64));
    }

    @Test
    @Betamax(tape = "is_raised_on_a_forbidden_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAForbiddenRequest() throws Exception {
        testFailedLogin("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0", "auth_failed", 403);
    }

    @Test
    @Betamax(tape = "is_raised_on_an_internal_server_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAnInternalServerError() throws Exception {
        testFailedLogin("rjnienaber@gmail.com", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0", "internal_application_error", 500);
    }

    @Test
    @Betamax(tape = "is_raised_on_incorrect_authentication_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnIncorrectAuthenticationDetails() throws Exception {
        CurrencyCloudException e = testFailedLogin("non-existent-login-id", "efb5ae2af84978b7a37f18dd61c8bbe139b403009faea83484405a3dcb64c4d8", "auth_failed", 401);
        Assert.assertEquals(e.getErrorMessages().get("username").size(), 1);
        Assert.assertEquals(e.getErrorMessages().get("username").get(0).getCode(), "invalid_supplied_credentials");
        Assert.assertEquals(e.getErrorMessages().get("username").get(0).getMessage(), "Authentication failed with the supplied credentials");
        Assert.assertEquals(e.getErrorMessages().get("username").get(0).getParams().size(), 0);
    }

    @Test
    @Betamax(tape = "is_raised_when_too_many_requests_have_been_issued", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedWhenTooManyRequestsHaveBeenIssued() throws Exception {
        testFailedLogin("rjnienaber@gmail.com2", "ef0fd50fca1fb14c1fab3a8436b9ecb65f02f129fd87eafa45ded8ae257528f0", "too_many_requests", 429);
    }

    private CurrencyCloudException testFailedLogin(String loginId, String apiKey, String errorCode, int httpStatusCode) {
        try {
            client.authenticate(loginId, apiKey);
            throw new AssertionError("An exception should be thown.");
        } catch (CurrencyCloudException e) {
            Assert.assertEquals(e.getHttpStatusCode(), httpStatusCode);
            Assert.assertEquals(e.getErrorCode(), errorCode);
            Assert.assertEquals(e.getErrorMessages().size(), 1);
            return e;
        }
    }
}