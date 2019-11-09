package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.civilliabilitylimit.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(CivilLiabilityLimitInput civilLiabilityLimit) {
    return PremiumAdjustmentGenerator.create();
  }
}
