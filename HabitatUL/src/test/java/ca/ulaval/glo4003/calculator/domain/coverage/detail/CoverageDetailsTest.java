package ca.ulaval.glo4003.calculator.domain.coverage.detail;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static ca.ulaval.glo4003.helper.calculator.coverage.CoverageDetailsGenerator.createCoverageDetail;
import static ca.ulaval.glo4003.helper.calculator.coverage.CoverageDetailsGenerator.createCoverageDetails;
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
}
