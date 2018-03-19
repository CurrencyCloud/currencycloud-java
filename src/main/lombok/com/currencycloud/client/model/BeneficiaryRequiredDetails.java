package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class BeneficiaryRequiredDetails {

    private List<Map<String, String>> details;

    /*
    @JsonNaming(PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy.class)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class Details {

        private String paymentType;
        private String acctNumber;
        private String aba;
        private String beneficiaryAddress;
        private String beneficiaryCity;
        private String beneficiaryCountry;
        private String beneficiaryPostcode;
        private String beneficiaryStateOrProvince;
        private String beneficiaryCompanyName;
        private String beneficiaryEntityType;

        public String getPaymentType() {
            return paymentType;
        }

        public String getAcctNumber() {
            return acctNumber;
        }

        public String getAba() {
            return aba;
        }

        public String getBeneficiaryAddress() {
            return beneficiaryAddress;
        }

        public String getBeneficiaryCity() {
            return beneficiaryCity;
        }

        public String getBeneficiaryCountry() {
            return beneficiaryCountry;
        }

        public String getBeneficiaryPostcode() {
            return beneficiaryPostcode;
        }

        public String getBeneficiaryStateOrProvince() {
            return beneficiaryStateOrProvince;
        }

        public String getBeneficiaryCompanyName() {
            return beneficiaryCompanyName;
        }

        public String getBeneficiaryEntityType() {
            return beneficiaryEntityType;
        }
    }
*/
}
