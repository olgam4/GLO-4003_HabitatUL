package ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;

public interface GraduateStudentAdjustmentProvider {
  PremiumAdjustment getAdjustment(Cycle cycle);
}
