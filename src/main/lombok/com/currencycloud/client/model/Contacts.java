package com.currencycloud.client.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@EqualsAndHashCode(callSuper = false)
@Getter
public class Contacts extends PaginatedData {

    private List<Contact> contacts;

    @Override
    public String toString() {
        return String.format("{\"contacts\":%s, \"pagination\":%s}", contacts, pagination);
    }

}
