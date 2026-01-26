package com.currencycloud.client.model;

import com.currencycloud.client.Utils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentTrackingInfo {

    private String uetr;
    private TransactionStatus transactionStatus;
    private Date initiationTime;
    private Date completionTime;
    private Date lastUpdateTime;
    private List<PaymentEvent> paymentEvents;

    public String getUetr() {
        return uetr;
    }

    public void setUetr(String uetr) {
        this.uetr = uetr;
    }

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public Date getInitiationTime() {
        return initiationTime;
    }

    public void setInitiationTime(Date initiationTime) {
        this.initiationTime = initiationTime;
    }

    public Date getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(Date completionTime) {
        this.completionTime = completionTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public List<PaymentEvent> getPaymentEvents() {
        return paymentEvents;
    }

    public void setPaymentEvents(List<PaymentEvent> paymentEvents) {
        this.paymentEvents = paymentEvents;
    }

    @Override
    public String toString() {
        final ObjectMapper objectMapper = new ObjectMapper()
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                .setDateFormat(new SimpleDateFormat(Utils.dateFormat));

        Map<String, Object> map = new HashMap<>();
        map.put("uetr", uetr);
        map.put("transactionStatus", transactionStatus);
        map.put("initiationTime", initiationTime);
        map.put("completionTime", completionTime);
        map.put("lastUpdateTime", lastUpdateTime);
        map.put("paymentEvents", paymentEvents);

        try {
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            return String.format("{\"error\": \"%s\"}", e.getMessage());
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class TransactionStatus {
        private String status;
        private String reason;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class PaymentEvent {

        private String trackerEventType;
        private Boolean valid;
        private TransactionStatus transactionStatus;
        private Date fundsAvailable;
        private String forwardedToAgent;
        private String from;
        private String to;
        private String originator;
        private SerialParties serialParties;
        private Date senderAcknowledgementReceipt;
        private Amount instructedAmount;
        private Amount confirmedAmount;
        private Amount interbankSettlementAmount;
        private Date interbankSettlementDate;
        private List<Amount> chargeAmount;
        private String chargeType;
        private ForeignExchangeDetails foreignExchangeDetails;
        private Date lastUpdateTime;

        public String getTrackerEventType() {
            return trackerEventType;
        }

        public void setTracker_event_type(String trackerEventType) {
            this.trackerEventType = trackerEventType;
        }

        public Boolean getValid() {
            return valid;
        }

        public void setValid(Boolean valid) {
            this.valid = valid;
        }

        public TransactionStatus getTransactionStatus() {
            return transactionStatus;
        }

        public void setTransactionStatus(TransactionStatus transactionStatus) {
            this.transactionStatus = transactionStatus;
        }

        public Date getFundsAvailable() {
            return fundsAvailable;
        }

        public void setFundsAvailable(Date fundsAvailable) {
            this.fundsAvailable = fundsAvailable;
        }

        public String getForwardedToAgent() {
            return forwardedToAgent;
        }

        public void setForwardedToAgent(String forwardedToAgent) {
            this.forwardedToAgent = forwardedToAgent;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getOriginator() {
            return originator;
        }

        public void setOriginator(String originator) {
            this.originator = originator;
        }

        public SerialParties getSerialParties() {
            return serialParties;
        }

        public void setSerialParties(SerialParties serialParties) {
            this.serialParties = serialParties;
        }

        public Date getSenderAcknowledgementReceipt() {
            return senderAcknowledgementReceipt;
        }

        public void setSenderAcknowledgementReceipt(Date senderAcknowledgementReceipt) {
            this.senderAcknowledgementReceipt = senderAcknowledgementReceipt;
        }

        public Amount getInstructedAmount() {
            return instructedAmount;
        }

        public void setInstructedAmount(Amount instructedAmount) {
            this.instructedAmount = instructedAmount;
        }

        public Amount getConfirmedAmount() {
            return confirmedAmount;
        }

        public void setConfirmedAmount(Amount confirmedAmount) {
            this.confirmedAmount = confirmedAmount;
        }

        public Amount getInterbankSettlementAmount() {
            return interbankSettlementAmount;
        }

        public void setInterbankSettlementAmount(Amount interbankSettlementAmount) {
            this.interbankSettlementAmount = interbankSettlementAmount;
        }

        public Date getInterbankSettlementDate() {
            return interbankSettlementDate;
        }

        public void setInterbankSettlementDate(Date interbankSettlementDate) {
            this.interbankSettlementDate = interbankSettlementDate;
        }

        public List<Amount> getChargeAmount() {
            return chargeAmount;
        }

        public void setChargeAmount(List<Amount> chargeAmount) {
            this.chargeAmount = chargeAmount;
        }

        public String getChargeType() {
            return chargeType;
        }

        public void setChargeType(String chargeType) {
            this.chargeType = chargeType;
        }

        public ForeignExchangeDetails getForeignExchangeDetails() {
            return foreignExchangeDetails;
        }

        public void setForeignExchangeDetails(ForeignExchangeDetails foreignExchangeDetails) {
            this.foreignExchangeDetails = foreignExchangeDetails;
        }

        public Date getLastUpdateTime() {
            return lastUpdateTime;
        }

        public void setLastUpdateTime(Date lastUpdateTime) {
            this.lastUpdateTime = lastUpdateTime;
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class SerialParties {
        private String debtor;
        private String debtorAgent;
        private String intermediaryAgent1;
        private String instructingReimbursementAgent;
        private String creditorAgent;
        private String creditor;

        public String getDebtor() {
            return debtor;
        }

        public void setDebtor(String debtor) {
            this.debtor = debtor;
        }

        public String getDebtorAgent() {
            return debtorAgent;
        }

        public void setDebtorAgent(String debtorAgent) {
            this.debtorAgent = debtorAgent;
        }

        public String getIntermediaryAgent1() {
            return intermediaryAgent1;
        }

        public void setIntermediaryAgent1(String intermediaryAgent1) {
            this.intermediaryAgent1 = intermediaryAgent1;
        }

        public String getInstructingReimbursementAgent() {
            return instructingReimbursementAgent;
        }

        public void setInstructingReimbursementAgent(String instructingReimbursementAgent) {
            this.instructingReimbursementAgent = instructingReimbursementAgent;
        }

        public String getCreditorAgent() {
            return creditorAgent;
        }

        public void setCreditorAgent(String creditorAgent) {
            this.creditorAgent = creditorAgent;
        }

        public String getCreditor() {
            return creditor;
        }

        public void setCreditor(String creditor) {
            this.creditor = creditor;
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class Amount {
        private String currency;
        private BigDecimal amount;

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public BigDecimal getAmount() {
            return amount;
        }

        public void setAmount(BigDecimal amount) {
            this.amount = amount;
        }
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static final class ForeignExchangeDetails {
        private String sourceCurrency;
        private String targetCurrency;
        private BigDecimal rate;

        public String getSourceCurrency() {
            return sourceCurrency;
        }

        public void setSourceCurrency(String sourceCurrency) {
            this.sourceCurrency = sourceCurrency;
        }

        public String getTargetCurrency() {
            return targetCurrency;
        }

        public void setTargetCurrency(String targetCurrency) {
            this.targetCurrency = targetCurrency;
        }

        public BigDecimal getRate() {
            return rate;
        }

        public void setRate(BigDecimal rate) {
            this.rate = rate;
        }
    }
}
