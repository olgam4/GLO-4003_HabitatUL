package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  private static final Map<Map.Entry<Gender, Gender>, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put(new AbstractMap.SimpleEntry<>(Gender.MALE, Gender.MALE), 0.1f);
  }

  @Override
  public PremiumAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender) {
    AbstractMap.SimpleEntry<Gender, Gender> key =
        new AbstractMap.SimpleEntry<>(namedInsuredGender, roommateGender);
    return Optional.ofNullable(LOOKUP_MAP.get(key))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
