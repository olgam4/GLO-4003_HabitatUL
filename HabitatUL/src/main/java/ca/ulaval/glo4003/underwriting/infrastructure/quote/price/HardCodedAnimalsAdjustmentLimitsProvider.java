package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentLimitsProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class HardCodedAnimalsAdjustmentLimitsProvider implements AnimalsAdjustmentLimitsProvider {
  static final QuotePriceAdjustment MAXIMUM_ADJUSTMENT = new MultiplicativeQuotePriceAdjustment(1f);
  static final QuotePriceAdjustment MINIMUM_ADJUSTMENT =
      new MultiplicativeQuotePriceAdjustment(-0.1f);

  @Override
  public QuotePriceAdjustment getMin() {
    return MINIMUM_ADJUSTMENT;
  }

  @Override
  public QuotePriceAdjustment getMax() {
    return MAXIMUM_ADJUSTMENT;
  }
}
