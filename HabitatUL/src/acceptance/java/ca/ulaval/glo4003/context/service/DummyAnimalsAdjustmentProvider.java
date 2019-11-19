package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;

import static ca.ulaval.glo4003.context.PremiumAdjustmentGenerator.createPremiumAdjustment;

public class DummyAnimalsAdjustmentProvider implements AnimalsAdjustmentProvider {
  @Override
  public PremiumAdjustment getAdjustment(AnimalBreed breed, Integer count) {
    return createPremiumAdjustment();
  }
}
