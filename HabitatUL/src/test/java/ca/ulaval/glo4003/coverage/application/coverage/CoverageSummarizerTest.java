package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.BicycleEndorsementCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;
import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator.createQuoteForm;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoverageSummarizerTest {
  private static final QuoteForm QUOTE_FORM = createQuoteForm();
  private static final CoverageDetails COVERAGE_DETAILS =
      CoverageDetailsBuilder.aCoverageDetails().build();
  private static final BicycleEndorsementForm BICYCLE_ENDORSEMENT_FORM =
      BicycleEndorsementFormBuilder.aBicycleEndorsementForm()
          .withCurrentCoverageDetails(COVERAGE_DETAILS)
          .build();

  @Mock private AdditionalCoverageResolver additionalCoverageResolver;

  private CoverageSummarizer subject;

  @Before
  public void setUp() {
    subject = new CoverageSummarizer(additionalCoverageResolver);
  }

  @Test
  public void
      summarizingQuoteCoverage_withoutBicycleEndorsement_shouldReturnCorrespondingCoverageDetails() {
    when(additionalCoverageResolver.shouldIncludeBicycleEndorsement(any(QuoteForm.class)))
        .thenReturn(false);

    CoverageDetails coverageDetails = subject.summarizeQuoteCoverage(QUOTE_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                QUOTE_FORM.getPersonalProperty().getCoverageAmount())
            .withCivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getCoverageAmount())
            .build();
    assertEquals(expectedCoverageDetails, coverageDetails);
  }

  @Test
  public void
      summarizingQuoteCoverage_withBicycleEndorsement_shouldReturnCorrespondingCoverageDetails() {
    when(additionalCoverageResolver.shouldIncludeBicycleEndorsement(any(QuoteForm.class)))
        .thenReturn(true);

    CoverageDetails coverageDetails = subject.summarizeQuoteCoverage(QUOTE_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                QUOTE_FORM.getPersonalProperty().getCoverageAmount())
            .withCivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getCoverageAmount())
            .withAdditionalCoverageDetail(
                new BicycleEndorsementCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getBicycle().getPrice()))
            .build();
    assertEquals(expectedCoverageDetails, coverageDetails);
  }

  @Test
  public void summarizingBicycleEndorsementCoverage_shouldReturnCorrespondingCoverageDetails() {
    CoverageDetails coverageDetails =
        subject.summarizeBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                COVERAGE_DETAILS.getCoverageAmount(PERSONAL_PROPERTY))
            .withCivilLiabilityCoverageDetail(COVERAGE_DETAILS.getCoverageAmount(CIVIL_LIABILITY))
            .withBicycleEndorsementCoverageDetail(BICYCLE_ENDORSEMENT_FORM.getBicycle().getPrice())
            .build();
    assertEquals(expectedCoverageDetails, coverageDetails);
  }
}
