package ca.ulaval.glo4003.coverage.domain.premium.detail;

import ca.ulaval.glo4003.shared.domain.money.Money;

import static ca.ulaval.glo4003.coverage.domain.premium.PremiumCategory.BASIC_BLOCK;

public class BasicBlockCoveragePremiumDetail extends PremiumDetail {
  public BasicBlockCoveragePremiumDetail(Money premium) {
    super(BASIC_BLOCK, premium);
  }
}
