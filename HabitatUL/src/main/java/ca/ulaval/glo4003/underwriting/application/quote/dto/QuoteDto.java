package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteDto extends DataTransferObject {
  private final QuoteId quoteId;
  private final DateTime expirationDate;
  private final Period effectivePeriod;
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;

  public QuoteDto(
      QuoteId quoteId,
      DateTime expirationDate,
      Period effectivePeriod,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.quoteId = quoteId;
    this.expirationDate = expirationDate;
    this.effectivePeriod = effectivePeriod;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
