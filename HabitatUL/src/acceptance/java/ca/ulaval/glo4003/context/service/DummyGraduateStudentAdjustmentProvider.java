package ca.ulaval.glo4003.context.service;

import ca.ulaval.glo4003.context.QuotePriceAdjustmentGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class DummyGraduateStudentAdjustmentProvider implements GraduateStudentAdjustmentProvider {
  @Override
  public QuotePriceAdjustment getAdjustment(String cycle) {
    return QuotePriceAdjustmentGenerator.create();
  }
}
