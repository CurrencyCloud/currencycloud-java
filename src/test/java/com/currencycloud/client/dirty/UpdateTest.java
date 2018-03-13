package com.currencycloud.client.dirty;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.BetamaxTestSupport;
import com.currencycloud.client.CurrencyCloudClient;
import com.currencycloud.client.model.Beneficiaries;
import com.currencycloud.client.model.Beneficiary;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.junit.MatcherAssert.assertThat;

public class UpdateTest extends BetamaxTestSupport {

    @Test
    @Betamax(tape = "only_updates_changed_records", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void onlyUpdatesChangedRecords() throws Exception {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiary beneficiary = client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");

        assertThat(beneficiary.getBankAccountHolderName(), equalTo("Test User"));
        assertThat(beneficiary.getEmail(), is(nullValue()));
        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setBankAccountHolderName("Test User 2");
        beneficiary.setEmail("development@currencycloud.com");
        beneficiary.setCurrency("GBP"); // doesn't change

        // The following will fail (with a HTTP 403 from Betamax and a message "tape is read only") if the request body
        // doesn't match the one in the yaml file.
        client.updateBeneficiary(beneficiary);
    }

    @Test
    @Betamax(tape = "does_nothing_if_nothing_has_changed", match = {MatchRule.method, MatchRule.uri})
    public void shouldDoNothingIfNothingHasChanged() throws Exception {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiary beneficiary = client.retrieveBeneficiary("081596c9-02de-483e-9f2a-4cf55dcdf98c");

        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setCurrency("GBP"); // doesn't change

        client.updateBeneficiary(beneficiary); // No matching request in the yaml
    }

    @Test
    @Betamax(tape = "does_nothing_if_nothing_has_changed_from_collection", match = {MatchRule.method, MatchRule.uri})
    public void shouldDoNothingIfNothingHasChangedFromCollection() throws Exception {
        CurrencyCloudClient client = prepareTestClient(null, null, "e5070d4a16c5ffe4ed9fb268a2a716be");

        Beneficiaries beneficiaries = client.findBeneficiaries(null, null);

        Beneficiary beneficiary = beneficiaries.getBeneficiaries().get(0);

        assertThat(beneficiary.getCurrency(), equalTo("GBP"));

        beneficiary.setCurrency("GBP"); // doesn't change

        client.updateBeneficiary(beneficiary); // No matching request in the yaml
    }
}
