package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.mediator.Event;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

public class QuotePurchasedEvent extends Event {
  private final QuoteId quoteId;
  private final QuoteForm quoteForm;
  private final Period effectivePeriod;
  private final CoverageDetails coverageDetails;
  private final PremiumDetails premiumDetails;
  private final Date purchaseDate;

  public QuotePurchasedEvent(
      QuoteId quoteId,
      QuoteForm quoteForm,
      Period effectivePeriod,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails,
      Date purchaseDate) {
    this.quoteId = quoteId;
    this.quoteForm = quoteForm;
    this.effectivePeriod = effectivePeriod;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
    this.purchaseDate = purchaseDate;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public QuoteForm getQuoteForm() {
    return quoteForm;
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

  public Date getPurchaseDate() {
    return purchaseDate;
  }
}
