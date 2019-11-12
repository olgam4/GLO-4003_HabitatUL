package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;

public class DummyGraduateStudentAdjustmentProvider implements GraduateStudentAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(String cycle) {
    return PremiumAdjustmentGenerator.create();
  }
}
