package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.calculator.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.calculator.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteId;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@SuppressWarnings("checkstyle:Indentation")
@JsonPropertyOrder({
  "quoteId",
  "expirationDate",
  "effectivePeriod",
  "coverageDetails",
  "totalPremium",
  "premiumDetails"
})
public class QuoteResponse {
  private QuoteId quoteId;
  private DateTime expirationDate;
  private Period effectivePeriod;
  private CoverageDetails coverageDetails;
  private Money totalPremium;
  private PremiumDetails premiumDetails;

  public QuoteResponse(
      QuoteId quoteId,
      DateTime expirationDate,
      Period effectivePeriod,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails) {
    this.quoteId = quoteId;
    this.expirationDate = expirationDate;
    this.effectivePeriod = effectivePeriod;
    this.coverageDetails = coverageDetails;
    this.totalPremium = premiumDetails.computeTotalPremium();
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

  public Money getTotalPremium() {
    return totalPremium;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
