package com.currencycloud.client.dirty;

import com.currencycloud.client.model.Entity;
import com.fasterxml.jackson.databind.util.StdConverter;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.modifier.FieldPersistence;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

public abstract class DirtyWatcherDeserializer<E extends Entity> extends StdConverter<E, E> {

    private static final Logger log = LoggerFactory.getLogger(DirtyWatcherDeserializer.class);

    @Override
    public E convert(E watched) {
        ModificationTracker modificationTracker = new ModificationTracker(watched);
        try {
            E proxy = (E) new ByteBuddy()
                    .subclass(watched.getClass())
                    .method(ElementMatchers.any())
                    .intercept(MethodDelegation.to(modificationTracker))
                    .defineField("modificationTracker", ModificationTracker.class, Visibility.PUBLIC, FieldPersistence.TRANSIENT)
                    .make()
                    .load(Entity.class.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
            Field modificationTrackerField = proxy.getClass().getDeclaredField("modificationTracker");
            modificationTrackerField.set(proxy, modificationTracker);
            return proxy;
        } catch (ReflectiveOperationException e) {
            log.error("Unable to deserialize entity as proxy", e);
            return watched;
        }
    }

    public static class Account extends DirtyWatcherDeserializer<com.currencycloud.client.model.Account> {}
    public static class Contact extends DirtyWatcherDeserializer<com.currencycloud.client.model.Contact> {}
    public static class Beneficiary extends DirtyWatcherDeserializer<com.currencycloud.client.model.Beneficiary> {}
    public static class Payment extends DirtyWatcherDeserializer<com.currencycloud.client.model.Payment> {}
    public static class Payer extends DirtyWatcherDeserializer<com.currencycloud.client.model.Payer> {}
}
