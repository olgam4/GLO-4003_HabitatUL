package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyAnimalsAdjustmentProvider implements AnimalsAdjustmentProvider {
  @Override
  public QuotePriceAdjustment getAdjustment(AnimalBreed breed, Integer count) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
