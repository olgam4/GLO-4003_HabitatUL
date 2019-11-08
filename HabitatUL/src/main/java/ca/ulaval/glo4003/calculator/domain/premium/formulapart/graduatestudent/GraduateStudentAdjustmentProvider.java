package ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;

public interface GraduateStudentAdjustmentProvider {
  PremiumAdjustment getAdjustment(String cycle);
}
