package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.premium.PremiumCategoryGenerator.createAdditionalPremiumCategory;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.*;
import static org.junit.Assert.assertEquals;

public class PremiumDetailsTest {
  private static final PremiumDetail ADDITIONAL_PREMIUM_DETAIL = createAdditionalPremiumDetail();
  private static final PremiumCategory ADDITIONAL_PREMIUM_DETAIL_PREMIUM_CATEGORY =
      ADDITIONAL_PREMIUM_DETAIL.getCoverage();
  private static final Money ADDITIONAL_PREMIUM_DETAIL_PREMIUM =
      ADDITIONAL_PREMIUM_DETAIL.getPremium();
  private static final PremiumDetail ANOTHER_ADDITIONAL_PREMIUM_DETAIL =
      createAdditionalPremiumDetail();

  private PremiumDetails subject;

  @Before
  public void setUp() {
    subject = createPremiumDetails();
  }

  @Test
  public void shouldBeImmutable() {
    List<PremiumDetail> collection = subject.getCollection();
    List<PremiumDetail> initialCollection = new ArrayList<>(collection);

    collection.add(ADDITIONAL_PREMIUM_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void addingPremiumDetail_shouldAppendPremiumDetailToExistingDetails() {
    List<PremiumDetail> collection = subject.getCollection();

    PremiumDetails premiumDetails = subject.addPremiumDetail(ADDITIONAL_PREMIUM_DETAIL);

    collection.add(ADDITIONAL_PREMIUM_DETAIL);
    assertEquals(collection, premiumDetails.getCollection());
  }

  @Test
  public void gettingCoveragePremium_withExistingPremiumCategory_shouldReturnAssociatedPremium() {
    subject =
        PremiumDetailsBuilder.aPremiumDetails()
            .withAdditionalPremiumDetail(ADDITIONAL_PREMIUM_DETAIL)
            .build();

    Money coveragePremium = subject.getCoveragePremium(ADDITIONAL_PREMIUM_DETAIL_PREMIUM_CATEGORY);

    assertEquals(ADDITIONAL_PREMIUM_DETAIL_PREMIUM, coveragePremium);
  }

  @Test
  public void gettingCoveragePremium_withNotExistingPremiumCategory_shouldReturnNullPremium() {
    subject = PremiumDetailsBuilder.aPremiumDetails().build();

    Money coveragePremium = subject.getCoveragePremium(createAdditionalPremiumCategory());

    assertEquals(Money.ZERO, coveragePremium);
  }

  @Test
  public void updatingPremiumDetails_shouldUpdatePremiumDetails() {
    subject =
        PremiumDetailsBuilder.aPremiumDetails()
            .withAdditionalPremiumDetail(ADDITIONAL_PREMIUM_DETAIL)
            .build();
    PremiumDetail updatedPremiumDetail =
        createPremiumDetail(ADDITIONAL_PREMIUM_DETAIL_PREMIUM_CATEGORY);

    PremiumDetails updatedPremiumDetails = subject.update(updatedPremiumDetail);

    Money coveragePremium =
        updatedPremiumDetails.getCoveragePremium(ADDITIONAL_PREMIUM_DETAIL_PREMIUM_CATEGORY);
    assertEquals(updatedPremiumDetail.getPremium(), coveragePremium);
  }

  @Test
  public void updatingPremiumDetails_shouldBeImmutable() {
    List<PremiumDetail> initialCollection = new ArrayList<>(subject.getCollection());

    subject.update(ANOTHER_ADDITIONAL_PREMIUM_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void computingTotalPremium_shouldComputeTotalPremium() {
    BasicBlockCoveragePremiumDetail basicBlockCoveragePremiumDetail =
        createBasicBlockCoveragePremiumDetail();
    PremiumDetail firstAdditionalCoveragePremiumDetail = createPremiumDetail();
    PremiumDetail secondAdditionalCoveragePremiumDetail = createPremiumDetail();
    PremiumDetail thirdAdditionalCoveragePremiumDetail = createPremiumDetail();
    subject =
        PremiumDetailsBuilder.aPremiumDetails()
            .withBasicBlockPremiumDetail(basicBlockCoveragePremiumDetail)
            .withAdditionalPremiumDetail(firstAdditionalCoveragePremiumDetail)
            .withAdditionalPremiumDetail(secondAdditionalCoveragePremiumDetail)
            .withAdditionalPremiumDetail(thirdAdditionalCoveragePremiumDetail)
            .build();

    Money totalPremium = subject.computeTotalPremium();

    Money expectedTotalPremium =
        basicBlockCoveragePremiumDetail
            .getPremium()
            .add(firstAdditionalCoveragePremiumDetail.getPremium())
            .add(secondAdditionalCoveragePremiumDetail.getPremium())
            .add(thirdAdditionalCoveragePremiumDetail.getPremium());
    assertEquals(expectedTotalPremium, totalPremium);
  }
}
