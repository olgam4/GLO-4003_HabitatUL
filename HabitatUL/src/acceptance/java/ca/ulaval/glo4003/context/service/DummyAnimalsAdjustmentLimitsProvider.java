package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  @Override
  public QuotePriceAdjustment getMin() {
    return QuotePriceAdjustmentGenerator.create();
  }

  @Override
  public QuotePriceAdjustment getMax() {
    return QuotePriceAdjustmentGenerator.create();
  }
}
