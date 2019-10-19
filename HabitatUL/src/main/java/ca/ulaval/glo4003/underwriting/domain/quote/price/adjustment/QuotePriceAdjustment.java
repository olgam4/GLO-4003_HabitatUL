package ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment;

import ca.ulaval.glo4003.shared.domain.money.Money;

public interface QuotePriceAdjustment {
  Money apply(Money price);
}
