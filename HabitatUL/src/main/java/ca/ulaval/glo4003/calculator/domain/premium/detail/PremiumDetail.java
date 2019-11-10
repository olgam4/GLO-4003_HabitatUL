package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumDetail extends ValueObject {
  private final String coverageName;
  private final Money premium;

  public PremiumDetail(String coverageName, Money premium) {
    this.coverageName = coverageName;
    this.premium = premium;
  }

  public String getCoverageName() {
    return coverageName;
  }

  public Money getPremium() {
    return premium;
  }
}
