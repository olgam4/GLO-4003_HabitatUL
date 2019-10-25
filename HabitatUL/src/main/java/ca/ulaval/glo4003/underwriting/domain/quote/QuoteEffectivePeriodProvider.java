package ca.ulaval.glo4003.underwriting.domain.quote;

import java.time.Period;

public interface QuoteEffectivePeriodProvider {
  Period getQuoteEffectivePeriod();
}
