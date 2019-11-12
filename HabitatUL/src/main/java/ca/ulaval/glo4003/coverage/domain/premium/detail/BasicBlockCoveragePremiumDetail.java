package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.coverage.domain.CoverageCategory;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class BasicBlockCoveragePremiumDetail extends PremiumDetail {
  public BasicBlockCoveragePremiumDetail(Money premium) {
    super(CoverageCategory.BASIC_BLOCK, premium);
  }
}
