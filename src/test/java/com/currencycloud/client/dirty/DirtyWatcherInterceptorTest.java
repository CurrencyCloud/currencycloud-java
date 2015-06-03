package com.currencycloud.client.dirty;

import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.Beneficiary;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicInteger;

import static com.currencycloud.client.dirty.DirtyWatcherInterceptor.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DirtyWatcherInterceptorTest {

    @Test
    public void shouldRecognizeSetters() throws Exception {
        assertThat(isSetter(Object.class.getMethod("toString"))).isFalse();
        assertThat(isSetter(Account.class.getMethod("getStatus"))).isFalse();
        assertThat(isSetter(Account.class.getMethod("setBrand", String.class))).isTrue();
        assertThat(isSetter(AtomicInteger.class.getMethod("set", int.class))).isFalse();
    }

    @Test
    public void shouldGetPropertyFromSetterMethod() throws Exception {
        assertThat(getPropertyFromSetter(Object.class.getMethod("toString"))).isNull();
        assertThat(getPropertyFromSetter(Account.class.getMethod("getStatus"))).isNull();
        assertThat(getPropertyFromSetter(Account.class.getMethod("setBrand", String.class))).isEqualTo("brand");
        assertThat(getPropertyFromSetter(AtomicInteger.class.getMethod("set", int.class))).isNull();
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
        assertThat(getter).isNotNull();
        assertThat(getter.getName()).isEqualTo(getterName);
        assertThat(getter.getReturnType()).isEqualTo(getterType);
    }

}