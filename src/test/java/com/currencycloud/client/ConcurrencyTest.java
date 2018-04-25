package com.currencycloud.client;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Test multiple threads attempting to access a single instance of the CurrencyCloudClient class.
 *
 */
public class ConcurrencyTest extends BetamaxTestSupport {
    private CurrencyCloudClient client;
    private String loginId = "development@currencycloud.com";
    private String apiKey = "deadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeefdeadbeef";
    private int numIterations = 512;
    private int numThreads = 256;

    @Before
    public void prepareClient() {
        client = prepareTestClient(loginId, apiKey, null);
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    public void testConcurrentAccess() throws Exception {

        ThreadSupport[] threads = new ThreadSupport[numThreads];
        int counter = 0;

        for (ThreadSupport thread : threads) {
            thread = new ThreadSupport(counter, client, "deadbeef-dead-beef-dead-" + String.format("%1$012d", counter), numIterations);
            thread.start();
            threads[counter++] = thread;
        }

        long l = System.currentTimeMillis();

        for (ThreadSupport thread : threads) {
            thread.join();
        }

        System.out.println("Concurrency test finsihed succesfully in " + (System.currentTimeMillis() - l) + " msecs");
    }

    /**
     *
     * Class to simulate multiple threads accessing a single CurrencyCloudClient instance.
     *
     */
    private class ThreadSupport extends Thread {

        private final String contactId;
        private final int threadId;
        private final int iterations;
        private final Logger log = LoggerFactory.getLogger(CurrencyCloudClient.class);
        private final CurrencyCloudClient client;

        ThreadSupport(int threadId, CurrencyCloudClient client, String contactId) {
            this.client = client;
            this.threadId = threadId;
            this.contactId = contactId;
            this.iterations = 1000;
        }

        ThreadSupport(int threadId, CurrencyCloudClient client, String contactId, int iterations) {
            this.client = client;
            this.threadId = threadId;
            this.contactId = contactId;
            this.iterations = 1000;
            assert iterations > 0;
        }

        @Override
        public void run() {
            System.out.println("ThreadSupport \"" + threadId + "\" running.");
            int i=0;
            while (i < iterations) {
                try {
                    client.onBehalfOfDo(contactId, new Runnable() {
                        @Override
                        public void run() {
                            String onBehalfOf = client.getOnBehalfOf();
                            /**
                             * It is always expected that \"threadId\" = 12th last characters of onBehalf to ensure that the Runnable for that Thread is running
                             */
                            assertThat(threadId, equalTo(Integer.parseInt(onBehalfOf.substring(onBehalfOf.length() - 12, onBehalfOf.length()))));
                        }
                    });
                    i++;
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
