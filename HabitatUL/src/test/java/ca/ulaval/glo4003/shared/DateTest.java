package ca.ulaval.glo4003.shared;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateTest {
  private static final Date NULL_DATE = Date.nullDate();
  private static final Date BEFORE_DATE = Date.from(LocalDateTime.now().minusDays(2));
  private static final Date AFTER_DATE = Date.from(LocalDateTime.now().plusDays(2));

  private Date subject;
  private ClockProvider clockProvider;

  @Before
  public void setUp() {
    clockProvider = new FixedClockProvider();
    subject = Date.now(clockProvider.getClock());
  }

  @Test
  public void checkingIfDateIsAfterAnother_onNullDate_shouldBeBefore() {
    subject = Date.nullDate();

    assertFalse(subject.isAfter(AFTER_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withNullDate_shouldBeBefore() {
    assertFalse(subject.isAfter(NULL_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateAfter_shouldBeAfter() {
    assertTrue(subject.isAfter(BEFORE_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_withDateBefore_shouldBeBefore() {
    assertFalse(subject.isAfter(AFTER_DATE));
  }
}
