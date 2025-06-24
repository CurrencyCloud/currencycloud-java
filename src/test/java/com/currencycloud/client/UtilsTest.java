package com.currencycloud.client;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void returnsRootCauseForChain() {
        NullPointerException root = new NullPointerException();
        assertEquals(root, Utils.getRootCause(new RuntimeException(new IllegalArgumentException(root))));
    }
    @Test
    public void returnsRootCauseForCauseless() {
        RuntimeException e = new RuntimeException();
        assertEquals(e, Utils.getRootCause(e));
    }
}
