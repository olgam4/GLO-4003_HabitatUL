package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.underwriting.domain.quote.QuoteEffectivePeriodProvider;

import java.time.Period;

import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createJavaTimePeriod;

public class DummyQuoteEffectivePeriodProvider implements QuoteEffectivePeriodProvider {
  @Override
  public Period getQuoteEffectivePeriod() {
    return createJavaTimePeriod();
  }
}
