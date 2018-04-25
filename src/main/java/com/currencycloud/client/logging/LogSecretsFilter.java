package com.currencycloud.client.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

import java.util.Arrays;
import java.util.Collection;

/**
 * This can be used to help avoid logging sensitive data in application logs, if you are using logback.
 * Add the filter to your appender configuration in logback.xml:
 *
 * <pre>
 * &lt;appender ...>
 *    &lt;filter class="com.currencycloud.client.logging.LogSecretsFilter" />
 * &lt;/appender>
 * </pre>
 *
 * Note that this depends on Logback, so the project must use Logback as the slf4j logging provider.
 */
public class LogSecretsFilter extends Filter<ILoggingEvent> {

    private static final Collection<String> forbiddens = Arrays.asList("X-Auth-Token", "authToken", "auth_token", "api_key", "apiKey");

    @Override
    public FilterReply decide(ILoggingEvent event) {
        String formattedMessage = event.getFormattedMessage();
        for (String forbidden : forbiddens) {
            if (formattedMessage.contains(forbidden)) {
                return FilterReply.DENY;
            }
        }
        return FilterReply.NEUTRAL;
    }
}
