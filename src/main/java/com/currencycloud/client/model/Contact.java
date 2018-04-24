package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import javax.annotation.Nullable;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Contact.class)
public class Contact implements Entity {

    private String loginId;
    private String id;
    private String yourReference;
    private String firstName;
    private String lastName;
    private String accountId;
    private String accountName;
    private String status;
    private String phoneNumber;
    private String mobilePhoneNumber;
    private String locale;
    private String timezone;
    private String emailAddress;
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;

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

    public static Contact create() {
        return new Contact();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
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

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("loginId", loginId)
                .appendField("id", id)
                .appendField("yourReference", yourReference)
                .appendField("firstName", firstName)
                .appendField("lastName", lastName)
                .appendField("accountId", accountId)
                .appendField("accountName", accountName)
                .appendField("status", status)
                .appendField("phoneNumber", phoneNumber)
                .appendField("mobilePhoneNumber", mobilePhoneNumber)
                .appendField("locale", locale)
                .appendField("timezone", timezone)
                .appendField("emailAddress", emailAddress)
                .appendField("dateOfBirth", dateOfBirth)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
        }
}
