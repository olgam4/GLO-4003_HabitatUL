package ca.ulaval.glo4003.underwriting.application.quote.dto;

import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;

public class QuoteDto extends DataTransferObject {
  private final QuoteId quoteId;
  private final Money price;
  private final DateTime expirationDate;
  private final Period effectivePeriod;
  private final QuoteCoverageOverviewDto coverageOverview;

  public QuoteDto(
      QuoteId quoteId,
      Money price,
      Period effectivePeriod,
      DateTime expirationDate,
      QuoteCoverageOverviewDto coverageOverview) {
    this.quoteId = quoteId;
    this.price = price;
    this.effectivePeriod = effectivePeriod;
    this.expirationDate = expirationDate;
    this.coverageOverview = coverageOverview;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public Money getPrice() {
    return price;
  }

  public Period getEffectivePeriod() {
    return effectivePeriod;
  }

  public DateTime getExpirationDate() {
    return expirationDate;
  }

  public QuoteCoverageOverviewDto getCoverageOverview() {
    return coverageOverview;
  }
}
