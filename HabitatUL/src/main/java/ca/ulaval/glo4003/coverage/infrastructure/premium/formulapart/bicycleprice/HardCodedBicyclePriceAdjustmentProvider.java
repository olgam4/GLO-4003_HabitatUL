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
  static final int MAXIMUM_BICYCLE_PRICE_VALUE_BASE_COVERAGE = 1500;
  static final Amount MAXIMUM_BICYCLE_PRICE_BASE_COVERAGE =
      new Amount(BigDecimal.valueOf(MAXIMUM_BICYCLE_PRICE_VALUE_BASE_COVERAGE));
  private static final Amount MINIMUM_BICYCLE_PRICE_BEFORE_SURCHARGE =
      MAXIMUM_BICYCLE_PRICE_BASE_COVERAGE.add(Amount.ONE);
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
