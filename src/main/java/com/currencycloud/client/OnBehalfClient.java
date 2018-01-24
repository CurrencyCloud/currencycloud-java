package com.currencycloud.client;

import com.currencycloud.client.exception.CurrencyCloudException;
import com.currencycloud.client.model.Account;
import com.currencycloud.client.model.Beneficiaries;
import com.currencycloud.client.model.Beneficiary;
import com.currencycloud.client.model.Conversion;
import com.currencycloud.client.model.Conversions;
import com.currencycloud.client.model.DetailedRate;
import com.currencycloud.client.model.Pagination;
import com.currencycloud.client.model.Payer;
import com.currencycloud.client.model.Payment;
import com.currencycloud.client.model.Payments;
import com.currencycloud.client.model.Rates;
import com.currencycloud.client.model.Settlement;
import com.currencycloud.client.model.Settlements;
import com.currencycloud.client.model.Transaction;
import com.currencycloud.client.model.Transactions;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.regex.Pattern;

public class OnBehalfClient implements OnBehalfFunctions {
    private static final Pattern UUID = Pattern.compile(
            "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}",
            Pattern.CASE_INSENSITIVE
    );

    private CurrencyCloudClient client;
    private String onBehalfOf;

    public OnBehalfClient(String onBehalfOf) {
        if (!UUID.matcher(onBehalfOf).matches()) {
            throw new IllegalArgumentException("Contact id for onBehalfOf is not a UUID");
        }
        this.onBehalfOf = onBehalfOf;
    }

    public CurrencyCloudClient getClient() {
        return client;
    }

    public String getOnBehalfOf() {
        return onBehalfOf;
    }

    @Override
    public Account retrieveAccount(String accountId) throws CurrencyCloudException {
        return client.retrieveAccount(accountId, getOnBehalfOf());
    }

    @Override
    public Beneficiary validateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return client.validateBeneficiary(beneficiary, getOnBehalfOf());
    }

    @Override
    public Beneficiary createBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return client.createBeneficiary(beneficiary, getOnBehalfOf());
    }

    @Override
    public Beneficiary retrieveBeneficiary(String id) throws CurrencyCloudException {
        return client.retrieveBeneficiary(id, getOnBehalfOf());
    }

    @Override
    public Beneficiary updateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException {
        return client.updateBeneficiary(beneficiary, getOnBehalfOf());
    }

    @Override
    public Beneficiaries findBeneficiaries(@Nullable Beneficiary example, @Nullable Pagination pagination) throws CurrencyCloudException {
        return client.findBeneficiaries(example, pagination, getOnBehalfOf());
    }

    @Override
    public Beneficiary firstBeneficiary(@Nullable Beneficiary beneficiary) throws CurrencyCloudException {
        return client.findBeneficiaries(beneficiary, Pagination.first(), getOnBehalfOf()).getBeneficiaries().iterator().next();
    }

    @Override
    public Beneficiary deleteBeneficiary(String id) throws CurrencyCloudException {
        return client.deleteBeneficiary(id, getOnBehalfOf());
    }

    @Override
    public Conversion createConversion(Conversion conversion, BigDecimal amount, String reason, Boolean termAgreement) throws CurrencyCloudException {
        return client.createConversion(conversion, amount, reason, termAgreement, getOnBehalfOf());
    }

    @Override
    public Conversions findConversions(@Nullable Conversion example, 
            @Nullable Collection<String> conversionIds, @Nullable Date createdAtFrom, 
            @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, 
            @Nullable BigDecimal partnerBuyAmountFrom, @Nullable BigDecimal partnerBuyAmountTo, 
            @Nullable BigDecimal partnerSellAmountFrom, @Nullable BigDecimal partnerSellAmountTo, 
            @Nullable BigDecimal buyAmountFrom, @Nullable BigDecimal buyAmountTo, 
            @Nullable BigDecimal sellAmountFrom, @Nullable BigDecimal sellAmountTo, 
            @Nullable String uniqueRequestId) throws CurrencyCloudException {
        return client.findConversions(example, conversionIds, createdAtFrom, createdAtTo,
                updatedAtFrom, updatedAtTo, partnerBuyAmountFrom, partnerBuyAmountTo,
                partnerSellAmountFrom, partnerSellAmountTo, buyAmountFrom, buyAmountTo,
                sellAmountFrom, sellAmountTo, uniqueRequestId, getOnBehalfOf());
    }

    @Override
    public Payment createPayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        return client.createPayment(payment, payer, getOnBehalfOf());
    }

    @Override
    public Payment retrievePayment(String id) throws CurrencyCloudException {
        return client.retrievePayment(id, getOnBehalfOf());
    }

    @Override
    public Payment updatePayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException {
        return client.updatePayment(payment, payer, getOnBehalfOf());
    }

    @Override
    public Payments findPayments(@Nullable Payment example, @Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable Date paymentDateFrom, @Nullable Date paymentDateTo, @Nullable Date transferredAtFrom, @Nullable Date transferredAtTo, @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, @Nullable Pagination pagination, @Nullable String uniqueRequestId) throws CurrencyCloudException {
        return client.findPayments(example,
                amountFrom,
                amountTo,
                paymentDateFrom,
                paymentDateTo,
                transferredAtFrom,
                transferredAtTo,
                createdAtFrom,
                createdAtTo,
                updatedAtFrom,
                updatedAtTo,
                pagination,
                uniqueRequestId, getOnBehalfOf()
        );
    }

    @Override
    public Payment deletePayment(String paymentId) throws CurrencyCloudException {
        return client.deletePayment(paymentId, getOnBehalfOf());
    }

    @Override
    public Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException {
        return client.findRates(currencyPair, ignoreInvalidPairs, getOnBehalfOf());
    }

    @Override
    public DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException {
        return client.detailedRates(buyCurrency, sellCurrency, fixedSide, amount, conversionDate, getOnBehalfOf());
    }

    @Override
    public Settlement createSettlement() throws CurrencyCloudException {
        return client.createSettlement(getOnBehalfOf());
    }

    @Override
    public Settlement retrieveSettlement(String id) throws CurrencyCloudException {
        return client.retrieveSettlement(id, getOnBehalfOf());
    }

    @Override
    public Settlements findSettlements(@Nullable String shortReference, @Nullable String status, 
            @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, 
            @Nullable Date updatedAtTo, @Nullable Date releasedAtFrom, @Nullable Date releasedAtTo, 
            @Nullable Pagination pagination) throws CurrencyCloudException {
        return client.findSettlements(shortReference, status, createdAtFrom, createdAtTo, 
                updatedAtFrom, updatedAtTo, releasedAtFrom, releasedAtTo, pagination, getOnBehalfOf());
    }

    @Override
    public Settlement deleteSettlement(String settlementId) throws CurrencyCloudException {
        return client.deleteSettlement(settlementId, getOnBehalfOf());
    }

    @Override
    public Settlement addConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return client.addConversion(settlementId, conversionId, getOnBehalfOf());
    }

    @Override
    public Settlement removeConversion(String settlementId, String conversionId) throws CurrencyCloudException {
        return client.removeConversion(settlementId, conversionId, getOnBehalfOf());
    }

    @Override
    public Settlement releaseSettlement(String settlementId) throws CurrencyCloudException {
        return client.releaseSettlement(settlementId, getOnBehalfOf());
    }

    @Override
    public Settlement unreleaseSettlement(String settlementId) throws CurrencyCloudException {
        return client.unreleaseSettlement(settlementId, getOnBehalfOf());
    }

    @Override
    public Transaction retrieveTransaction(String id) throws CurrencyCloudException {
        return client.retrieveTransaction(id, getOnBehalfOf());
    }

    @Override
    public Transactions findTransactions(@Nullable Transaction example, @Nullable BigDecimal amountFrom, @Nullable BigDecimal amountTo, @Nullable Date settlesAtFrom, @Nullable Date settlesAtTo, @Nullable Date createdAtFrom, @Nullable Date createdAtTo, @Nullable Date updatedAtFrom, @Nullable Date updatedAtTo, @Nullable Pagination pagination) throws CurrencyCloudException {
        return client.findTransactions(example,
                amountFrom,
                amountTo,
                settlesAtFrom,
                settlesAtTo,
                createdAtFrom,
                createdAtTo,
                updatedAtFrom,
                updatedAtTo,
                pagination, getOnBehalfOf());
    }
}
