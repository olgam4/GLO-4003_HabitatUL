package ca.ulaval.glo4003.underwriting.domain.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public interface GraduateStudentAdjustmentProvider {
  QuotePriceAdjustment getAdjustment(String cycle);
}
