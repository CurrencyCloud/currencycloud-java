package com.currencycloud.client;

import co.freeside.betamax.Recorder;
import org.junit.Rule;

import java.io.File;

public class BetamaxTestSupport extends JsonTestSupport {

    @Rule
    public Recorder createRecorder() {
        Recorder recorder = new Recorder();
        String tc = this.getClass().getSimpleName();
        recorder.setTapeRoot(new File(recorder.getTapeRoot(), (tc).substring(0, tc.length() - "Test".length())));
        return recorder;
    }

    protected CurrencyCloudClient prepareTestClient(String loginId, String apiKey, String authToken) {
        CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555", loginId, apiKey);
        client.setAuthToken(authToken);
        return client;
    }
}
