package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

public class DummyRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender) {
    return PremiumAdjustmentGenerator.create();
  }
}
