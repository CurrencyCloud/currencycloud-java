package com.currencycloud.client;

import com.currencycloud.client.model.CurrencyCloudException;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test(groups = "demo-api-calls")
public class ClientTest {

    private CurrencyCloudClient client = new CurrencyCloudClient(CurrencyCloudClient.Environment.demo);

    public void testError() throws Exception {
        try {
            client.authenticate("illegal-id", "illegal-key");
            Assert.assertTrue(false, "Expected an exception");
        } catch (CurrencyCloudException e) {
            Assert.assertEquals(e.getErrorCode(), "auth_invalid_user_login_details");
        }
    }
}