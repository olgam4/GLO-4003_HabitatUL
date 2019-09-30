package ca.ulaval.glo4003.underwriting.persistence.premium;

import ca.ulaval.glo4003.generator.PremiumGenerator;
import ca.ulaval.glo4003.underwriting.domain.premium.PremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.premium.PremiumCalculatorIT;
import ca.ulaval.glo4003.underwriting.infrastructure.premium.DummyPremiumCalculator;

public class DummyPremiumCalculatorIT extends PremiumCalculatorIT {
  @Override
  public PremiumCalculator createSubject() {
    return new DummyPremiumCalculator(PremiumGenerator.create());
  }
}
