package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;

public class DummyPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(String cycle, String degree, String major) {
    return PremiumAdjustmentGenerator.create();
  }
}
