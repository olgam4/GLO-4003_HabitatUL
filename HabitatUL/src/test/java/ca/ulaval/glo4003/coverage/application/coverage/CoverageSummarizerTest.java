package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.BikeEndorsementCoverageDetail;
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
      summarizingQuoteCoverage_withoutBikeEndorsement_shouldReturnCorrespondingCoverageDetails() {
    when(additionalCoverageResolver.shouldIncludeBikeEndorsement(any(QuoteForm.class)))
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
      summarizingQuoteCoverage_withBikeEndorsement_shouldReturnCorrespondingCoverageDetails() {
    when(additionalCoverageResolver.shouldIncludeBikeEndorsement(any(QuoteForm.class)))
        .thenReturn(true);

    CoverageDetails coverageDetails = subject.summarizeQuoteCoverage(QUOTE_FORM);

    CoverageDetails expectedCoverageDetails =
        CoverageDetailsBuilder.aCoverageDetails()
            .withPersonalPropertyCoverageDetail(
                QUOTE_FORM.getPersonalProperty().getCoverageAmount())
            .withCivilLiabilityCoverageDetail(QUOTE_FORM.getCivilLiability().getLimit())
            .withAdditionalCoverageDetail(
                new BikeEndorsementCoverageDetail(
                    QUOTE_FORM.getPersonalProperty().getBike().getPrice()))
            .build();
    assertEquals(expectedCoverageDetails, coverageDetails);
  }
}
