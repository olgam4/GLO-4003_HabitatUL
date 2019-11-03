package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.helper.MoneyGenerator;
import com.github.javafaker.Faker;
import org.junit.Test;

import java.math.BigDecimal;

import static ca.ulaval.glo4003.shared.domain.money.Amount.DECIMAL_PRECISION;
import static ca.ulaval.glo4003.shared.domain.money.Amount.ROUNDING;
import static org.junit.Assert.assertEquals;

public class MoneyTest {
  private static final Money MONEY = MoneyGenerator.createMoney();
  private static final BigDecimal MONEY_VALUE = MONEY.getAmount().getValue();
  private static final Money OTHER_MONEY = MoneyGenerator.createMoney();
  private static final BigDecimal OTHER_MONEY_VALUE = OTHER_MONEY.getAmount().getValue();
  private static final double FACTOR = Faker.instance().number().randomDouble(5, 0, 1000);

  @Test
  public void addingMoney_shouldAdd() {
    Money money = MONEY.add(OTHER_MONEY);

    Money expected = format(MONEY_VALUE.add(OTHER_MONEY_VALUE));
    assertEquals(expected, money);
  }

  @Test
  public void subtractingMoney_shouldSubtract() {
    Money money = MONEY.subtract(OTHER_MONEY);

    Money expected = format(MONEY_VALUE.subtract(OTHER_MONEY_VALUE));
    assertEquals(expected, money);
  }

  @Test
  public void multiplyingMoney_shouldMultiply() {
    Money money = MONEY.multiply(FACTOR);

    Money expected = format(MONEY_VALUE.multiply(new BigDecimal(FACTOR)));
    assertEquals(expected, money);
  }

  @Test
  public void dividingMoney_shouldDivide() {
    Money money = MONEY.divide(FACTOR);

    Money expected = format(MONEY_VALUE.divide(new BigDecimal(FACTOR), ROUNDING));
    assertEquals(expected, money);
  }

  @Test
  public void gettingMinimum_shouldReturnMinimum() {
    Money first = new Money(new Amount(BigDecimal.valueOf(10)));
    Money second = new Money(new Amount(BigDecimal.valueOf(7)));

    Money minimum = Money.min(first, second);

    assertEquals(second, minimum);
  }

  @Test
  public void gettingMaximum_shouldReturnMaximum() {
    Money first = new Money(new Amount(BigDecimal.valueOf(10)));
    Money second = new Money(new Amount(BigDecimal.valueOf(7)));

    Money maximum = Money.max(first, second);

    assertEquals(first, maximum);
  }

  private Money format(BigDecimal value) {
    return new Money(new Amount(value.setScale(DECIMAL_PRECISION, ROUNDING)));
  }
}
