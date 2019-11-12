package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;

public interface AnimalsAdjustmentProvider {
  PremiumAdjustment getAdjustment(AnimalBreed breed, Integer count);
}
