package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.minidev.json.JSONObject;

import javax.annotation.Nullable;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Contact.class)
@Data
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
