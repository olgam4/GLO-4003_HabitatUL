package ca.ulaval.glo4003.generator;

import ca.ulaval.glo4003.shared.domain.Money;
import com.github.javafaker.Faker;

import java.math.BigDecimal;

public class MoneyGenerator {
  public static Money create() {
    double randomDouble = Faker.instance().number().randomDouble(5, 0, 1000);
    return new Money(new BigDecimal(randomDouble));
  }
}
