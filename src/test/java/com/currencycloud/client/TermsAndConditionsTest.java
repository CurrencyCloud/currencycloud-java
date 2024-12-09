package com.currencycloud.client;

import com.currencycloud.client.model.TermsAndConditionsAcceptance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class TermsAndConditionsTest extends TestSupport {
  private CurrencyCloudClient client;

  @Before
  public void prepareClient() {
    client = prepareTestClient(null, null, "acad59188ce6ddb54d4043bc4efb5f57");
  }

  @Before
  @After
  public void methodName() { log.debug("------------------------- " + name.getMethodName() + " -------------------------"); }

  @Test
  public void testAccept(){
    TermsAndConditionsAcceptance acceptance = client.acceptTermsAndConditions("VGSI",
            "1.0",
            "ACCOUNT",
            "ebcaee2f-a733-11ef-8de2-0242ac1d0002",
            "firstName",
            "lastName",
            "development@currencycloud.com");

    assertThat(acceptance.getAcceptanceId(), equalTo("e781c919-a733-11ef-8de2-0242ac1d0002"));
    assertThat(acceptance.getAcceptedAt(), equalTo(parseDateTime("2024-10-04T15:27:04+00:00")));
  }
}
