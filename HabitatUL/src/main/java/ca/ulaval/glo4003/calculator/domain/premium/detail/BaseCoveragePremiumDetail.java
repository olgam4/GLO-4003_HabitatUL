package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class BaseCoveragePremiumDetail extends PremiumDetail {
  private static final String COVERAGE_NAME = "base coverage";

  public BaseCoveragePremiumDetail(Money premium) {
    super(COVERAGE_NAME, premium);
  }
}
