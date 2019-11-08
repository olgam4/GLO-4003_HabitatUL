package ca.ulaval.glo4003.calculator.domain.premium.formulapart.preferentialprogram;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;

public interface PreferentialProgramAdjustmentProvider {
  PremiumAdjustment getAdjustment(String cycle, String degree, String program);
}
