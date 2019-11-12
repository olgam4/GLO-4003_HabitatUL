package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

import java.time.Period;

public class DummyQuoteEffectivePeriodProvider implements QuoteEffectivePeriodProvider {
  @Override
  public Period getQuoteEffectivePeriod() {
    return TemporalGenerator.createJavaTimePeriod();
  }
}
