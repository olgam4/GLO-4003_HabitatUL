package ca.ulaval.glo4003.underwriting.persistence;

import ca.ulaval.glo4003.generator.premium.PremiumGenerator;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculator;
import ca.ulaval.glo4003.underwriting.domain.QuotePremiumCalculatorIT;
import ca.ulaval.glo4003.underwriting.infrastructure.DummyQuotePremiumCalculator;

public class DummyQuotePremiumCalculatorIT extends QuotePremiumCalculatorIT {
  @Override
  public QuotePremiumCalculator createSubject() {
    return new DummyQuotePremiumCalculator(PremiumGenerator.create());
  }
}
