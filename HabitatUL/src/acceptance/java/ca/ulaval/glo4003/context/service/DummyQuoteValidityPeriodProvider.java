package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteValidityPeriodProvider;

import java.time.Duration;

public class DummyQuoteValidityPeriodProvider implements QuoteValidityPeriodProvider {
  @Override
  public Duration getQuoteValidityPeriod() {
    return Duration.ZERO;
  }
}
