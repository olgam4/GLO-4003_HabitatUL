package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public interface CivilLiabilityLimitAdjustmentProvider {
  QuotePriceAdjustment getAdjustment(CivilLiabilityLimit civilLiabilityLimit);
}
