package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  @Override
  public QuotePriceAdjustment getMinimumAdjustment(Money basePrice) {
    return QuotePriceAdjustmentGenerator.create();
  }

  @Override
  public QuotePriceAdjustment getMaximumAdjustment(Money basePrice) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
