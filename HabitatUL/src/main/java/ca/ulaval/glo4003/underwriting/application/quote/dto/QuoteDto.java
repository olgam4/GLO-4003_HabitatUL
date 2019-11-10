package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteDto extends DataTransferObject {
  private final QuoteId quoteId;
  private final DateTime expirationDate;
  private final Period effectivePeriod;
  private final QuoteCoverageOverviewDto coverageOverview;
  private final PremiumDetails premiumDetails;

  public QuoteDto(
      QuoteId quoteId,
      DateTime expirationDate,
      Period effectivePeriod,
      QuoteCoverageOverviewDto coverageOverview,
      PremiumDetails premiumDetails) {
    this.quoteId = quoteId;
    this.expirationDate = expirationDate;
    this.effectivePeriod = effectivePeriod;
    this.coverageOverview = coverageOverview;
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

  public QuoteCoverageOverviewDto getCoverageOverview() {
    return coverageOverview;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
