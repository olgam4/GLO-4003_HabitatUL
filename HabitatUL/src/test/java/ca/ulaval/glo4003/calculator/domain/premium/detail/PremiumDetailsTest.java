package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.helper.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.premium.PremiumDetailsGenerator.*;
import static org.junit.Assert.assertEquals;

public class PremiumDetailsTest {
  private static final PremiumDetail PREMIUM_DETAIL = createPremiumDetail();

  private PremiumDetails subject;

  @Before
  public void setUp() {
    subject = createPremiumDetails();
  }

  @Test
  public void shouldBeImmutable() {
    List<PremiumDetail> collection = subject.getCollection();
    List<PremiumDetail> initialCollection = new ArrayList<>(collection);

    collection.add(PREMIUM_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void addingPremiumDetail_shouldAppendPremiumDetailToExistingDetails() {
    List<PremiumDetail> collection = subject.getCollection();

    PremiumDetails premiumDetails = subject.addPremiumDetail(PREMIUM_DETAIL);

    collection.add(PREMIUM_DETAIL);
    assertEquals(collection, premiumDetails.getCollection());
  }

  @Test
  public void computingTotalPremium_shouldComputeTotalPremium() {
    BaseCoveragePremiumDetail baseCoveragePremiumDetail = createBaseCoveragePremiumDetail();
    PremiumDetail firstAdditionalCoveragePremiumDetail = createPremiumDetail();
    PremiumDetail secondAdditionalCoveragePremiumDetail = createPremiumDetail();
    PremiumDetail thirdAdditionalCoveragePremiumDetail = createPremiumDetail();
    subject =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBaseCoveragePremiumDetail(baseCoveragePremiumDetail)
            .withAdditionalPremiumDetail(firstAdditionalCoveragePremiumDetail)
            .withAdditionalPremiumDetail(secondAdditionalCoveragePremiumDetail)
            .withAdditionalPremiumDetail(thirdAdditionalCoveragePremiumDetail)
            .build();

    Money totalPremium = subject.computeTotalPremium();

    Money expectedTotalPremium =
        baseCoveragePremiumDetail
            .getPremium()
            .add(firstAdditionalCoveragePremiumDetail.getPremium())
            .add(secondAdditionalCoveragePremiumDetail.getPremium())
            .add(thirdAdditionalCoveragePremiumDetail.getPremium());
    assertEquals(expectedTotalPremium, totalPremium);
  }
}
