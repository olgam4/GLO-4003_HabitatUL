package ca.ulaval.glo4003.shared.helper;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class MoneyGenerator {
  private static final int HUGE_VALUE = 1000000000;
  private static final int MAX_NUMBER_OF_DECIMALS = 5;

  private MoneyGenerator() {}

  public static Money createMoney() {
    Amount amount = createAmount();
    return new Money(amount);
  }

  public static Money createMoneySmallerThan(Money money) {
    Amount amount = createAmountSmallerThan(money.getAmount());
    return new Money(amount);
  }

  public static Money createMoneyGreaterThan(Money money) {
    Amount amount = createAmountGreaterThan(money.getAmount());
    return new Money(amount);
  }

  public static Money createMoneyGreaterThanZero() {
    return createMoneyGreaterThan(Money.ZERO);
  }

  public static Amount createAmount() {
    double randomDouble =
        Faker.instance().number().randomDouble(MAX_NUMBER_OF_DECIMALS, -HUGE_VALUE, HUGE_VALUE);
    return new Amount(new BigDecimal(randomDouble));
  }

  public static Amount createAmountSmallerThan(Amount amount) {
    double randomDouble =
        Faker.instance()
            .number()
            .randomDouble(MAX_NUMBER_OF_DECIMALS, -HUGE_VALUE, amount.getValue().intValue());
    return new Amount(new BigDecimal(randomDouble));
  }

  public static Amount createAmountGreaterThan(Amount amount) {
    double randomDouble =
        Faker.instance()
            .number()
            .randomDouble(MAX_NUMBER_OF_DECIMALS, amount.getValue().intValue() + 1, HUGE_VALUE);
    return new Amount(new BigDecimal(randomDouble));
  }

  public static Amount createAmountSmallerThanZero() {
    return createAmountSmallerThan(Amount.ZERO);
  }

  public static Amount createAmountGreaterThanZero() {
    return createAmountGreaterThan(Amount.ZERO);
  }

  public static Amount createAmountBetween(Amount min, Amount max) {
    double randomDouble =
        Faker.instance()
            .number()
            .randomDouble(
                MAX_NUMBER_OF_DECIMALS, min.getValue().intValue(), max.getValue().intValue());
    return new Amount(new BigDecimal(randomDouble));
  }
}
