package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit) {
    return PremiumAdjustmentGenerator.create();
  }
}
