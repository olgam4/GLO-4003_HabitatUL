package ca.ulaval.glo4003.shared.domain;

import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

public class DateTest {
  private static final DateTime BEFORE_DATE = DateTime.from(LocalDateTime.now().minusDays(2));
  private static final DateTime AFTER_DATE = DateTime.from(LocalDateTime.now().plusDays(2));

  private DateTime subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    clockProvider = new FixedClockProvider();
    subject = DateTime.now(clockProvider.getClock());
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateAfter_shouldBeAfter() {
    assertTrue(subject.isAfter(BEFORE_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateBefore_shouldBeBefore() {
    assertFalse(subject.isAfter(AFTER_DATE));
  }

  @Test
  public void addingDates_shouldAdd() {
    subject = BEFORE_DATE;
    Duration duration = Duration.of(3, ChronoUnit.HOURS);

    DateTime observed = subject.plus(duration);

    DateTime expected = DateTime.from(BEFORE_DATE.getValue().plus(duration));
    assertEquals(expected, observed);
  }
}
