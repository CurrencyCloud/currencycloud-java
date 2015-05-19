package com.currencycloud.client;

import co.freeside.betamax.Recorder;
import org.junit.Rule;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BetamaxTestSupport {
    private final SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX");
    protected CurrencyCloudClient client = new CurrencyCloudClient("http://localhost:5555");

    @Rule
    public Recorder createRecorder() {
        Recorder recorder = new Recorder();
        String tc = this.getClass().getSimpleName();
        recorder.setTapeRoot(new File(recorder.getTapeRoot(), (tc).substring(0, tc.length() - "Test".length())));
        return recorder;
    }

    protected Date parseDate(String str) {
        try {
            return timeFormat.parse(str);
        } catch (ParseException e) {
            throw new RuntimeException("Cannot parse time: " + str);
        }
    }
}
