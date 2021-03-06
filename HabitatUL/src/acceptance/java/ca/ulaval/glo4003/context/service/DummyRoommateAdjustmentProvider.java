package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

import static ca.ulaval.glo4003.context.PremiumAdjustmentGenerator.createPremiumAdjustment;

public class DummyRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender) {
    return createPremiumAdjustment();
  }
}
