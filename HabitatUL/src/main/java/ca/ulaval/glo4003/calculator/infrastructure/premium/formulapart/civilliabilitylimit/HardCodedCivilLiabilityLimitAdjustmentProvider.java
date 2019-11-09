package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  private static final Map<CivilLiabilityLimitInput, Float> LOOKUP_MAP =
      new EnumMap<>(CivilLiabilityLimitInput.class);

  static {
    LOOKUP_MAP.put(CivilLiabilityLimitInput.TWO_MILLION, 0.25f);
  }

  @Override
  public PremiumAdjustment getAdjustment(CivilLiabilityLimitInput civilLiabilityLimit) {
    return Optional.ofNullable(LOOKUP_MAP.get(civilLiabilityLimit))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
