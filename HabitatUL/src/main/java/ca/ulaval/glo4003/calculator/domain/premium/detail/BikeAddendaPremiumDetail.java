package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.money.Money;

public class BikeAddendaPremiumDetail extends PremiumDetail {
  private static final String COVERAGE_NAME = "bike addenda";

  public BikeAddendaPremiumDetail(Money premium) {
    super(COVERAGE_NAME, premium);
  }
}
