package com.currencycloud.client;

import com.currencycloud.client.model.Contact;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.testng.annotations.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class DeserialisationTest extends JsonTestSupport {

    @Test
    public void testContact() throws Exception {
        Contact contact = readJson(Contact.class);
        assertThat(contact.getLoginId(), equalTo("john.smith"));
        assertThat(contact.getId(), equalTo("543477161-91de-012f-e284-1e0030c7f352"));
        assertThat(contact.getYourReference(), equalTo("ACME12345"));
        assertThat(contact.getFirstName(), equalTo("John"));
        assertThat(contact.getLastName(), equalTo("Smith"));
        assertThat(contact.getAccountId(), equalTo("87077161-91de-012f-e284-1e0030c7f352"));
        assertThat(contact.getAccountName(), equalTo("Company PLC"));
        assertThat(contact.getStatus(), equalTo("enabled"));
        assertThat(contact.getPhoneNumber(), equalTo("06554 87845"));
        assertThat(contact.getMobilePhoneNumber(), equalTo("07564 534 54"));
        assertThat(contact.getLocale(), equalTo("en-US"));
        assertThat(contact.getTimezone(), equalTo("Europe/London"));
        assertThat(contact.getEmailAddress(), equalTo("john.smith@company.com"));
        assertThat(contact.getDateOfBirth(), equalTo(parseDate("1980-01-22")));
        assertThat(contact.getCreatedAt(), equalTo(parseDateTime("2014-01-12T00:00:00+00:00")));
        assertThat(contact.getUpdatedAt(), equalTo(parseDateTime("2014-01-12T00:00:00+00:00")));
    }

    private <T> T readJson(Class<T> type) throws java.io.IOException {
        URL jsonUrl = DeserialisationTest.class.getResource(String.format("/json/%s.json", type.getSimpleName()));
        return new ObjectMapper().readValue(jsonUrl, type);
    }
}
