package ca.ulaval.glo4003.shared.domain;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Period;

import static org.junit.Assert.*;

public class DateTest {
  private static final LocalDate BEFORE_DATE_VALUE = LocalDate.now().minusDays(2);
  private static final Date BEFORE_DATE = Date.from(BEFORE_DATE_VALUE);
  private static final LocalDate AFTER_DATE_VALUE = LocalDate.now().plusDays(2);
  private static final Date AFTER_DATE = Date.from(AFTER_DATE_VALUE);

  private Date subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    clockProvider = new FixedClockProvider();
    subject = Date.now(clockProvider.getClock());
  }

  @Test
  public void checkingIfDateIsBeforeAnother_withDateBefore_shouldBeAfter() {
    assertFalse(subject.isBefore(BEFORE_DATE));
  }

  @Test
  public void checkingIfDateIsBeforeAnother_withDateAfter_shouldBeBefore() {
    assertTrue(subject.isBefore(AFTER_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateBefore_shouldBeAfter() {
    assertTrue(subject.isAfter(BEFORE_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateAfter_shouldBeBefore() {
    assertFalse(subject.isAfter(AFTER_DATE));
  }

  @Test
  public void addingDates_shouldAdd() {
    subject = BEFORE_DATE;
    Period period = Period.ofDays(Faker.instance().number().randomDigit());

    Date observed = subject.plus(period);

    Date expected = Date.from(BEFORE_DATE_VALUE.plus(period));
    assertEquals(expected, observed);
  }

  @Test
  public void subtractingDates_shouldSubtract() {
    subject = AFTER_DATE;
    Period period = Period.ofDays(Faker.instance().number().randomDigit());

    Date observed = subject.minus(period);

    Date expected = Date.from(AFTER_DATE_VALUE.minus(period));
    assertEquals(expected, observed);
  }
}
