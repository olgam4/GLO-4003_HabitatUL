package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(String cycle, String degree, String major) {
    return PremiumAdjustmentGenerator.create();
  }
}
