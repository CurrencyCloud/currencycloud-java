package com.currencycloud.client;

import org.junit.Assert;
import org.junit.Test;

/**
 *
 * Class to simulate multiple threads accessing a single CurrencyCloudClient instance.
 *
 */
class TestThread extends Thread {

    private final String contactId;
    private final int threadId;
    private final CurrencyCloudClient client;

    public TestThread(int threadId, CurrencyCloudClient client, String contactId) {
        this.client = client;
        this.threadId = threadId;
        this.contactId = contactId;
    }

    @Override
    public void run() {
        System.out.println("TestThread \"" + threadId + "\" running.");
        int i=0;
        while (i<1000) {
            try {
                client.onBehalfOfDo(contactId, new Runnable() {
                    @Override
                    public void run() {
                        String onBehalfOf = client.getOnBehalfOf();
                        /**
                         * It is always expected that \"threadId\" = 2nd last character of onBehalf to ensure that the Runnable for that Thread is running
                         */
                        Assert.assertEquals(threadId, Integer.parseInt(onBehalfOf.substring(onBehalfOf.length() - 2, onBehalfOf.length() - 1)));
                    }
                });
                i++;
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }
}

/**
 * Test multiple threads attempting to access a single instance of the CurrencyCloudClient class.
 *
 */
public class CurrencyCloudClientConcurrencyTest {

    protected CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555", null, null);

    @Test
    public void testConcurrentAccess() throws Exception {

        TestThread[] threads = new TestThread[10];
        int counter = 0;
        for (TestThread thread : threads) {
            thread = new TestThread(counter, client, "f57b2d33-652c-4589-a8ff-7762add270" + counter + "d");
            thread.start();
            threads[counter++] = thread;
        }
        long l = System.currentTimeMillis();
        for (TestThread thread : threads) {
            thread.join();
        }
        System.out.println("All done in " + (System.currentTimeMillis() - l));
    }
}