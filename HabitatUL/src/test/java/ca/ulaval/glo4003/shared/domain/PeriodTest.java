package ca.ulaval.glo4003.shared.domain;

import ca.ulaval.glo4003.shared.domain.exceptions.PeriodInvalidDateException;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeriodTest {
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();
  private static final java.time.Period A_PERIOD = java.time.Period.ofDays(5);
  private static final Date START_DATE = Date.from(LocalDate.now().minus(A_PERIOD));
  private static final Date END_DATE = Date.from(LocalDate.now().plus(A_PERIOD));

  private Period subject;

  @Before
  public void setUp() {
    subject = new Period(START_DATE, END_DATE);
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateAfter_shouldNotBeWithin() {
    Date afterDate = END_DATE.plus(A_PERIOD);

    assertFalse(subject.isWithin(afterDate));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateBefore_shouldNotBeWithin() {
    Date afterDate = END_DATE.plus(A_PERIOD);

    assertFalse(subject.isWithin(afterDate));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateOnStartDate_shouldBeWithin() {
    assertTrue(subject.isWithin(START_DATE));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateOnEnd_shouldBeWithin() {
    assertTrue(subject.isWithin(END_DATE));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateInMiddle_shouldBeWithin() {
    Date date = Date.now(FIXED_CLOCK_PROVIDER.getClock());

    assertTrue(subject.isWithin(date));
  }

  @Test(expected = PeriodInvalidDateException.class)
  public void creatingPeriodWithStartDateSmallerThenEndDate_shouldThrow() {
    new Period(END_DATE, START_DATE);
  }
}
