package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.input.AnimalBreed;

public interface AnimalsAdjustmentProvider {
  PremiumAdjustment getAdjustment(AnimalBreed breed, Integer count);
}
