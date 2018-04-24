package com.currencycloud.client;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import com.currencycloud.client.model.Transfer;
import com.currencycloud.client.model.Transfers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TransfersTest extends BetamaxTestSupport {

    private CurrencyCloudClient client;

    @Before
    public void prepareClient() {
        client = prepareTestClient(null, null, "334cbfdb9ba9bfb6ffd499b0c6af6b12");
    }

    @Before
    @After
    public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

    @Test
    @Betamax(tape = "can_create", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanCreateTransfer() throws Exception {
        Transfer transfer = Transfer.create("a7117404-e150-11e6-a5af-080027a79e8f", "946f2d58-e150-11e6-a5af-080027a79e8f", "GBP", new BigDecimal("1250.0"));
        transfer.setReason("Client funding");
        transfer = client.createTransfer(transfer);

        assertThat(transfer.getId(), equalTo("993d63bd-e151-11e6-a5af-080027a79e8f"));
        assertThat(transfer.getShortReference(), equalTo("BT-20180101-JGCWQH"));
        assertThat(transfer.getSourceAccountId(), equalTo("a7117404-e150-11e6-a5af-080027a79e8f"));
        assertThat(transfer.getDestinationAccountId(),equalTo("946f2d58-e150-11e6-a5af-080027a79e8f"));
        assertThat(transfer.getCurrency(), equalTo("GBP"));
        assertThat(transfer.getAmount(), equalTo(new BigDecimal("1250.0")));
        assertThat(transfer.getStatus(), equalTo("completed"));
        assertThat(transfer.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getCompletedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getCreatorAccountId(), equalTo("262e3d2a-e152-11e6-a5af-080027a79e8f"));
        assertThat(transfer.getCreatorContactId(), equalTo("30cb8632-e152-11e6-a5af-080027a79e8f"));
        assertThat(transfer.getReason(), equalTo("Client funding"));
    }

    @Test
    @Betamax(tape = "can_retrieve", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanRetrieveTransfer() throws Exception {
        Transfer transfer = client.retrieveTransfer("b0c2df71-28db-42ef-b6b7-5710f22d2115");

        assertThat(transfer.getId(), equalTo("b0c2df71-28db-42ef-b6b7-5710f22d2115"));
        assertThat(transfer.getShortReference(), equalTo("BT-20180101-YRSYGK"));
        assertThat(transfer.getSourceAccountId(), equalTo("1bd29e41-f019-0133-ed7e-0022194273c7"));
        assertThat(transfer.getDestinationAccountId(), equalTo("d9c34271-b7a6-0133-9fe2-0022194273c7"));
        assertThat(transfer.getCurrency(), equalTo("EUR"));
        assertThat(transfer.getAmount(), equalTo(new BigDecimal("123.45")));
        assertThat(transfer.getStatus(), equalTo("completed"));
        assertThat(transfer.getCreatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getUpdatedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getCompletedAt(), equalTo(parseDateTime("2018-01-01T12:34:56+00:00")));
        assertThat(transfer.getCreatorAccountId(), equalTo("2090939e-b2f7-3f2b-1363-4d235b3f58af"));
        assertThat(transfer.getCreatorContactId(), equalTo("8a98ebac-6f88-e205-a685-4d235b1b088b"));
        assertThat(transfer.getReason(), equalTo("Test"));

    }

    @Test
    @Betamax(tape = "can_find", match = {MatchRule.method, MatchRule.uri, MatchRule.body})
    public void testCanFindTransfer() throws Exception {
        Transfers transferData = client.findTransfers(null, null);
        List<Transfer> transfers = transferData.getTransfers();

        assertThat(transfers, not(nullValue()));
        assertThat(transfers.size(), is(2));
        assertThat(transfers.get(0).getId(), equalTo("993d63bd-e151-11e6-a5af-080027a79e8f"));
        assertThat(transfers.get(0).getShortReference(), equalTo("BT-20180101-JGCWQH"));
        assertThat(transfers.get(0).getSourceAccountId(), equalTo("a7117404-e150-11e6-a5af-080027a79e8f"));
        assertThat(transfers.get(0).getDestinationAccountId(), equalTo("946f2d58-e150-11e6-a5af-080027a79e8f"));
        assertThat(transfers.get(0).getCurrency(), equalTo("GBP"));
        assertThat(transfers.get(0).getAmount(), equalTo(new BigDecimal("1250.00")));
        assertThat(transfers.get(0).getStatus(), equalTo("completed"));
    }
}
