package ca.ulaval.glo4003.coverage.domain.coverage;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetail;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.CoverageCategoryGenerator.createAdditionalCoverageCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.*;
import static org.junit.Assert.assertEquals;

public class CoverageDetailsTest {
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
    List<CoverageDetail> collection = subject.getCollection();
    List<CoverageDetail> initialCollection = new ArrayList<>(collection);

    collection.add(ADDITIONAL_COVERAGE_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void addingCoverageDetail_shouldAppendCoverageDetailToExistingDetails() {
    List<CoverageDetail> collection = subject.getCollection();

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
  public void updatingCoverageDetails_shouldUpdateCoverageDetails() {
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withAdditionalCoverageDetail(ADDITIONAL_COVERAGE_DETAIL)
            .build();
    CoverageDetail updatedCoverageDetail =
        createCoverageDetail(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);

    CoverageDetails updatedCoverageDetails = subject.update(updatedCoverageDetail);

    Amount coverageAmount =
        updatedCoverageDetails.getCoverageAmount(ADDITIONAL_COVERAGE_DETAIL_COVERAGE_CATEGORY);
    assertEquals(updatedCoverageDetail.getAmount(), coverageAmount);
  }

  @Test
  public void updatingCoverageDetails_shouldBeImmutable() {
    List<CoverageDetail> initialCollection = new ArrayList<>(subject.getCollection());

    subject.update(ANOTHER_ADDITIONAL_COVERAGE_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }
}
