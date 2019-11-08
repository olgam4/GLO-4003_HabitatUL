package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedGraduateStudentAdjustmentProvider
    implements GraduateStudentAdjustmentProvider {
  private static final Map<String, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put("2e", -0.1273f);
    LOOKUP_MAP.put("3e", -0.1273f);
  }

  @Override
  public PremiumAdjustment getAdjustment(String cycle) {
    return Optional.ofNullable(LOOKUP_MAP.get(cycle))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
