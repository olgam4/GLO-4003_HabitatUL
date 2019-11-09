package ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;

public interface AnimalsAdjustmentProvider {
  PremiumAdjustment getAdjustment(AnimalBreedInput breed, Integer count);
}
