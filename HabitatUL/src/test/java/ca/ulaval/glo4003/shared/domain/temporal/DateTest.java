package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.helper.TemporalGenerator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Enclosed.class)
public class DateTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final LocalDate BEFORE_DATE_VALUE = LocalDate.now().minusDays(2);
  private static final Date BEFORE_DATE = Date.from(BEFORE_DATE_VALUE);
  private static final LocalDate AFTER_DATE_VALUE = LocalDate.now().plusDays(2);
  private static final Date AFTER_DATE = Date.from(AFTER_DATE_VALUE);

  private Date subject;

  @Before
  public void setUp() {
    subject = Date.now(CLOCK_PROVIDER.getClock());
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

  @Test
  public void gettingDateAtStartOfDayTime_shouldReturnDateAtStartOfDayTime() {
    DateTime value = subject.atStartOfDay();

    DateTime expectedValue = DateTime.from(subject.getValue().atStartOfDay());
    assertEquals(expectedValue, value);
  }

  @RunWith(Parameterized.class)
  public static class gettingEarliestDateBetweenTwoDates {
    private Date firstDate;
    private Date secondDate;
    private Date expectedDate;

    public gettingEarliestDateBetweenTwoDates(Date firstDate, Date secondDate, Date expectedDate) {
      this.firstDate = firstDate;
      this.secondDate = secondDate;
      this.expectedDate = expectedDate;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {BEFORE_DATE, AFTER_DATE, BEFORE_DATE},
            {AFTER_DATE, BEFORE_DATE, BEFORE_DATE}
          });
    }

    @Test
    public void gettingEarliestDateBetweenTwoDates_shouldReturnEarliestDate() {
      Date earliestDate = Date.earliest(firstDate, secondDate);

      assertEquals(expectedDate, earliestDate);
    }
  }

  @RunWith(Parameterized.class)
  public static class gettingLatestDateBetweenTwoDates {
    private Date firstDate;
    private Date secondDate;
    private Date expectedDate;

    public gettingLatestDateBetweenTwoDates(Date firstDate, Date secondDate, Date expectedDate) {
      this.firstDate = firstDate;
      this.secondDate = secondDate;
      this.expectedDate = expectedDate;
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {BEFORE_DATE, AFTER_DATE, AFTER_DATE},
            {AFTER_DATE, BEFORE_DATE, AFTER_DATE}
          });
    }

    @Test
    public void gettingLatestDateBetweenTwoDates_shouldReturnLatestDate() {
      Date latestDate = Date.latest(firstDate, secondDate);

      assertEquals(expectedDate, latestDate);
    }
  }
}
