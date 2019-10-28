package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  @Override
  public QuotePriceAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
