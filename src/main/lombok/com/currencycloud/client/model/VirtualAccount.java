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
public class VirtualAccount implements Entity {

    private String id;
    private String accountId;
    private String virtualAccountNumber;
    private String accountHolderName;
    private String bankInstitutionName;
    private String bankInstitutionAddress;
    private String bankInstitutionCountry;
    private String routingCode;
    private Date createdAt;
    private Date updatedAt;

    protected VirtualAccount() { }

    public static VirtualAccount create() {
        return new VirtualAccount();
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("accountId", accountId)
                .appendField("virtualAccountNumber", virtualAccountNumber)
                .appendField("accountHolderName", accountHolderName)
                .appendField("bankInstitutionName", bankInstitutionName)
                .appendField("bankInstitutionAddress", bankInstitutionAddress)
                .appendField("bankInstitutionCountry", bankInstitutionCountry)
                .appendField("routingCode", routingCode)
                .appendField("createdAt", createdAt)
                .appendField("updatedAt", updatedAt)
                .toString();
        }
}
