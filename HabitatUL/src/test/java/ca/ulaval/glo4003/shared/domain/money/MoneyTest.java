package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.generator.MoneyGenerator;
import com.github.javafaker.Faker;
import org.junit.Test;

import java.math.BigDecimal;

import static ca.ulaval.glo4003.shared.domain.money.Amount.DECIMAL_PRECISION;
import static ca.ulaval.glo4003.shared.domain.money.Amount.ROUNDING;
import static org.junit.Assert.assertEquals;

public class MoneyTest {
  private static final Money MONEY = MoneyGenerator.create();
  private static final BigDecimal MONEY_VALUE = MONEY.getAmount().getValue();
  private static final Money OTHER_MONEY = MoneyGenerator.create();
  private static final BigDecimal OTHER_MONEY_VALUE = OTHER_MONEY.getAmount().getValue();

  @Test
  public void addingMoney_shouldAdd() {
    Money money = MONEY.add(OTHER_MONEY);

    Money expected = format(MONEY_VALUE.add(OTHER_MONEY_VALUE));
    assertEquals(expected, money);
  }

  @Test
  public void multiplyingMoney_shouldMultiply() {
    double factor = Faker.instance().number().randomDouble(5, 0, 1000);

    Money money = MONEY.multiply(factor);

    Money expected = format(MONEY_VALUE.multiply(new BigDecimal(factor)));
    assertEquals(expected, money);
  }

  private Money format(BigDecimal value) {
    return new Money(new Amount(value.setScale(DECIMAL_PRECISION, ROUNDING)));
  }
}
