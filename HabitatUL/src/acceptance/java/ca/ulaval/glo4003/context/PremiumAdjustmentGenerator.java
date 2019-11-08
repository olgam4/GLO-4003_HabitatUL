package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumAdjustmentGenerator {
  private PremiumAdjustmentGenerator() {}

  public static PremiumAdjustment create() {
    return new PremiumAdjustment() {
      @Override
      public Money apply(Money premium) {
        return MoneyGenerator.createMoney();
      }
    };
  }
}
