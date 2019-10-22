package ca.ulaval.glo4003.helper;

import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.money.Money;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class MoneyGenerator {
  public static Money create() {
    Amount amount = createAmount();
    return new Money(amount);
  }

  public static Amount createAmount() {
    double randomDouble = Faker.instance().number().randomDouble(5, 0, 1000);
    return new Amount(new BigDecimal(randomDouble));
  }
}
