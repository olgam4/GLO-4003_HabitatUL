package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.CivilLiabilityLimitAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyCivilLiabilityLimitAdjustmentProvider
    implements CivilLiabilityLimitAdjustmentProvider {
  @Override
  public QuotePriceAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
