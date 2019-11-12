package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.bicycleprice;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.bicycleprice.BicyclePriceAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class HardCodedBicyclePriceAdjustmentProvider implements BicyclePriceAdjustmentProvider {
  static final int MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE = 1500;
  private static final Amount MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE =
      new Amount(BigDecimal.valueOf(MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE_VALUE));
  private static final NavigableMap<Amount, Float> LOOKUP_MAP = new TreeMap<>();

  static {
    LOOKUP_MAP.put(MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE, 0.01f);
  }

  @Override
  public PremiumAdjustment getAdjustment(Amount bicyclePrice) {
    if (bicyclePrice == null) return new NullPremiumAdjustment();

    return Optional.ofNullable(LOOKUP_MAP.floorEntry(bicyclePrice))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x.getValue()))
        .orElse(new NullPremiumAdjustment());
  }
}
