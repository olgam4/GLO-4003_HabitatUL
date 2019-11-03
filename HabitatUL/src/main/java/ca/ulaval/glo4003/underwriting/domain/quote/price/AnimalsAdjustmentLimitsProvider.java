package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public interface AnimalsAdjustmentLimitsProvider {
  QuotePriceAdjustment getMinimumAdjustment(Money basePrice);

  QuotePriceAdjustment getMaximumAdjustment(Money basePrice);
}
