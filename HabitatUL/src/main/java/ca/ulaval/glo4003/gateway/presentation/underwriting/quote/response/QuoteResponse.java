package ca.ulaval.glo4003.gateway.presentation.underwriting.quote.response;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
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
  "totalPremium",
  "coverageDetails",
  "premiumDetails"
})
public class QuoteResponse {
  private QuoteId quoteId;
  private DateTime expirationDate;
  private Period effectivePeriod;
  private Money totalPremium;
  private CoverageDetails coverageDetails;
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
    this.totalPremium = premiumDetails.computeTotalPremium();
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

  public Money getTotalPremium() {
    return totalPremium;
  }

  public CoverageDetails getCoverageDetails() {
    return coverageDetails;
  }

  public PremiumDetails getPremiumDetails() {
    return premiumDetails;
  }
}
