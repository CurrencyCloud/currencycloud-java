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

public interface OnBehalfFunctions {
    Account retrieveAccount(String accountId) throws CurrencyCloudException;

    Beneficiary validateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException;

    Beneficiary createBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException;

    Beneficiary retrieveBeneficiary(String id) throws CurrencyCloudException;

    Beneficiary updateBeneficiary(Beneficiary beneficiary) throws CurrencyCloudException;

    Beneficiaries findBeneficiaries(@Nullable Beneficiary example, @Nullable Pagination pagination)
            throws CurrencyCloudException;

    Beneficiary firstBeneficiary(@Nullable Beneficiary beneficiary) throws CurrencyCloudException;

    Beneficiary deleteBeneficiary(String id) throws CurrencyCloudException;

    Conversion createConversion(
            Conversion conversion,
            BigDecimal amount,
            String reason,
            Boolean termAgreement
    ) throws CurrencyCloudException;

    Conversions findConversions(
            @Nullable Conversion example,
            @Nullable Collection<String> conversionIds,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable BigDecimal partnerBuyAmountFrom,
            @Nullable BigDecimal partnerBuyAmountTo,
            @Nullable BigDecimal partnerSellAmountFrom,
            @Nullable BigDecimal partnerSellAmountTo,
            @Nullable BigDecimal buyAmountFrom,
            @Nullable BigDecimal buyAmountTo,
            @Nullable BigDecimal sellAmountFrom,
            @Nullable BigDecimal sellAmountTo,
            @Nullable String uniqueRequestId
    ) throws CurrencyCloudException;

    Payment createPayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException;

    Payment retrievePayment(String id) throws CurrencyCloudException;

    Payment updatePayment(Payment payment, @Nullable Payer payer) throws CurrencyCloudException;

    Payments findPayments(@Nullable Payment example,
            @Nullable BigDecimal amountFrom,
            @Nullable BigDecimal amountTo,
            @Nullable Date paymentDateFrom,
            @Nullable Date paymentDateTo,
            @Nullable Date transferredAtFrom,
            @Nullable Date transferredAtTo,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable Pagination pagination,
            @Nullable String uniqueRequestId
    ) throws CurrencyCloudException;

    Payment deletePayment(String paymentId) throws CurrencyCloudException;

    Rates findRates(Collection<String> currencyPair, @Nullable Boolean ignoreInvalidPairs) throws CurrencyCloudException;

    DetailedRate detailedRates(String buyCurrency, String sellCurrency, String fixedSide, BigDecimal amount, @Nullable Date conversionDate) throws CurrencyCloudException;

    Settlement createSettlement() throws CurrencyCloudException;

    Settlement retrieveSettlement(String id) throws CurrencyCloudException;

    Settlements findSettlements(
            @Nullable String shortReference,
            @Nullable String status,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable Date releasedAtFrom,
            @Nullable Date releasedAtTo,
            @Nullable Pagination pagination
    ) throws CurrencyCloudException;

    Settlement deleteSettlement(String settlementId) throws CurrencyCloudException;

    Settlement addConversion(String settlementId, String conversionId) throws CurrencyCloudException;

    Settlement removeConversion(String settlementId, String conversionId) throws CurrencyCloudException;

    Settlement releaseSettlement(String settlementId) throws CurrencyCloudException;

    Settlement unreleaseSettlement(String settlementId) throws CurrencyCloudException;

    Transaction retrieveTransaction(String id) throws CurrencyCloudException;

    Transactions findTransactions(
            @Nullable Transaction example,
            @Nullable BigDecimal amountFrom,
            @Nullable BigDecimal amountTo,
            @Nullable Date settlesAtFrom,
            @Nullable Date settlesAtTo,
            @Nullable Date createdAtFrom,
            @Nullable Date createdAtTo,
            @Nullable Date updatedAtFrom,
            @Nullable Date updatedAtTo,
            @Nullable Pagination pagination
    ) throws CurrencyCloudException;
}
