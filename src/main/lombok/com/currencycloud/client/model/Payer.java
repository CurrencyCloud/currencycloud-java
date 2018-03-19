package com.currencycloud.client.model;

import com.currencycloud.client.dirty.DirtyWatcherDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(converter = DirtyWatcherDeserializer.Payer.class)
@Data
public class Payer implements Entity {

    private String id;
    private String legalEntityType;
    private String companyName;
    private String firstName;
    private String lastName;
    private List<String> address = new ArrayList<>();
    private String city;
    private String stateOrProvince;
    private String country;
    private String identificationType;
    private String identificationValue;
    private String postcode;
    private Date dateOfBirth;
    private Date createdAt;
    private Date updatedAt;

    protected Payer() { }

    private Payer(String entityType,
                  String companyName,
                  String firstName,
                  String lastName,
                  List<String> address,
                  String city,
                  String country,
                  String postcode,
                  String stateOrProvince,
                  Date dateOfBirth,
                  String identificationType,
                  String identificationValue
    ) {
        this.legalEntityType = entityType;
        this.companyName = companyName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.city = city;
        this.address = address;
        this.postcode = postcode;
        this.stateOrProvince = stateOrProvince;
        this.dateOfBirth = dateOfBirth;
        this.identificationType = identificationType;
        this.identificationValue = identificationValue;
    }

    public static Payer create(String entityType,
                               String companyName,
                               String firstName,
                               String lastName,
                               List<String> address,
                               String city,
                               String country,
                               String postcode,
                               String stateOrProvince,
                               Date dateOfBirth,
                               String identificationType,
                               String identificationValue
    ) {
        return new Payer(entityType,
                         companyName,
                         firstName,
                         lastName,
                         address,
                         city,
                         country,
                         postcode,
                         stateOrProvince,
                         dateOfBirth,
                         identificationType,
                         identificationValue
        );
    }

    /**
     * Create payer with minimum set of required attributes
     * @param entityType
     * @param companyName
     * @param firstName
     * @param lastName
     * @param address
     * @param city
     * @param country
     * @param dateOfBirth
     * @return
     */
    public static Payer create(String entityType,
                               String companyName,
                               String firstName,
                               String lastName,
                               List<String> address,
                               String city,
                               String country,
                               Date dateOfBirth
    ) {
        return new Payer(entityType,
                companyName,
                firstName,
                lastName,
                address,
                city,
                country,
                null,
                null,
                dateOfBirth,
                null,
                null
        );
    }

    public static Payer create() {
        return new Payer();
    }

}