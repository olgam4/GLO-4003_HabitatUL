package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.GenderInput;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  private static final Map<Map.Entry<GenderInput, GenderInput>, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put(new AbstractMap.SimpleEntry<>(GenderInput.MALE, GenderInput.MALE), 0.1f);
  }

  @Override
  public PremiumAdjustment getAdjustment(
      GenderInput namedInsuredGender, GenderInput roommateGender) {
    AbstractMap.SimpleEntry<GenderInput, GenderInput> key =
        new AbstractMap.SimpleEntry<>(namedInsuredGender, roommateGender);
    return Optional.ofNullable(LOOKUP_MAP.get(key))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
