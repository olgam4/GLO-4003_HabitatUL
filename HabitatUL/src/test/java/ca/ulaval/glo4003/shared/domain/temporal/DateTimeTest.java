package ca.ulaval.glo4003.shared.domain.temporal;

import ca.ulaval.glo4003.shared.helper.TemporalGenerator;
import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.Assert.*;

public class DateTimeTest {
  private static final ClockProvider CLOCK_PROVIDER = TemporalGenerator.getClockProvider();
  private static final LocalDateTime BEFORE_DATETIME_VALUE =
      LocalDateTime.now(ZoneOffset.UTC).minusHours(1);
  private static final DateTime BEFORE_DATETIME = DateTime.from(BEFORE_DATETIME_VALUE);
  private static final LocalDateTime AFTER_DATETIME_VALUE =
      LocalDateTime.now(ZoneOffset.UTC).plusHours(1);
  private static final DateTime AFTER_DATETIME = DateTime.from(AFTER_DATETIME_VALUE);

  private DateTime subject;

  @Before
  public void setUp() {
    subject = DateTime.now(CLOCK_PROVIDER.getClock());
  }

  @Test
  public void checkingIfDateTimeIsBeforeAnother_withDateTimeBefore_shouldBeAfter() {
    assertTrue(subject.isAfter(BEFORE_DATETIME));
  }

  @Test
  public void checkingIfDateTimeIsBeforeAnother_withDateTimeAfter_shouldBeBefore() {
    assertFalse(subject.isAfter(AFTER_DATETIME));
  }

  @Test
  public void checkingIfDateTimeIsAfterAnother_withDateTimeBefore_shouldBeAfter() {
    assertTrue(subject.isAfter(BEFORE_DATETIME));
  }

  @Test
  public void checkingIfDateTimeIsAfterAnother_withDateTimeAfter_shouldBeBefore() {
    assertFalse(subject.isAfter(AFTER_DATETIME));
  }

  @Test
  public void addingDateTimes_shouldAdd() {
    subject = BEFORE_DATETIME;
    Duration duration = Duration.ofHours(Faker.instance().number().randomDigit());

    DateTime observed = subject.plus(duration);

    DateTime expected = DateTime.from(BEFORE_DATETIME_VALUE.plus(duration));
    assertEquals(expected, observed);
  }
}
