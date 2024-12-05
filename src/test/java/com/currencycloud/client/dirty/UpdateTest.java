package com.currencycloud.client.dirty;

import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.TestSupport;
import com.currencycloud.client.model.Beneficiaries;
import com.currencycloud.client.model.Beneficiary;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class UpdateTest extends TestSupport {

    @Test
    public void onlyUpdatesChangedRecords() {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiary beneficiary = client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");

        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User"));
        assertThat(beneficiary.getEmail(), is(nullValue()));
        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setBankAccountHolderName("Test User 2");
        beneficiary.setEmail("development@currencycloud.com");
        beneficiary.setCurrency("GBP"); // doesn't change

        // The following will fail if the request body
        // doesn't match the one in the json file.
        client.updateBeneficiary(beneficiary);
    }

    @Test
    public void shouldDoNothingIfNothingHasChanged() {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiary beneficiary = client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");

        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setCurrency("GBP"); // doesn't change

        client.updateBeneficiary(beneficiary); // No matching request in the json
    }

    @Test
    public void shouldDoNothingIfNothingHasChangedFromCollection() {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiaries beneficiaries = client.findBeneficiaries(null, null);

        Beneficiary beneficiary = beneficiaries.getBeneficiaries().get(0);

        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setCurrency("GBP"); // doesn't change

        client.updateBeneficiary(beneficiary); // No matching request in the json
    }
}
