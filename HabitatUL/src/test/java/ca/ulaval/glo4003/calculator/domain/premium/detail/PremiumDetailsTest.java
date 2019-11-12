package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.helper.calculator.premium.PremiumDetailsBuilder;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.shared.domain.money.Money;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo4003.helper.calculator.premium.PremiumDetailsGenerator.*;
import static org.junit.Assert.*;

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
  public void checkingIfCoverageCategoryIsIncluded_withIncludedCoverageCategory_shouldBeTrue() {
    PremiumDetail premiumDetail = createPremiumDetail();
    subject =
        PremiumDetailsBuilder.aPremiumDetails().withAdditionalPremiumDetail(premiumDetail).build();

    assertTrue(subject.includes(premiumDetail.getCoverage()));
  }

  @Test
  public void checkingIfCoverageCategoryIsIncluded_withoutIncludedCoverageCategory_shouldBeFalse() {
    subject = PremiumDetailsBuilder.aPremiumDetails().withoutAdditionalPremiumDetail().build();

    List<CoverageCategory> coverageCategories =
        subject.getCollection().stream()
            .map(PremiumDetail::getCoverage)
            .collect(Collectors.toList());
    CoverageCategory sample = EnumSampler.sample(CoverageCategory.class, coverageCategories);
    assertFalse(subject.includes(sample));
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
            .withBasicBlockCoveragePremiumDetail(basicBlockCoveragePremiumDetail)
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
