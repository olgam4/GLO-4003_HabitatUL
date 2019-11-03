package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MaximumQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MinimumQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class HardCodedAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  static final float MINIMUM_ADJUSTMENT_FACTOR = -0.1f;
  static final float MAXIMUM_ADJUSTMENT_FACTOR = 1f;

  @Override
  public QuotePriceAdjustment getMinimumAdjustment(Money basePrice) {
    return new MinimumQuotePriceAdjustment(computeAdjustment(basePrice, MINIMUM_ADJUSTMENT_FACTOR));
  }

  @Override
  public QuotePriceAdjustment getMaximumAdjustment(Money basePrice) {
    return new MaximumQuotePriceAdjustment(computeAdjustment(basePrice, MAXIMUM_ADJUSTMENT_FACTOR));
  }

  private Money computeAdjustment(Money basePrice, float factor) {
    return basePrice.multiply(factor);
  }
}
