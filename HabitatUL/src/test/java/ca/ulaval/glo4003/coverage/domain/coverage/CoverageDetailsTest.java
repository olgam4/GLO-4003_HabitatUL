package ca.ulaval.glo4003.coverage.domain.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageCategoryGenerator.createAdditionalCoverageCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageCategoryGenerator.createBaseCoverageCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.*;
import static org.junit.Assert.*;

public class CoverageDetailsTest {
  private static final CoverageCategory BASE_COVERAGE_CATEGORY = createBaseCoverageCategory();
  private static final CoverageDetail ADDITIONAL_COVERAGE_DETAIL = createAdditionalCoverageDetail();
  private static final CoverageCategory ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY =
      ADDITIONAL_COVERAGE_DETAIL.getCoverage();
  private static final Amount ADDITIONAL_COVERAGE_DETAIL_AMOUNT =
      ADDITIONAL_COVERAGE_DETAIL.getAmount();
  private static final CoverageDetail ANOTHER_ADDITIONAL_COVERAGE_DETAIL =
      createAdditionalCoverageDetail();

  private CoverageDetails subject;

  @Before
  public void setUp() {
    subject = createCoverageDetails();
  }

  @Test
  public void shouldBeImmutable() {
    Set<CoverageDetail> collection = subject.getCollection();
    Set<CoverageDetail> initialCollection = new HashSet<>(collection);

    collection.add(ADDITIONAL_COVERAGE_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void addingCoverageDetail_shouldAppendCoverageDetailToExistingDetails() {
    Set<CoverageDetail> collection = subject.getCollection();

    CoverageDetails coverageDetails = subject.addCoverageDetail(ADDITIONAL_COVERAGE_DETAIL);

    collection.add(ADDITIONAL_COVERAGE_DETAIL);
    assertEquals(collection, coverageDetails.getCollection());
  }

  @Test
  public void gettingCoverageAmount_withExistingCoverageCategory_shouldReturnAssociatedAmount() {
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withAdditionalCoverageDetail(ADDITIONAL_COVERAGE_DETAIL)
            .build();

    Amount coverageAmount = subject.getCoverageAmount(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);

    assertEquals(ADDITIONAL_COVERAGE_DETAIL_AMOUNT, coverageAmount);
  }

  @Test
  public void gettingCoverageAmount_withNotExistingCoverageCategory_shouldReturnNullAmount() {
    subject = CoverageDetailsBuilder.aCoverageDetails().build();

    Amount coverageAmount = subject.getCoverageAmount(createAdditionalCoverageCategory());

    assertEquals(Amount.ZERO, coverageAmount);
  }

  @Test
  public void checkingIfCoverageCategoryIsIncluded_withIncludedCoverageCategory_shouldBeTrue() {
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withAdditionalCoverageDetail(ADDITIONAL_COVERAGE_DETAIL)
            .build();

    assertTrue(subject.includes(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY));
  }

  @Test
  public void checkingIfCoverageCategoryIsIncluded_withNotCoveredCoverageCategory_shouldBeFalse() {
    subject = CoverageDetailsBuilder.aCoverageDetails().build();

    assertFalse(subject.includes(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY));
  }

  @Test
  public void updatingCoverageDetails_shouldUpdateCoverageDetails() {
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withAdditionalCoverageDetail(ADDITIONAL_COVERAGE_DETAIL)
            .build();
    CoverageDetail updatedCoverageDetail =
        createCoverageDetail(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);

    CoverageDetails updated = subject.update(updatedCoverageDetail);

    Amount coverageAmount = updated.getCoverageAmount(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);
    assertEquals(updatedCoverageDetail.getAmount(), coverageAmount);
  }

  @Test
  public void
      updatingCoverageDetails_withMultipleCoveragesUpdated_shouldUpdateAllCoverageDetails() {
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withAdditionalCoverageDetail(ADDITIONAL_COVERAGE_DETAIL)
            .build();
    CoverageDetail firstUpdatedCoverageDetail = createCoverageDetail(BASE_COVERAGE_CATEGORY);
    CoverageDetail secondUpdatedCoverageDetail =
        createCoverageDetail(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);
    Set<CoverageDetail> updatedCoverageDetails =
        new HashSet<>(Arrays.asList(firstUpdatedCoverageDetail, secondUpdatedCoverageDetail));

    CoverageDetails updated = subject.update(updatedCoverageDetails);

    Amount firstCoverageAmount = updated.getCoverageAmount(BASE_COVERAGE_CATEGORY);
    Amount secondCoverageAmount =
        updated.getCoverageAmount(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);
    assertEquals(firstUpdatedCoverageDetail.getAmount(), firstCoverageAmount);
    assertEquals(secondUpdatedCoverageDetail.getAmount(), secondCoverageAmount);
  }

  @Test
  public void updatingCoverageDetails_shouldBeImmutable() {
    Set<CoverageDetail> initialCollection = new HashSet<>(subject.getCollection());

    subject.update(ANOTHER_ADDITIONAL_COVERAGE_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }
}
