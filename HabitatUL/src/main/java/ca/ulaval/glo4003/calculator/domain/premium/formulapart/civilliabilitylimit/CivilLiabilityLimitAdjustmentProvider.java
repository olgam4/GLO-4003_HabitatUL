package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;

public interface CivilLiabilityLimitAdjustmentProvider {
  PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit);
}
