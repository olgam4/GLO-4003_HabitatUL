package ca.ulaval.glo4003.calculator.domain.premium.detail;

import ca.ulaval.glo4003.calculator.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BasicBlockCoveragePremiumDetail extends PremiumDetail {
  public BasicBlockCoveragePremiumDetail(Money premium) {
    super(CoverageCategory.BASIC_BLOCK, premium);
  }
}
