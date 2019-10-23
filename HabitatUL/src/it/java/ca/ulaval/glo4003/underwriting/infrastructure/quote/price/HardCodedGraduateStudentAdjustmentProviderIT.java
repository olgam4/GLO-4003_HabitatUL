package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProviderIT;

public class HardCodedGraduateStudentAdjustmentProviderIT
    extends GraduateStudentAdjustmentProviderIT {
  @Override
  public GraduateStudentAdjustmentProvider createSubject() {
    return new HardCodedGraduateStudentAdjustmentProvider();
  }
}
