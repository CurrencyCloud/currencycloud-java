package com.currencycloud.client.dirty;

import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.Beneficiary;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import static com.currencycloud.client.dirty.ReflectionUtils.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class ReflectionUtilsTest {

    @Test
    public void shouldRecognizeSetters() throws Exception {
        assertThat(isSetter(Object.class.getMethod("toString")), equalTo(false));
        assertThat(isSetter(Account.class.getMethod("getStatus")), equalTo(false));
        assertThat(isSetter(Account.class.getMethod("setBrand", String.class)), equalTo(true));
        assertThat(isSetter(AtomicInteger.class.getMethod("set", int.class)), equalTo(false));
    }

    @Test
    public void shouldGetPropertyFromSetterMethod() throws Exception {
        assertThat(getPropertyFromSetter(Object.class.getMethod("toString")), is(nullValue()));
        assertThat(getPropertyFromSetter(Account.class.getMethod("getStatus")), is(nullValue()));
        assertThat(getPropertyFromSetter(Account.class.getMethod("setBrand", String.class)), equalTo("brand"));
        assertThat(getPropertyFromSetter(AtomicInteger.class.getMethod("set", int.class)), is(nullValue()));
    }

    @Test
    public void shouldReturnCorrectGetter() throws Exception {
        shouldReturnCorrectGetter(Account.class, "status", "getStatus", String.class);
        shouldReturnCorrectGetter(Beneficiary.class, "defaultBeneficiary", "getDefaultBeneficiary", Boolean.class);
    }

    private void shouldReturnCorrectGetter(
            Class<?> clazz,
            String property,
            String getterName,
            Class<?> getterType
    ) throws NoSuchMethodException {
        Method getter = getGetterFromProperty(clazz, property);
        assertThat(getter, is(not(nullValue())));
        assertThat(getter.getName(), equalTo(getterName));
        assertThat(getter.getReturnType(), Matchers.<Class>equalTo(getterType));
    }

}
