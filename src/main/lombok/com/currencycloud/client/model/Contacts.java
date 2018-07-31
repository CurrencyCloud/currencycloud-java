package com.currencycloud.client.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@EqualsAndHashCode(callSuper = false)
@Getter
public class Contacts extends PaginatedData {

    private List<Contact> contacts;

    @Override
    public String toString() {
        return String.format("{\"contacts\":%s, \"pagination\":%s}", contacts, pagination);
    }

}
