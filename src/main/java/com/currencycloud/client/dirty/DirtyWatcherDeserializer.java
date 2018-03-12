package com.currencycloud.client.dirty;

import com.currencycloud.client.model.Entity;
import com.fasterxml.jackson.databind.util.StdConverter;
import net.sf.cglib.proxy.Enhancer;

public abstract class DirtyWatcherDeserializer<E extends Entity> extends StdConverter<E, E> {

    @Override
    public E convert(E watched) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(watched.getClass());
        enhancer.setCallback(new ModificationTracker(watched));
        //noinspection unchecked
        return (E) enhancer.create();
    }

    public static class Account extends DirtyWatcherDeserializer<com.currencycloud.client.model.Account> {}
    public static class Contact extends DirtyWatcherDeserializer<com.currencycloud.client.model.Contact> {}
    public static class Beneficiary extends DirtyWatcherDeserializer<com.currencycloud.client.model.Beneficiary> {}
    public static class Payment extends DirtyWatcherDeserializer<com.currencycloud.client.model.Payment> {}
    public static class Payer extends DirtyWatcherDeserializer<com.currencycloud.client.model.Payer> {}
}
