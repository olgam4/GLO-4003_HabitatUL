package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.helper.MoneyGenerator;

public class PremiumAdjustmentGenerator {
  private PremiumAdjustmentGenerator() {}

  public static PremiumAdjustment createPremiumAdjustment() {
    return new PremiumAdjustment() {
      @Override
      public Money apply(Money premium) {
        return MoneyGenerator.createMoney();
      }
    };
  }
}
