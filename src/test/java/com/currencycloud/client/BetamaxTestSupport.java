package com.currencycloud.client;

import co.freeside.betamax.Recorder;
import org.junit.Rule;

import java.io.File;

public class BetamaxTestSupport extends JsonTestSupport {
    protected CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555");

    @Rule
    public Recorder createRecorder() {
        Recorder recorder = new Recorder();
        String tc = this.getClass().getSimpleName();
        recorder.setTapeRoot(new File(recorder.getTapeRoot(), (tc).substring(0, tc.length() - "Test".length())));
        return recorder;
    }
}
