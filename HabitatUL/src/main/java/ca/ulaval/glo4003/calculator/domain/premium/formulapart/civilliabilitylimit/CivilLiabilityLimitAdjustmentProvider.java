package ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;

public interface CivilLiabilityLimitAdjustmentProvider {
  PremiumAdjustment getAdjustment(CivilLiabilityLimitInput civilLiabilityLimit);
}
