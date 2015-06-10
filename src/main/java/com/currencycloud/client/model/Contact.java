package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import javax.annotation.Nullable;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Contact.class)
public class Contact implements Entity {

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

    protected Contact() { }

    private Contact(
            String accountId,
            String firstName,
            String lastName,
            String emailAddress,
            String phoneNumber,
            @Nullable String yourReference,
            @Nullable String mobilePhoneNumber,
            @Nullable String loginId,
            @Nullable String status,
            @Nullable String locale,
            @Nullable String timezone,
            @Nullable Date dateOfBirth
    ) {
        this.accountId = accountId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.yourReference = yourReference;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.loginId = loginId;
        this.status = status;
        this.locale = locale;
        this.timezone = timezone;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Creates a contact as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createContact(Contact)} method.
     */
    public static Contact create(
            String accountId,
            String firstName,
            String lastName,
            String emailAddress,
            String phoneNumber,
            @Nullable String yourReference,
            @Nullable String mobilePhoneNumber,
            @Nullable String loginId,
            @Nullable String status,
            @Nullable String locale,
            @Nullable String timezone,
            @Nullable Date dateOfBirth
    ) {
        return new Contact(
                accountId,
                firstName,
                lastName,
                emailAddress,
                phoneNumber,
                yourReference,
                mobilePhoneNumber,
                loginId,
                status,
                locale,
                timezone,
                dateOfBirth
        );
    }

    /**
     * Creates a contact as expected by the
     * {@link com.currencycloud.client.CurrencyCloudClient#createContact(Contact)} method,
     * using only required parameters.
     */
    public static Contact create(
            String accountId,
            String firstName,
            String lastName,
            String emailAddress,
            String phoneNumber
    ) {
        return new Contact(
                accountId, firstName, lastName, emailAddress, phoneNumber, null, null, null, null, null, null, null
        );
    }

    public static Contact create() {
        return new Contact();
    }

    public String getId() {
        return id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getYourReference() {
        return yourReference;
    }

    public void setYourReference(String yourReference) {
        this.yourReference = yourReference;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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
