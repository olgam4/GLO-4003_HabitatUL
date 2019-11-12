package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.application.coverage.CoverageSummarizer;
import ca.ulaval.glo4003.coverage.application.dto.CoverageDto;
import ca.ulaval.glo4003.coverage.application.form.validation.FormValidator;
import ca.ulaval.glo4003.coverage.application.premium.PremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator.createQuoteForm;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoverageDomainServiceTest {
  private static final QuoteForm QUOTE_FORM = createQuoteForm();
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final PremiumDetails PREMIUM_DETAILS = createPremiumDetails();

  @Mock private FormValidator formValidator;
  @Mock private CoverageSummarizer coverageSummarizer;
  @Mock private PremiumCalculator premiumCalculator;

  private CoverageDomainService subject;

  @Before
  public void setUp() {
    when(coverageSummarizer.summarizeQuoteCoverage(any(QuoteForm.class)))
        .thenReturn(COVERAGE_DETAILS);
    when(premiumCalculator.computeQuotePremium(any(QuoteForm.class))).thenReturn(PREMIUM_DETAILS);
    subject = new CoverageDomainService(formValidator, coverageSummarizer, premiumCalculator);
  }

  @Test
  public void requestingQuoteCoverage_shouldValidateForm() {
    subject.requestQuoteCoverage(QUOTE_FORM);

    verify(formValidator).validate(QUOTE_FORM);
  }

  @Test
  public void requestingQuoteCoverage_shouldSummarizeCoverage() {
    subject.requestQuoteCoverage(QUOTE_FORM);

    verify(coverageSummarizer).summarizeQuoteCoverage(QUOTE_FORM);
  }

  @Test
  public void requestingQuoteCoverage_shouldComputePremium() {
    subject.requestQuoteCoverage(QUOTE_FORM);

    verify(premiumCalculator).computeQuotePremium(QUOTE_FORM);
  }

  @Test
  public void requestingQuoteCoverage_shouldProduceCorrespondingCoverageDto() {
    CoverageDto coverageDto = subject.requestQuoteCoverage(QUOTE_FORM);

    CoverageDto expectedCoverageDto = new CoverageDto(COVERAGE_DETAILS, PREMIUM_DETAILS);
    assertEquals(expectedCoverageDto, coverageDto);
  }
}
