package ca.ulaval.glo4003.shared.domain.money;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class AmountTest {
  private static BigDecimal SMALLER_VALUE =
      BigDecimal.valueOf(Faker.instance().number().numberBetween(1, 22));
  private static BigDecimal VALUE =
      BigDecimal.valueOf(Faker.instance().number().numberBetween(23, 70));
  private static BigDecimal BIGGER_VALUE =
      BigDecimal.valueOf(Faker.instance().number().numberBetween(71, 111));

  private Amount subject;

  @Before
  public void setUp() {
    subject = new Amount(VALUE);
  }

  @Test
  public void comparingAmount_withSmallerValue_shouldBeGreater() {
    assertTrue(subject.isGreaterThan(new Amount(SMALLER_VALUE)));
  }

  @Test
  public void comparingAmount_withGreaterValue_shouldBeSmaller() {
    assertFalse(subject.isGreaterThan(new Amount(BIGGER_VALUE)));
  }
}
