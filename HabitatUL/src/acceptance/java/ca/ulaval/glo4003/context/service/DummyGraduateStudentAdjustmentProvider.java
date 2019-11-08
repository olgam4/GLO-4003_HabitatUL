package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyGraduateStudentAdjustmentProvider implements GraduateStudentAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(String cycle) {
    return PremiumAdjustmentGenerator.create();
  }
}
