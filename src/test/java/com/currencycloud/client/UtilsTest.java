package com.currencycloud.client;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UtilsTest {

    private static final List<String> ANIMALS = Arrays.asList("elephant", "cow", "pig");
    private static final String SEPARATOR = "|";

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
        assertThat(Utils.getRootCause(e), equalTo((Throwable) e));
    }

    @Test
    public void join_validListAndSeparator_listInverted() {
        String result = Utils.join(ANIMALS, SEPARATOR);
        assertThat(result, is("elephant|cow|pig"));
    }

    @Test
    public void join_singleItemInList_itemReturned() {
        String item = "kangaroo";
        String result = Utils.join(Collections.singletonList(item), SEPARATOR);
        assertThat(result, is(item));
    }

    @Test
    public void join_nullList_nullReturned() {
        String result = Utils.join(null, SEPARATOR);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void join_nullSeparator_nullReturned() {
        String result = Utils.join(ANIMALS, null);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void joinInverse_validListAndSeparator_listInverted() {
        String result = Utils.joinInverse(ANIMALS, SEPARATOR);
        assertThat(result, is("pig|cow|elephant"));
    }

    @Test
    public void joinInverse_singleItemInList_itemReturned() {
        String item = "kangaroo";
        String result = Utils.joinInverse(Collections.singletonList(item), SEPARATOR);
        assertThat(result, is(item));
    }

    @Test
    public void joinInverse_nullList_nullReturned() {
        String result = Utils.joinInverse(null, SEPARATOR);
        assertThat(result, is(nullValue()));
    }

    @Test
    public void joinInverse_nullSeparator_nullReturned() {
        String result = Utils.joinInverse(ANIMALS, null);
        assertThat(result, is(nullValue()));
    }
}
