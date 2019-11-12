package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.CivilLiabilityLimit;

public interface CivilLiabilityLimitAdjustmentProvider {
  PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit);
}
