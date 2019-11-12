package ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;

public interface AnimalsAdjustmentProvider {
  PremiumAdjustment getAdjustment(AnimalBreed breed, Integer count);
}
