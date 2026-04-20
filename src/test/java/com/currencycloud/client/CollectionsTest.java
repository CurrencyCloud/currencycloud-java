package com.currencycloud.client;

import com.currencycloud.client.model.CompleteCollectionsScreeningResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class CollectionsTest extends TestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "334cbfdb9ba9bfb6ffd499b0c6af6b12");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testCanCompleteCollectionsScreening() {
        CompleteCollectionsScreeningResponse response = client.completeCollectionsScreening(
            "bdcca5e6-32fe-45f6-9476-6f8f518e6270",
            true,
            "accepted"
        );

        assertThat(response, notNullValue());
        assertThat(response.getTransactionId(), equalTo("bdcca5e6-32fe-45f6-9476-6f8f518e6270"));
        assertThat(response.getAccountId(), equalTo("7a116d7d-6310-40ae-8d54-0ffbe41dc1c9"));
        assertThat(response.getHouseAccountId(), equalTo("7a116d7d-6310-40ae-8d54-0ffbe41dc1c9"));
        assertThat(response.getResult(), notNullValue());
        assertThat(response.getResult().getReason(), equalTo("accepted"));
        assertThat(response.getResult().getAccepted(), equalTo(true));
    }
}
