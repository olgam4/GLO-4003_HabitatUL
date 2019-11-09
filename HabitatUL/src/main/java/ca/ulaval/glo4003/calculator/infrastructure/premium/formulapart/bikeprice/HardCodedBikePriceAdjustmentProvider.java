package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.bikeprice;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.bikeprice.BikePriceAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.TreeMap;

public class HardCodedBikePriceAdjustmentProvider implements BikePriceAdjustmentProvider {
  static final int MAXIMUM_BIKE_PRICE_VALUE_BASE_COVERAGE = 1500;
  static final Amount MAXIMUM_BIKE_PRICE_BASE_COVERAGE =
      new Amount(BigDecimal.valueOf(MAXIMUM_BIKE_PRICE_VALUE_BASE_COVERAGE));
  private static final Amount MINIMUM_BIKE_PRICE_BEFORE_SURCHARGE =
      MAXIMUM_BIKE_PRICE_BASE_COVERAGE.add(new Amount(BigDecimal.ONE));
  private static final NavigableMap<Amount, Float> LOOKUP_MAP = new TreeMap<>();

  static {
    LOOKUP_MAP.put(MINIMUM_BIKE_PRICE_BEFORE_SURCHARGE, 0.01f);
  }

  @Override
  public PremiumAdjustment getAdjustment(Amount bikePrice) {
    if (bikePrice == null) return new NullPremiumAdjustment();

    return Optional.ofNullable(LOOKUP_MAP.floorEntry(bikePrice))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x.getValue()))
        .orElse(new NullPremiumAdjustment());
  }
}
