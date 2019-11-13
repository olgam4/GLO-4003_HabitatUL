package ca.ulaval.glo4003.coverage.domain.coverage.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.shared.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetail;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static org.junit.Assert.assertEquals;

public class CoverageDetailsTest {
  private static final CoverageDetail COVERAGE_DETAIL = createCoverageDetail();

  private CoverageDetails subject;

  @Before
  public void setUp() {
    subject = createCoverageDetails();
  }

  @Test
  public void shouldBeImmutable() {
    List<CoverageDetail> collection = subject.getCollection();
    List<CoverageDetail> initialCollection = new ArrayList<>(collection);

    collection.add(COVERAGE_DETAIL);

    assertEquals(initialCollection, subject.getCollection());
  }

  @Test
  public void addingCoverageDetail_shouldAppendCoverageDetailToExistingDetails() {
    List<CoverageDetail> collection = subject.getCollection();

    CoverageDetails coverageDetails = subject.addCoverageDetail(COVERAGE_DETAIL);

    collection.add(COVERAGE_DETAIL);
    assertEquals(collection, coverageDetails.getCollection());
  }

  @Test
  public void gettingCoverageAmount_withExistingCoverageCategory_shouldReturnAssociatedAmount() {
    Amount personalPropertyCoverageAmount = MoneyGenerator.createAmount();
    subject =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(personalPropertyCoverageAmount)
            .build();

    Amount coverageAmount = subject.getCoverageAmount(CoverageCategory.PERSONAL_PROPERTY);

    assertEquals(personalPropertyCoverageAmount, coverageAmount);
  }

  @Test
  public void gettingCoverageAmount_withoutExistingCoverageCategory_shouldReturnNullAmount() {
    subject = CoverageDetailsBuilder.aCoverageDetails().build();

    Amount coverageAmount = subject.getCoverageAmount(CoverageCategory.BASIC_BLOCK);

    assertEquals(Amount.ZERO, coverageAmount);
  }
}
