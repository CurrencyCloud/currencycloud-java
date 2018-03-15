package com.currencycloud.client;

import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    @Test
    public void returnsRootCauseForChain() throws Exception {
        NullPointerException root = new NullPointerException();
        assertThat(
                Utils.getRootCause(new RuntimeException(new IllegalArgumentException(root))),
                equalTo((Throwable) root)
        );
    }
    @Test
    public void returnsRootCauseForCauseless() throws Exception {
        RuntimeException e = new RuntimeException();
        assertThat(Utils.getRootCause(e), equalTo((Throwable)e));
    }
}
