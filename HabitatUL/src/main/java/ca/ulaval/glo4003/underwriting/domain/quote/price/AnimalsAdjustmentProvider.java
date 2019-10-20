package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public interface AnimalsAdjustmentProvider {
  QuotePriceAdjustment getAdjustment(AnimalBreed breed, Integer count);
}
