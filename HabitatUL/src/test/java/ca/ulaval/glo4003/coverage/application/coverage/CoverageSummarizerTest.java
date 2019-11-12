package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.application.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.BicycleEndorsementCoverageDetail;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoverageSummarizerTest {
  private static final QuoteForm QUOTE_FORM = QuoteFormGenerator.createQuoteForm();

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
            .withCivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getLimit())
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
            .withCivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getLimit())
            .withAdditionalCoverageDetail(
                new BicycleEndorsementCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getBicycle().getPrice()))
            .build();
    assertEquals(expectedCoverageDetails, coverageDetails);
  }
}
