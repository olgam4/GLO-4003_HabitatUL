package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.input.GenderInput;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(
      GenderInput namedInsuredGender, GenderInput roommateGender) {
    return PremiumAdjustmentGenerator.create();
  }
}
