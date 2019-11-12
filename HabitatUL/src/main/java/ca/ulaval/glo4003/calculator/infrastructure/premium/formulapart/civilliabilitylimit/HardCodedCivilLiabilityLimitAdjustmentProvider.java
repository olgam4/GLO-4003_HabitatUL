package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  private static final Map<CivilLiabilityLimit, Float> LOOKUP_MAP =
      new EnumMap<>(CivilLiabilityLimit.class);

  static {
    LOOKUP_MAP.put(CivilLiabilityLimit.TWO_MILLION, 0.25f);
  }

  @Override
  public PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit) {
    return Optional.ofNullable(LOOKUP_MAP.get(civilLiabilityLimit))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
