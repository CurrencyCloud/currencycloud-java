package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FundingTransactionSender {
  private String senderId;
  private String senderAddress;
  private String senderCountry;
  private String senderName;
  private String senderBic;
  private String senderIban;
  private String senderAccountNumber;
  private String senderRoutingCode;

  public String getSenderId() {
    return senderId;
  }

  public void setSenderId(String senderId) {
    this.senderId = senderId;
  }

  public String getSenderAddress() {
    return senderAddress;
  }

  public void setSenderAddress(String senderAddress) {
    this.senderAddress = senderAddress;
  }

  public String getSenderCountry() {
    return senderCountry;
  }

  public void setSenderCountry(String senderCountry) {
    this.senderCountry = senderCountry;
  }

  public String getSenderName() {
    return senderName;
  }

  public void setSenderName(String senderName) {
    this.senderName = senderName;
  }

  public String getSenderBic() {
    return senderBic;
  }

  public void setSenderBic(String senderBic) {
    this.senderBic = senderBic;
  }

  public String getSenderIban() {
    return senderIban;
  }

  public void setSenderIban(String senderIban) {
    this.senderIban = senderIban;
  }

  public String getSenderAccountNumber() {
    return senderAccountNumber;
  }

  public void setSenderAccountNumber(String senderAccountNumber) {
    this.senderAccountNumber = senderAccountNumber;
  }

  public String getSenderRoutingCode() {
    return senderRoutingCode;
  }

  public void setSenderRoutingCode(String senderRoutingCode) {
    this.senderRoutingCode = senderRoutingCode;
  }

  @Override
  public String toString() {
    final ObjectMapper objectMapper =
        new ObjectMapper()
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

    Map<String, Object> map = new HashMap<>();
    map.put("senderId", senderId);
    map.put("senderAddress", senderAddress);
    map.put("senderCountry", senderCountry);
    map.put("senderName", senderName);
    map.put("senderBic", senderBic);
    map.put("senderIban", senderIban);
    map.put("senderAccountNumber", senderAccountNumber);
    map.put("senderRoutingCode", senderRoutingCode);

    try {
      return objectMapper.writeValueAsString(map);
    } catch (JsonProcessingException e) {
      return String.format("{\"error\": \"%s\"}", e.getMessage());
    }
  }
}
