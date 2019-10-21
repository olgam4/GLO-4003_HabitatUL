package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public interface RoommateAdjustmentProvider {
  QuotePriceAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender);
}
