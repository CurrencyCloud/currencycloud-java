package com.currencycloud.client.model;

import java.util.List;

public class Settlements extends PaginatedData {

    private List<Conversion> settlements;

    public List<Conversion> getConversions() {
        return settlements;
    }
}
