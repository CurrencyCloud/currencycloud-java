package com.currencycloud.client;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CurrencyCloudClientOnBehalfOfTest {

    protected CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555", null, null);

    @Test
    public void testSetsTheValueOnTheSessionAndRemovesItWhenDone() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        client.onBehalfOfDo(obo, new OnBehalfRunnable() {
            @Override
            public void run(OnBehalfClient client) {
                assertThat(client.getOnBehalfOf(), equalTo(obo));
            }
        });
    }

    @Test
    public void testStillRemovesTheValueFromTheSessionOnError() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        try {
            client.onBehalfOfDo(obo, new OnBehalfRunnable() {
                @Override
                public void run(OnBehalfClient client) {
                    assertThat(client.getOnBehalfOf(), equalTo(obo));
                    throw new RuntimeException("Completed Expected error");
                }
            });
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Completed Expected error"));
        }
    }

    @Test
    public void testOnBehalf() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        OnBehalfClient onBehalfClient = client.onBehalfOf(obo);
        assertThat(onBehalfClient.getOnBehalfOf(), equalTo(obo));
    }

    @Test
    public void testPreventsIllegalIdFormat() throws Exception {
        try {
            client.onBehalfOfDo("Richard Nienaber", new OnBehalfRunnable() {
                @Override
                public void run(OnBehalfClient client) {
                    throw new AssertionError("Should raise exception");
                }
            });
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("is not a UUID"));
        }
    }
}