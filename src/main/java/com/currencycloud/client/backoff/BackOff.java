/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Chris Campo
 * With additions and modifications by Currencycloud, 2018
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.currencycloud.client.backoff;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.exception.ApiException;
import com.currencycloud.client.exception.TooManyRequestsException;
import com.currencycloud.client.model.ResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Executes a task with an exponential backoff retry policy.
 *
 * Upon failure (i.e. an exception is thrown from the task) it will wait (backoff) a random period of time, increasing
 * exponetially on each retry until success or maxAttempts is reached. If the task is successful, it returns immediately.
 * The task's return value (if any) is stored in the data field of the result object {@link BackOffResult}.
 *
 * {@link BackOff} objects are created via the Builder class, configuring the relevant fields and supplying the task,
 * which can then be executed with the BackOff.<T>builder().execute() method.
 *
 * @param <T> The return type of the task executed with exponential backoff.
 */
public class BackOff<T> {

    protected static final int DEFAULT_MAX_ATTEMPTS = 7;
    protected static final int DEFAULT_WAIT_CAP_MILLIS = 90000;
    protected static final int DEFAULT_WAIT_BASE_MILLIS = 125;

    private final int cap;
    private final int base;
    private final int maxAttempts;
    private final Class exceptionType;
    private final Callable<T> task;
    private final Consumer<Exception> exceptionHandler;
    private final Predicate<T> forceRetry;

    private static final Logger log = LoggerFactory.getLogger(CurrencyCloudClient.class);

    private BackOff(final int cap, final int base, final int maxAttempts, final Class<? extends ApiException> exceptionType,
                   final Callable<T> task, final Consumer<Exception> exceptionHandler, final Predicate<T> forceRetry) {
        this.cap = cap;
        this.base = base;
        this.maxAttempts = maxAttempts;
        this.exceptionType = exceptionType;
        this.task = Objects.requireNonNull(task);
        this.exceptionHandler = Objects.requireNonNull(exceptionHandler);
        this.forceRetry = Objects.requireNonNull(forceRetry);
    }

    public static <T> Builder<T> builder() {
        return new Builder<>();
    }

    /**
     * Executes the given task configured via the builder.
     */
    public BackOffResult<T> execute() {
        return execute(attempt -> attempt < maxAttempts, 0);
    }

    /**
     * Executes the given task.
     */
    protected BackOffResult<T> execute(final Predicate<Integer> predicate, final int attempt) {
        int curAttempt = attempt;
        do {
            try {
                log.trace("curAttempt: " + curAttempt);
                final T result = task.call();
                if (forceRetry.test(result)) {
                    log.trace("throwing new Retry exception");
                    throw new ApiException(new ResponseException());
                }
                log.trace("return SUCCESSFUL");
                return new BackOffResult<>(result, BackOffResultStatus.SUCCESSFUL);
            } catch (final Exception e) {
                log.trace("exceptionType: " + exceptionType.getSimpleName() + " | exception thrown: " + e.getClass().getSimpleName());
                if (e.getClass() == exceptionType) {
                    exceptionHandler.accept(e);
                    doWait(curAttempt);
                } else {
                    throw new RuntimeException(e);
                }
            }
        } while (predicate.test(curAttempt++));
        log.trace("return EXCEEDED_MAX_ATTEMPTS");
        return new BackOffResult<>(BackOffResultStatus.EXCEEDED_MAX_ATTEMPTS);
    }

    private void doWait(final int attempt) {
        try {
            final int waitTime = getWaitTimeWithJitter(cap, base, attempt);
            log.trace("waitTime: " + String.format("%.3f", (double) waitTime/1000)  + " sec");
            Thread.sleep(waitTime);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    protected static int getWaitTime(final int cap, final int base, final int n) {
        final int expWait = ((int) Math.pow(2, n)) * base;
        return expWait <= 0 ? cap : Math.min(cap, expWait);
    }

    protected static int getWaitTimeWithJitter(final int cap, final int base, final int n) {
        return ThreadLocalRandom.current().nextInt(0, getWaitTime(cap, base, n));
    }

    public static final class Builder<T> {
        private int cap = DEFAULT_WAIT_CAP_MILLIS;
        private int base = DEFAULT_WAIT_BASE_MILLIS;
        private int maxAttempts = DEFAULT_MAX_ATTEMPTS;
        private Class exceptionType = TooManyRequestsException.class;
        private Callable<T> task = () -> null;
        private Consumer<Exception> exceptionHandler = e -> { };
        private Predicate<T> retryIf = t -> false;

        private Builder() {
        }

        @SuppressWarnings("unchecked")
        public BackOff<T> build() {
            return new BackOff<>(cap, base, maxAttempts, this.exceptionType, task, exceptionHandler, retryIf);
        }

        /**
         * Executes the {@link BackOff} produced by this builder.
         *
         * @return A {@link BackOffResult} containing the return data of the task and the status.
         */
        public BackOffResult<T> execute() {
            return build().execute();
        }

        /**
         * The max wait time, in milliseconds, before retrying. Value must be larger than 0.
         * Default is 90 secs
         */
        public Builder<T> withCap(final int cap) {
            this.cap = cap;
            assert cap > 0;
            log.debug("withCap: " + cap);
            return this;
        }

        /**
         * The base wait time, in milliseconds, before retrying. Value must be larger than 0.
         * Default is 125 msecs
         */
        public Builder<T> withBase(final int base) {
            this.base = base;
            assert base > 0;
            log.debug("withBase: " + base);
            return this;
        }

        /**
         * The maximum number of retry attempts. Value must be larger than 0.
         * Default is 7 attempts
         */
        public Builder<T> withMaxAttempts(final int maxAttempts) {
            this.maxAttempts = maxAttempts;
            assert maxAttempts > 0;
            log.debug("withMaxAttempts: " + maxAttempts);
            return this;
        }

        /**
         * The exception type to retry. All others are executed normally without retrying.
         * Default is TooManyRequestsException
         */
        public Builder<T> withExceptionType(final Class<? extends ApiException> exceptionType) {
            this.exceptionType = exceptionType;
            log.debug("withExceptionType: " + exceptionType.getSimpleName());
            return this;
        }

        /**
         * The task to perform, which will be retried with backoff if it encounters exceptionType exception.
         */
        public Builder<T> withTask(final Callable<T> task) {
            this.task = Objects.requireNonNull(task);
            log.debug("withTask");
            return this;
        }

        /**
         * Additional exception handling logic. The task will still be retried after the exception is handled, but this
         * allows for things like logging the exception, updating application state, etc.
         */
        public Builder<T> withExceptionHandler(final Consumer<Exception> exceptionHandler) {
            this.exceptionHandler = Objects.requireNonNull(exceptionHandler);
            log.debug("withExceptionHandler");
            return this;
        }

        /**
         * Used mainly for testing (hence package-private) to force retry of the main task even if it did not throw
         * an exception.
         */
        Builder<T> retryIf(final Predicate<T> retryIf) {
            this.retryIf = Objects.requireNonNull(retryIf);
            log.debug("forceRetry");
            return this;
        }
    }
}
