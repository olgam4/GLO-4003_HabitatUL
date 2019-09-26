package ca.ulaval.glo4003.underwriting.persistence;

import ca.ulaval.glo4003.generator.PremiumGenerator;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.PremiumCalculatorIT;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyPremiumCalculator;

public class DummyPremiumCalculatorIT extends PremiumCalculatorIT {
  @Override
  public PremiumCalculator createSubject() {
    return new DummyPremiumCalculator(PremiumGenerator.create());
  }
}
