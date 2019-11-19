package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram.PreferentialProgramAdjustmentProvider;

import static ca.ulaval.glo4003.context.PremiumAdjustmentGenerator.createPremiumAdjustment;

public class DummyPreferentialProgramAdjustmentProvider
    implements PreferentialProgramAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(String cycle, String degree, String major) {
    return createPremiumAdjustment();
  }
}
