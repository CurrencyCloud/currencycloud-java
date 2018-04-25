package com.currencycloud.client;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CurrencyCloudClientOnBehalfOfTest {

    protected CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555", null, null);

    @Test
    public void testSetsTheValueOnTheSessionAndRemovesItWhenDone() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        assertThat(client.getOnBehalfOf(), nullValue());
        client.onBehalfOfDo(obo, new Runnable() {
            @Override
            public void run() {
                assertThat(client.getOnBehalfOf(), equalTo(obo));
            }
        });
        assertThat(client.getOnBehalfOf(), nullValue());
    }

    @Test
    public void testStillRemovesTheValueFromTheSessionOnError() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        assertThat(client.getOnBehalfOf(), nullValue());
        try {
            client.onBehalfOfDo(obo, new Runnable() {
                @Override
                public void run() {
                    assertThat(client.getOnBehalfOf(), equalTo(obo));
                    throw new RuntimeException("Completed Expected error");
                }
            });
        } catch (RuntimeException e) {
            assertThat(e.getMessage(), equalTo("Completed Expected error"));
        }
        assertThat(client.getOnBehalfOf(), nullValue());
    }

    @Test
    public void testPreventsReentrantUsage() throws Exception {
        final String obo = "c6ece846-6df1-461d-acaa-b42a6aa74045";
        assertThat(client.getOnBehalfOf(), nullValue());
        try {
            client.onBehalfOfDo(obo, new Runnable() {
                @Override
                public void run() {
                    client.onBehalfOfDo("f57b2d33-652c-4589-a8ff-7762add2706d", new Runnable() {
                        @Override public void run() {
                            throw new AssertionError("Should raise exception");
                        }
                    });
                }
            });
        } catch (IllegalStateException e) {
            assertThat(e.getMessage(), containsString("Can't nest on-behalf-of calls"));
        }
        assertThat(client.getOnBehalfOf(), nullValue());
    }

    @Test
    public void testPreventsIllegalIdFormat() throws Exception {
        assertThat(client.getOnBehalfOf(), nullValue());
        try {
            client.onBehalfOfDo("Richard Nienaber", new Runnable() {
                @Override
                public void run() {
                    throw new AssertionError("Should raise exception");
                }
            });
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), containsString("is not a UUID"));
        }
        assertThat(client.getOnBehalfOf(), nullValue());
    }
}
