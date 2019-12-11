package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;

import static ca.ulaval.glo4003.context.PremiumAdjustmentGenerator.createPremiumAdjustment;

public class DummyGraduateStudentAdjustmentProvider implements GraduateStudentAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(Cycle cycle) {
    return createPremiumAdjustment();
  }
}
