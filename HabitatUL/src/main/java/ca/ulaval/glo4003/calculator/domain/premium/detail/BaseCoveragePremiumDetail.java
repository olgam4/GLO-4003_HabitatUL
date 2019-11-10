package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.Coverage;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BaseCoveragePremiumDetail extends PremiumDetail {
  public BaseCoveragePremiumDetail(Money premium) {
    super(Coverage.BASIC_BLOCK, premium);
  }
}
