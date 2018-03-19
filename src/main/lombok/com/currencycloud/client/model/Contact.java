package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import javax.annotation.Nullable;
import java.util.Date;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Contact.class)
@Data
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

}
