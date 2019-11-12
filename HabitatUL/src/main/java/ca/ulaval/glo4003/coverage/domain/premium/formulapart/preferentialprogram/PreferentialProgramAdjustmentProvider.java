package ca.ulaval.glo4003.coverage.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;

public interface PreferentialProgramAdjustmentProvider {
  PremiumAdjustment getAdjustment(String cycle, String degree, String major);
}
