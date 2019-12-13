package ca.ulaval.glo4003.underwriting.domain.quote;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.mediator.AggregateRoot;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;

import static ca.ulaval.glo4003.underwriting.domain.quote.QuoteStatus.EXPIRED;
import static ca.ulaval.glo4003.underwriting.domain.quote.QuoteStatus.PURCHASED;

public class Quote extends AggregateRoot {
  private QuoteId quoteId;
  private QuoteStatus status;
  private QuoteForm quoteForm;
  private DateTime expirationDate;
  private Period effectivePeriod;
  private CoverageDetails coverageDetails;
  private PremiumDetails premiumDetails;
  private ClockProvider clockProvider;

  public Quote(
      QuoteId quoteId,
      QuoteStatus status,
      QuoteForm quoteForm,
      DateTime expirationDate,
      Period effectivePeriod,
      CoverageDetails coverageDetails,
      PremiumDetails premiumDetails,
      ClockProvider clockProvider) {
    this.quoteId = quoteId;
    this.status = status;
    this.quoteForm = quoteForm;
    this.expirationDate = expirationDate;
    this.effectivePeriod = effectivePeriod;
    this.coverageDetails = coverageDetails;
    this.premiumDetails = premiumDetails;
    this.clockProvider = clockProvider;
  }

  public QuoteId getQuoteId() {
    return quoteId;
  }

  public QuoteStatus getStatus() {
    return status;
  }

  public QuoteForm getQuoteForm() {
    return quoteForm;
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

  public void purchase() {
    checkIfAlreadyPurchased();
    checkIfExpired();
    status = PURCHASED;
    registerQuotePurchaseEvent();
  }

  private void checkIfAlreadyPurchased() {
    if (status.equals(PURCHASED)) {
      throw new QuoteAlreadyPurchasedError(quoteId);
    }
  }

  private void checkIfExpired() {
    if (status.equals(EXPIRED)) {
      throw new QuoteExpiredError(quoteId);
    }
  }

  private void registerQuotePurchaseEvent() {
    Date now = Date.now(clockProvider.getClock());
    registerEvent(
        new QuotePurchasedEvent(
            quoteId, quoteForm, effectivePeriod, coverageDetails, premiumDetails, now));
  }
}
