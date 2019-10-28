package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  @Override
  public QuotePriceAdjustment getAdjustment(String cycle, String degree, String program) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
