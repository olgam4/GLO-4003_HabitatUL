package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.context.PremiumAdjustmentGenerator;

public class DummyAnimalsAdjustmentProvider implements AnimalsAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(AnimalBreedInput breed, Integer count) {
    return PremiumAdjustmentGenerator.create();
  }
}
