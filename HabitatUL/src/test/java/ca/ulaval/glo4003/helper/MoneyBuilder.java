package ca.ulaval.glo4003.helper;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;

import java.math.BigDecimal;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmount;

public class MoneyBuilder {
  private static final Amount DEFAULT_AMOUNT = createAmount();

  private Amount amount = DEFAULT_AMOUNT;

  private MoneyBuilder() {}

  public static MoneyBuilder aMoney() {
    return new MoneyBuilder();
  }

  public MoneyBuilder withAmount(float amountValue) {
    withAmount(new Amount(BigDecimal.valueOf(amountValue)));
    return this;
  }

  public MoneyBuilder withSmallerAmountThan(Money money) {
    withAmount(money.subtract(MoneyGenerator.create()).getAmount());
    return this;
  }

  public MoneyBuilder withBiggerAmountThan(Money money) {
    withAmount(money.add(MoneyGenerator.create()).getAmount());
    return this;
  }

  public MoneyBuilder withAmount(Amount amount) {
    this.amount = amount;
    return this;
  }

  public Money build() {
    return new Money(amount);
  }
}
