package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.graduatestudent;

import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.graduatestudent.GraduateStudentAdjustmentProviderIT;

public class HardCodedGraduateStudentAdjustmentProviderIT
    extends GraduateStudentAdjustmentProviderIT {
  @Override
  public GraduateStudentAdjustmentProvider createSubject() {
    return new HardCodedGraduateStudentAdjustmentProvider();
  }
}
