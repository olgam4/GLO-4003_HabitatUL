package ca.ulaval.glo4003.underwriting.domain.quote;

import java.time.Duration;

public interface QuoteValidityPeriodProvider {
  Duration getQuoteValidityPeriod();
}
