package ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.input.GenderInput;

public interface RoommateAdjustmentProvider {
  PremiumAdjustment getAdjustment(GenderInput namedInsuredGender, GenderInput roommateGender);
}
