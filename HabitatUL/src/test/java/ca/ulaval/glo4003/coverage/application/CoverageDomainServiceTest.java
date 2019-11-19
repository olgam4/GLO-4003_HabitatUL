package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.application.coverage.CoverageSummarizer;
import ca.ulaval.glo4003.coverage.application.form.FormValidator;
import ca.ulaval.glo4003.coverage.application.premium.PremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.BicycleEndorsementFormGenerator.createBicycleEndorsementForm;
import static ca.ulaval.glo4003.helper.coverage.form.CoverageModificationFormGenerator.createCoverageModificationForm;
import static ca.ulaval.glo4003.helper.coverage.form.QuoteFormGenerator.createQuoteForm;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoverageDomainServiceTest {
  private static final QuoteForm QUOTE_FORM = createQuoteForm();
  private static final BicycleEndorsementForm BICYCLE_ENDORSEMENT_FORM =
      createBicycleEndorsementForm();
  private static final CoverageModificationForm COVERAGE_MODIFICATION_FORM =
      createCoverageModificationForm();
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
    when(coverageSummarizer.summarizeBicycleEndorsementCoverage(any(BicycleEndorsementForm.class)))
        .thenReturn(COVERAGE_DETAILS);
    when(premiumCalculator.computeBicycleEndorsementPremium(any(BicycleEndorsementForm.class)))
        .thenReturn(PREMIUM_DETAILS);
    when(coverageSummarizer.summarizeCoverageModification(any(CoverageModificationForm.class)))
        .thenReturn(COVERAGE_DETAILS);
    when(premiumCalculator.computeCoverageModificationPremium(any(CoverageModificationForm.class)))
        .thenReturn(PREMIUM_DETAILS);
    subject = new CoverageDomainService(formValidator, coverageSummarizer, premiumCalculator);
  }

  @Test
  public void requestingQuoteCoverage_shouldValidateForm() {
    subject.requestQuoteCoverage(QUOTE_FORM);

    verify(formValidator).validateQuoteForm(QUOTE_FORM);
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

  @Test
  public void requestingBicycleEndorsementCoverage_shouldValidateForm() {
    subject.requestBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);

    verify(formValidator).validateBicycleEndorsementForm(BICYCLE_ENDORSEMENT_FORM);
  }

  @Test
  public void requestingBicycleEndorsementCoverage_shouldSummarizeCoverage() {
    subject.requestBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);

    verify(coverageSummarizer).summarizeBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);
  }

  @Test
  public void requestingBicycleEndorsementCoverage_shouldComputePremium() {
    subject.requestBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);

    verify(premiumCalculator).computeBicycleEndorsementPremium(BICYCLE_ENDORSEMENT_FORM);
  }

  @Test
  public void requestingBicycleEndorsementCoverage_shouldProduceCorrespondingCoverageDto() {
    CoverageDto coverageDto = subject.requestBicycleEndorsementCoverage(BICYCLE_ENDORSEMENT_FORM);

    CoverageDto expectedCoverageDto = new CoverageDto(COVERAGE_DETAILS, PREMIUM_DETAILS);
    assertEquals(expectedCoverageDto, coverageDto);
  }

  @Test
  public void requestingCoverageModification_shouldValidateForm() {
    subject.requestCoverageModification(COVERAGE_MODIFICATION_FORM);

    verify(formValidator).validateCoverageModificationForm(COVERAGE_MODIFICATION_FORM);
  }

  @Test
  public void requestingCoverageModification_shouldSummarizeCoverage() {
    subject.requestCoverageModification(COVERAGE_MODIFICATION_FORM);

    verify(coverageSummarizer).summarizeCoverageModification(COVERAGE_MODIFICATION_FORM);
  }

  @Test
  public void requestingCoverageModification_shouldComputePremium() {
    subject.requestCoverageModification(COVERAGE_MODIFICATION_FORM);

    verify(premiumCalculator).computeCoverageModificationPremium(COVERAGE_MODIFICATION_FORM);
  }

  @Test
  public void requestingCoverageModification_shouldProduceCorrespondingCoverageDto() {
    CoverageDto coverageDto = subject.requestCoverageModification(COVERAGE_MODIFICATION_FORM);

    CoverageDto expectedCoverageDto = new CoverageDto(COVERAGE_DETAILS, PREMIUM_DETAILS);
    assertEquals(expectedCoverageDto, coverageDto);
  }
}
