package ca.ulaval.glo4003.helper;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class MoneyGenerator {
  private static final int MAX_NUMBER_OF_DECIMALS = 5;
  private static final int MIN_VALUE = 0;
  private static final int MAX_VALUE = 1000;

  public static Money create() {
    Amount amount = createAmount();
    return new Money(amount);
  }

  public static Money createMaxValue() {
    return new Money(new Amount(BigDecimal.valueOf(Long.MAX_VALUE)));
  }

  public static Amount createAmount() {
    double randomDouble =
        Faker.instance().number().randomDouble(MAX_NUMBER_OF_DECIMALS, MIN_VALUE, MAX_VALUE);
    return new Amount(new BigDecimal(randomDouble));
  }
}
