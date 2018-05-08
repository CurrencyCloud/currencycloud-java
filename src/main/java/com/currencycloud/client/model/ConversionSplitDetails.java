package com.currencycloud.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import net.minidev.json.JSONObject;

import java.util.Date;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversionSplitDetails implements Entity {

    private String id;
    private String dealRef;
    private String sellCcy;
    private String buyCcy;
    private String clientBuyAmt;
    private String clientSellAmt;
    private Date settlesAt;
    private Date deliveryAt;
    private String status;

    protected ConversionSplitDetails() { }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDealRef() {
        return dealRef;
    }

    public void setDealRef(String dealRef) {
        this.dealRef = dealRef;
    }

    public String getSellCcy() {
        return sellCcy;
    }

    public void setSellCcy(String sellCcy) {
        this.sellCcy = sellCcy;
    }

    public String getBuyCcy() {
        return buyCcy;
    }

    public void setBuyCcy(String buyCcy) {
        this.buyCcy = buyCcy;
    }

    public String getClientBuyAmt() {
        return clientBuyAmt;
    }

    public void setClientBuyAmt(String clientBuyAmt) {
        this.clientBuyAmt = clientBuyAmt;
    }

    public String getClientSellAmt() {
        return clientSellAmt;
    }

    public void setClientSellAmt(String clientSellAmt) {
        this.clientSellAmt = clientSellAmt;
    }

    public Date getSettlesAt() {
        return settlesAt;
    }

    public void setSettlesAt(Date settlesAt) {
        this.settlesAt = settlesAt;
    }

    public Date getDeliveryAt() {
        return deliveryAt;
    }

    public void setDeliveryAt(Date deliveryAt) {
        this.deliveryAt = deliveryAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new JSONObject()
                .appendField("id", id)
                .appendField("dealRef", dealRef)
                .appendField("buyCcy", buyCcy)
                .appendField("sellCcy", sellCcy)
                .appendField("clientBuyAmt", clientBuyAmt)
                .appendField("clientSellAmt", clientSellAmt)
                .appendField("settlesAt", settlesAt)
                .appendField("deliveryAt", deliveryAt)
                .appendField("status", status)
                .toString();
    }
}
