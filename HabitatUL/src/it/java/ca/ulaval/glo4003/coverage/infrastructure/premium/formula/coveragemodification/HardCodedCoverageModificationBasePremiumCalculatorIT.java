package ca.ulaval.glo4003.coverage.infrastructure.premium.formula.coveragemodification;

import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculator;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationBasePremiumCalculatorIT;

public class HardCodedCoverageModificationBasePremiumCalculatorIT
    extends CoverageModificationBasePremiumCalculatorIT {
  @Override
  public CoverageModificationBasePremiumCalculator createSubject() {
    return new HardCodedCoverageModificationBasePremiumCalculator();
  }
}
