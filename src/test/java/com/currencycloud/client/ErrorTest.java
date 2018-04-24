package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.exception.*;
import com.currencycloud.client.model.ErrorMessage;
import org.eclipse.jetty.io.WriterOutputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings({"ThrowableResultOfMethodCallIgnored", "UnnecessaryBoxing"})
public class ErrorTest extends BetamaxTestSupport {

    private String loginId = "development@currencycloud.com";
    private String apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef";

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "contains_full_details_for_api_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testContainsFullDetailsForApiError() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
        BadRequestException error = testFailedLogin("auth_invalid_user_login_details", 400, BadRequestException.class);
        ErrorMessage errorMessage = error.getErrors().get(0);
        assertThat(errorMessage.getField(), equalTo("api_key"));
        assertThat(errorMessage.getCode(), equalTo("api_key_length_is_invalid"));
        assertThat(errorMessage.getMessage(), equalTo("api_key should be 64 character(s) long"));
        assertThat(errorMessage.getParams().get("length"), instanceOf(Integer.class));
        assertThat((Integer) errorMessage.getParams().get("length"), equalTo(Integer.valueOf(64)));

        String expectedErrorPattern = interpolate(
                readFile("/errors/contains_full_details_for_api_error.yaml"),
                buildMap("error.platform", CurrencyCloudException.PLATFORM)
        );

        assertThat(error.toString(), equalTo(expectedErrorPattern));
    }

    @Test
    @Betamax(tape = "is_raised_on_a_bad_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnABadRequest() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
        BadRequestException error = testFailedLogin("auth_invalid_user_login_details", 400, BadRequestException.class);
        ErrorMessage errorMessage = error.getErrors().get(0);
        assertThat(errorMessage.getField(), equalTo("api_key"));
        assertThat(errorMessage.getCode(), equalTo("api_key_length_is_invalid"));
        assertThat(errorMessage.getMessage(), equalTo("api_key should be 64 character(s) long"));
        assertThat(errorMessage.getParams().get("length"), instanceOf(Integer.class));
        assertThat((Integer)errorMessage.getParams().get("length"), equalTo(Integer.valueOf(64)));
    }

    @Test
    @Betamax(tape = "is_raised_on_incorrect_authentication_details", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnIncorrectAuthenticationDetails() throws Exception {
        loginId = "non-existent-login-id";
        apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
        AuthenticationException error = testFailedLogin("auth_failed", 401, AuthenticationException.class);
        assertThat(error.getErrors().size(), equalTo(1));
        assertThat(error.getErrors().get(0).getField(), equalTo("username"));
        assertThat(error.getErrors().get(0).getCode(), equalTo("invalid_supplied_credentials"));
        assertThat(error.getErrors().get(0).getMessage(), equalTo("Authentication failed with the supplied credentials"));
        assertThat(error.getErrors().get(0).getParams(), anEmptyMap());
    }

    @Test
    public void testIsRaisedOnUnexpectedError() throws Exception {
        // No Betamax here. The call will fail with java.net.ConnectException
        // because there is (hopefully) no server at localhost:5555.
        CurrencyCloudClient client = prepareTestClient(loginId, apiKey, null);
        try {
            client.authenticate();
            throw new AssertionError("Should have failed");
        } catch (UnexpectedException error) {
            String expectedErrorPattern = interpolate(
                    readFile("/errors/is_raised_on_unexpected_error.yaml"),
                    buildMap("error.platform", CurrencyCloudException.PLATFORM)
            );
            assertThat(error.toString(), equalTo(expectedErrorPattern));
        }
    }

    @Test
    @Betamax(tape = "is_raised_on_a_forbidden_request", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAForbiddenRequest() throws Exception {
        ForbiddenException error = testFailedLogin("auth_failed", 403, ForbiddenException.class);
        assertThat(error.getErrorCode(), equalTo("auth_failed"));
        assertThat(error.getResponse().statusCode, equalTo(403));


        ErrorMessage errorMessage = error.getErrors().get(0);
        assertThat(errorMessage.getField(), equalTo("username"));
        assertThat(errorMessage.getCode(), equalTo("invalid_supplied_credentials"));
        assertThat(errorMessage.getMessage(), equalTo("Authentication failed with the supplied credentials"));
        assertThat(errorMessage.getParams(), is(anEmptyMap()));
    }

    @Test
    @Betamax(tape = "is_raised_when_a_resource_is_not_found", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedWhenAResourceIsNotFound() throws Exception {
        CurrencyCloudClient client = prepareTestClient(loginId, apiKey, "656485646b068f6e9c81e3d885fa54f5");

        try {
            client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");
            assertThat("Should fail.", false);
        } catch (NotFoundException error) {
            assertThat(error.getErrorCode(), equalTo("beneficiary_not_found"));
            assertThat(error.getResponse().statusCode, equalTo(404));
            assertThat(error.getErrors().size(), equalTo(1));

            ErrorMessage errorMessage = error.getErrors().get(0);
            assertThat(errorMessage.getField(), equalTo("id"));
            assertThat(errorMessage.getCode(), equalTo("beneficiary_not_found"));
            assertThat(errorMessage.getMessage(), equalTo("Beneficiary was not found for this id"));
            assertThat(errorMessage.getParams(), is(anEmptyMap()));
        }
    }

    @Test
    @Betamax(tape = "is_raised_on_an_internal_server_error", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedOnAnInternalServerError() throws Exception {
        InternalApplicationException error = testFailedLogin("internal_application_error", 500, InternalApplicationException.class);
        ErrorMessage errorMessage = error.getErrors().get(0);
        assertThat(errorMessage.getField(), equalTo("base"));
        assertThat(errorMessage.getCode(), equalTo("internal_application_error"));
        assertThat(errorMessage.getMessage(), equalTo("A general application error occurred"));
        assertThat(errorMessage.getParams(), hasEntry("request_id", (Object)2771875643610572878L));
    }

    @Test
    @Betamax(tape = "is_raised_when_too_many_requests_have_been_issued", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testIsRaisedWhenTooManyRequestsHaveBeenIssued() throws Exception {
        loginId = "development@currencycloud.com2";
        TooManyRequestsException error = testFailedLogin("too_many_requests", 429, TooManyRequestsException.class);
        ErrorMessage errorMessage = error.getErrors().get(0);
        assertThat(errorMessage.getField(), equalTo("base"));
        assertThat(errorMessage.getCode(), equalTo("too_many_requests"));
        assertThat(errorMessage.getMessage(), equalTo("Too many requests have been made to the api. Please refer to the Developer Center for more information"));
        assertThat(errorMessage.getParams(), is(anEmptyMap()));
    }

    private <E extends ApiException> E testFailedLogin(String errorCode, int httpStatusCode, Class<E> exceptionClass) {
        CurrencyCloudClient client = prepareTestClient(loginId, apiKey, null);
        try {
            client.authenticate();
            throw new AssertionError("Should have failed");
        } catch (ApiException error) {
            assertThat(error, instanceOf(exceptionClass));
            assertThat(error.getResponse().statusCode, equalTo(httpStatusCode));
            assertThat(error.getErrorCode(), equalTo(errorCode));
            assertThat(error.getErrors().size(), equalTo(1));
            return exceptionClass.cast(error);
        }
    }

    private String readFile(String file) throws IOException {
        try (
                BufferedReader input = new BufferedReader(new InputStreamReader(ErrorTest.class.getResourceAsStream(file)));
                StringWriter writer = new StringWriter();
                PrintStream printStream = new PrintStream(new WriterOutputStream(writer))
        ) {
            String line;
            while ((line = input.readLine()) != null) {
                printStream.println(line);
            }
            return writer.toString();
        }
    }

    private String interpolate(String string, Map<String, String> vars) {
        for (String var : vars.keySet()) {
            string = string.replace(String.format("${%s}", var), vars.get(var));
        }
        return string;
    }

    private Map<String, String> buildMap(String ... str) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < str.length; i++) {
            map.put(str[i], str[++i]);
        }
        return map;
    }
}
