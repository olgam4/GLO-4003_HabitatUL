package ca.ulaval.glo4003.coverage.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.identity.Gender;

public interface RoommateAdjustmentProvider {
  PremiumAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender);
}
