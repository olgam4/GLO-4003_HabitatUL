package ca.ulaval.glo4003.shared.domain.temporal;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PeriodTest {
  private static final LocalDate NOW = LocalDate.now();
  private static final java.time.Period A_PERIOD = java.time.Period.ofDays(5);
  private static final Date START_DATE = Date.from(NOW.minus(A_PERIOD));
  private static final Date END_DATE = Date.from(NOW.plus(A_PERIOD));
  private static final Date WITHIN_RANGE_DATE = Date.from(NOW);
  private static final Date OUTSIDE_RANGE_DATE = Date.from(NOW.plus(A_PERIOD).plus(A_PERIOD));

  private Period subject;

  @Before
  public void setUp() {
    subject = new Period(START_DATE, END_DATE);
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateAfter_shouldNotBeWithin() {
    Date aDateAfter = END_DATE.plus(A_PERIOD);

    assertFalse(subject.isWithin(aDateAfter));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateBefore_shouldNotBeWithin() {
    Date aDateBefore = START_DATE.minus(A_PERIOD);

    assertFalse(subject.isWithin(aDateBefore));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withStartDate_shouldBeWithin() {
    assertTrue(subject.isWithin(START_DATE));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withEndDate_shouldBeWithin() {
    assertTrue(subject.isWithin(END_DATE));
  }

  @Test
  public void checkingIfDateIsWithinPeriod_withDateWithinPeriod_shouldBeWithin() {
    assertTrue(subject.isWithin(WITHIN_RANGE_DATE));
  }

  @Test
  public void creatingPeriod_withFirstDateBeforeSecondDate_shouldProperlyMapStartAndEndDates() {
    subject = new Period(END_DATE, START_DATE);

    assertTrue(subject.isWithin(WITHIN_RANGE_DATE));
    assertFalse(subject.isWithin(OUTSIDE_RANGE_DATE));
  }
}
