package ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;

public interface GraduateStudentAdjustmentProvider {
  PremiumAdjustment getAdjustment(String cycle);
}
