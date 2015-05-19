package com.currencycloud.client.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.util.Date;

// todo: this is entirely untested
@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
public class Contact {

    private String loginId;  // john.smith

    private String id;  // 543477161-91de-012f-e284-1e0030c7f352

    private String yourReference;  // ACME12345

    private String firstName;  // John

    private String lastName;  // Smith

    private String accountId;  // 87077161-91de-012f-e284-1e0030c7f352

    private String accountName;  // Company PLC

    private String status;  // enabled

    private String phoneNumber;  // 06554 87845

    private String mobilePhoneNumber;  // 07564 534 54

    private String locale;  // en-US

    private String timezone;  // Europe/London

    private String emailAddress;  // john.smith@company.com

    private Date dateOfBirth;  // 1980-01-22

    private Date createdAt;  // 2014-01-12T00:00:00+00:00

    private Date updatedAt;  // 2014-01-12T00:00:00+00:00

    public String getLoginId() {
        return loginId;
    }

    public String getId() {
        return id;
    }

    public String getYourReference() {
        return yourReference;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAccountId() {
        return accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getStatus() {
        return status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public String getLocale() {
        return locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return String.format("Contact{loginId='%s', id='%s', yourReference='%s', firstName='%s', lastName='%s', accountId='%s', accountName='%s', status='%s', phoneNumber='%s', mobilePhoneNumber='%s', locale='%s', timezone='%s', emailAddress='%s', dateOfBirth=%s, createdAt=%s, updatedAt=%s}",
                loginId, id, yourReference, firstName, lastName, accountId, accountName, status, phoneNumber, mobilePhoneNumber, locale, timezone, emailAddress, dateOfBirth, createdAt, updatedAt);
    }
}
