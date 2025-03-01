package com.currencycloud.client.backoff;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.TestSupport;
import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.AuthenticationException;
import com.currencycloud.client.exception.BadRequestException;
import com.currencycloud.client.exception.ForbiddenException;
import com.currencycloud.client.exception.InternalApplicationException;
import com.currencycloud.client.exception.NotFoundException;
import com.currencycloud.client.exception.TooManyRequestsException;
import com.currencycloud.client.model.Beneficiary;
import com.currencycloud.client.model.DetailedRate;
import com.currencycloud.client.model.ResponseException;
import com.currencycloud.client.model.Transactions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class BackOffTest extends TestSupport {

    private final int cap = 90000;
    private final int base = 125;
    private final int attempts = 7;
    private final String loginId = "development@currencycloud.com";
    private final String apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
    private final String authToken = "deadbeefdeadbeefdeadbeefdeadbeef";
    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(loginId, apiKey, authToken);
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testBackOffoverflowMinMaxIsHandled() {
        int wait;

        wait = BackOff.getWaitTime(cap, base, Integer.MAX_VALUE);
        assertThat(wait, equalTo(cap));

        wait = BackOff.getWaitTime(cap, base, Integer.MAX_VALUE + 1);
        assertThat(wait, equalTo(cap));

        wait = BackOff.getWaitTime(cap, base, Integer.MIN_VALUE);
        assertThat(wait, equalTo(cap));

        wait = BackOff.getWaitTime(cap, base, Integer.MIN_VALUE - 1);
        assertThat(wait, equalTo(cap));

        wait = BackOff.getWaitTime(cap, base, -1);
        assertThat(wait, equalTo(cap));

        wait = BackOff.getWaitTime(cap, base, 0);
        assertThat(wait, equalTo(base));
    }

    @Test
    public void testBackOffMaxAttemptsAreExceeded() {
        final BackOffResult<Integer> result = BackOff.<Integer>builder()
                .withCap(cap)
                .withBase(base)
                .withMaxAttempts(attempts)
                .withExceptionType(ApiException.class)
                .withTask(() -> {
                    throw new ApiException(new ResponseException());
                })
                .withExceptionHandler(e -> System.out.println(e.getClass().getCanonicalName()))
                .execute();

        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(result.data, is(Optional.empty()));
    }

    @Test
    public void testBackOffSuccessfulExecution() {
        final BackOffResult<String> result = BackOff.<String>builder()
                .withCap(cap)
                .withBase(base)
                .withMaxAttempts(attempts)
                .withExceptionType(ApiException.class)
                .withTask(() -> "Do something")
                .withExceptionHandler(Throwable::printStackTrace)
                .execute();

        assertThat(result.status, equalTo(BackOffResultStatus.SUCCESSFUL));
        assertThat(result.data.get(), equalTo("Do something"));
    }

    @Test
    public void testBackOffSuccessfulAfterThreeAttempts() {
        final AtomicInteger numAttempts = new AtomicInteger(0);
        final AtomicInteger numExceptions = new AtomicInteger(0);

        final BackOffResult<String> result = BackOff.<String>builder()
                .withCap(cap)
                .withBase(base)
                .withMaxAttempts(attempts)
                .withExceptionType(ApiException.class)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    final boolean shouldThrow = numAttempts.get() < 3;
                    if (shouldThrow) {
                        System.out.println("Throwing exception");
                        throw new ApiException(new ResponseException());
                    }
                    return "Fake result";
                })
                .withExceptionHandler(e -> numExceptions.incrementAndGet())
                .execute();

        assertThat(numAttempts.get(), equalTo(3));
        assertThat(numExceptions.get(), equalTo(2));
        assertThat(result.status, equalTo(BackOffResultStatus.SUCCESSFUL));
        assertThat(result.data.get(), equalTo("Fake result"));
    }

    @Test(expected = BadRequestException.class)
    public void testStandardBadRequestException() {
        CurrencyCloudClient client = prepareTestClient("development@currencycloud.com", "deadbeef", null);
        client.authenticate();

        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test
    public void testBackOffBadRequestException() {
        CurrencyCloudClient client = prepareTestClient("development@currencycloud.com", "deadbeef", null);
        final AtomicInteger numAttempts = new AtomicInteger(0);

        final BackOffResult<Void> result = BackOff.<Void>builder()
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    client.authenticate();
                    return null;
                })
                .withExceptionType(BadRequestException.class)
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(attempts + 1));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test(expected = AuthenticationException.class)
    public void testStandardAuthenticationException() {
        CurrencyCloudClient client = prepareTestClient("nobody@currencycloud.com", "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef", null);
        client.authenticate();

        assertThat(client.getLoginId(), equalTo("nobody@currencycloud.com"));
    }

    @Test
    public void testBackOffAuthenticationException() {
        final AtomicInteger numAttempts = new AtomicInteger(0);
        CurrencyCloudClient client = prepareTestClient("nobody@currencycloud.com", "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef", null);

        final BackOffResult<Void> result = BackOff.<Void>builder()
                .withCap(100)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    client.authenticate();
                    return null;
                })
                .withExceptionType(AuthenticationException.class)
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(attempts + 1));
        assertThat(client.getLoginId(), equalTo("nobody@currencycloud.com"));
    }

    @Test(expected = ForbiddenException.class)
    public void testStandardForbiddenException() {
        Transactions transactions = client.findTransactions(null, null);

        assertThat(transactions, is(nullValue(Transactions.class)));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test
    public void testBackOffForbiddenException() {
        final AtomicInteger numAttempts = new AtomicInteger(0);

        final BackOffResult<Transactions> result = BackOff.<Transactions>builder()
                .withCap(10000)
                .withBase(10)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    Transactions transactions = client.findTransactions(null, null);
                    return transactions;
                })
                .withExceptionType(ForbiddenException.class)
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(attempts + 1));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test(expected = NotFoundException.class)
    public void testStandardNotFoundException() {
        Beneficiary beneficiary = client.retrieveBeneficiary("deadbeef-dead-beef-dead-beefdeadbeef");

        assertThat(beneficiary, is(nullValue(Beneficiary.class)));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test
    public void testBackOffNotFoundException() {
        final AtomicInteger numAttempts = new AtomicInteger(0);

        final BackOffResult<Beneficiary> result = BackOff.<Beneficiary>builder()
                .withCap(10000)
                .withBase(10)
                .withMaxAttempts(5)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    Beneficiary beneficiary = client.retrieveBeneficiary("deadbeef-dead-beef-dead-beefdeadbeef");
                    return beneficiary;
                })
                .withExceptionType(NotFoundException.class)
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(6));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test(expected = TooManyRequestsException.class)
    public void testStandardTooManyRequestsException() {
        DetailedRate detailedRate = client.detailedRates("EUR", "GBP", "buy", new BigDecimal("12345.67"), null, null);

        assertThat(detailedRate, is(nullValue(DetailedRate.class)));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test
    public void testBackOffTooManyRequestsException() {
        final AtomicInteger numAttempts = new AtomicInteger(0);
        final AtomicInteger numExceptions = new AtomicInteger(0);

        final BackOffResult<DetailedRate> result = BackOff.<DetailedRate>builder()
                .withCap(10000)
                .withBase(10)
                .withMaxAttempts(5)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    DetailedRate detailedRate = client.detailedRates("EUR", "GBP", "buy", new BigDecimal("12345.67"), null, null);
                    return detailedRate;
                })
                .withExceptionHandler(e -> numExceptions.incrementAndGet())
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(6));
        assertThat(numExceptions.get(), equalTo(6));
        assertThat(client.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test(expected = InternalApplicationException.class)
    public void testStandardInternalApplicationException() {
        final CurrencyCloudClient internalApplicationErrorClient = prepareTestClient(loginId, "internalapplicationerrorapikey", authToken);
        internalApplicationErrorClient.authenticate();

        assertThat(internalApplicationErrorClient.getLoginId(), equalTo("development@currencycloud.com"));
    }

    @Test
    public void testBackOffInternalApplicationException() {
        final AtomicInteger numAttempts = new AtomicInteger(0);
        final AtomicInteger numExceptions = new AtomicInteger(0);
        final CurrencyCloudClient internalApplicationErrorClient = prepareTestClient(loginId, "internalapplicationerrorapikey", authToken);
        final BackOffResult<Void> result = BackOff.<Void>builder()
                .withCap(90000)
                .withBase(125)
                .withMaxAttempts(7)
                .withTask(() -> {
                    numAttempts.incrementAndGet();
                    internalApplicationErrorClient.authenticate();
                    return null;
                })
                .withExceptionType(InternalApplicationException.class)
                .withExceptionHandler(e -> numExceptions.incrementAndGet())
                .execute();

        assertThat(result.data.isPresent(), is(false));
        assertThat(result.status, equalTo(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS));
        assertThat(numAttempts.get(), equalTo(8));
        assertThat(numExceptions.get(), equalTo(8));
        assertThat(internalApplicationErrorClient.getLoginId(), equalTo("development@currencycloud.com"));
    }
}
