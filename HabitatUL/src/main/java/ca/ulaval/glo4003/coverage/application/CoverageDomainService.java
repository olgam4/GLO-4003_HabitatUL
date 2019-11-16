package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.coverage.application.coverage.CoverageSummarizer;
import ca.ulaval.glo4003.coverage.application.form.FormValidator;
import ca.ulaval.glo4003.coverage.application.premium.PremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;

public class CoverageDomainService {
  private FormValidator formValidator;
  private CoverageSummarizer coverageSummarizer;
  private PremiumCalculator premiumCalculator;

  public CoverageDomainService() {
    this(new FormValidator(), new CoverageSummarizer(), new PremiumCalculator());
  }

  public CoverageDomainService(
      FormValidator formValidator,
      CoverageSummarizer coverageSummarizer,
      PremiumCalculator premiumCalculator) {
    this.formValidator = formValidator;
    this.coverageSummarizer = coverageSummarizer;
    this.premiumCalculator = premiumCalculator;
  }

  public CoverageDto requestQuoteCoverage(QuoteForm quoteForm) {
    formValidator.validateQuoteForm(quoteForm);
    CoverageDetails coverageDetails = coverageSummarizer.summarizeQuoteCoverage(quoteForm);
    PremiumDetails premiumDetails = premiumCalculator.computeQuotePremium(quoteForm);
    return new CoverageDto(coverageDetails, premiumDetails);
  }

  public CoverageDto requestBicycleEndorsementCoverage(
      BicycleEndorsementForm bicycleEndorsementForm) {
    formValidator.validateBicycleEndorsementForm(bicycleEndorsementForm);
    CoverageDetails coverageDetails =
        coverageSummarizer.summarizeBicycleEndorsementCoverage(bicycleEndorsementForm);
    PremiumDetails premiumDetails =
        premiumCalculator.computeBicycleEndorsementPremium(bicycleEndorsementForm);
    return new CoverageDto(coverageDetails, premiumDetails);
  }
}
