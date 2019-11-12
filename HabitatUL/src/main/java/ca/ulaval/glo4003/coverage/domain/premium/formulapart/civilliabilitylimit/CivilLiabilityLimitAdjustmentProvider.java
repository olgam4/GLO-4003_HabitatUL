package ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;

public interface CivilLiabilityLimitAdjustmentProvider {
  PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit);
}
