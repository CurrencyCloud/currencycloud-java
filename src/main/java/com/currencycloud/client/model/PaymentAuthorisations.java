package com.currencycloud.client.model;

import java.util.List;

public class PaymentAuthorisations extends PaginatedData {

	private List<PaymentAuthorisation> authorisations;

    public List<PaymentAuthorisation> getAuthorisations() {
        return authorisations;
    }

    @Override
    public String toString() {
        return String.format("{\"authorisations\":%s, \"pagination\":%s}", authorisations, pagination);
    }
}
