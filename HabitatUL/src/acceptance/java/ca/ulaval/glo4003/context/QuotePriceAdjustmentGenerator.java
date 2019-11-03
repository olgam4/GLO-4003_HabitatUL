package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

public class QuotePriceAdjustmentGenerator {
  public static QuotePriceAdjustment create() {
    return new QuotePriceAdjustment() {
      @Override
      public Money apply(Money price) {
        return MoneyGenerator.createMoney();
      }
    };
  }
}
