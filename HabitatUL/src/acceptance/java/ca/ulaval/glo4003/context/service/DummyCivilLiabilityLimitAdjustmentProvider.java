package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;

public class DummyCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit) {
    return PremiumAdjustmentGenerator.create();
  }
}
