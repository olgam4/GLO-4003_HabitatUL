package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ca.ulaval.glo4003.shared.domain.identity.Cycle.SECOND_CYCLE;
import static ca.ulaval.glo4003.shared.domain.identity.Cycle.THIRD_CYCLE;

public class HardCodedGraduateStudentAdjustmentProvider
    implements GraduateStudentAdjustmentProvider {
  private static final Map<Cycle, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put(SECOND_CYCLE, -0.1273f);
    LOOKUP_MAP.put(THIRD_CYCLE, -0.1273f);
  }

  @Override
  public PremiumAdjustment getAdjustment(Cycle cycle) {
    return Optional.ofNullable(LOOKUP_MAP.get(cycle))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x))
        .orElse(new NullPremiumAdjustment());
  }
}
