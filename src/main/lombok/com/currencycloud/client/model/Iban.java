package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import net.minidev.json.JSONObject;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Iban implements Entity {

    private String id;
    private String ibanCode;
    private String accountId;
    private String currency;
    private String accountHolderName;
    private String bankInstitutionName;
    private String bankInstitutionAddress;
    private String bankInstitutionCountry;
    private String bicSwift;
    private Date createdAt;
    private Date updatedAt;

    protected Iban() { }

    /**
     * @deprecated as of 1.2.3; IBANs are automatically created upon account creation
     * */
    @Deprecated
    private Iban(String currency) {
        this.currency = currency;
    }

    public static Iban create() {
        return new Iban();
    }

    /**
     * @deprecated as of 1.2.3; IBANs are automatically created upon account creation
     * */
    @Deprecated
    public static Iban create(String currency) {
        return new Iban(currency);
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("ibanCode", ibanCode)
                .appendField("accountId", accountId)
                .appendField("currency", currency)
                .appendField("accountHolderName", accountHolderName)
                .appendField("bankInstitutionName", bankInstitutionName)
                .appendField("bankInstitutionAddress", bankInstitutionAddress)
                .appendField("bankInstitutionCountry", bankInstitutionCountry)
                .appendField("bicSwift", bicSwift)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
    }
}
