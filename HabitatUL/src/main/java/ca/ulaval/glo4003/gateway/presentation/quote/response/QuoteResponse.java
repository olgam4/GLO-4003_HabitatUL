package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"quoteId", "premium", "effectivePeriod", "expirationDate", "coverageOverview"})
public class QuoteResponse {
  private QuoteId quoteId;
  private Money premium;
  private Period effectivePeriod;
  private DateTime expirationDate;
  private QuoteCoverageOverviewResponse coverageOverview;

  public QuoteResponse(
      QuoteId quoteId,
      Money premium,
      Period effectivePeriod,
      DateTime expirationDate,
      QuoteCoverageOverviewResponse coverageOverview) {
    this.quoteId = quoteId;
    this.premium = premium;
    this.effectivePeriod = effectivePeriod;
    this.expirationDate = expirationDate;
    this.coverageOverview = coverageOverview;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Money getPremium() {
    return premium;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public QuoteCoverageOverviewResponse getCoverageOverview() {
    return coverageOverview;
  }
}
