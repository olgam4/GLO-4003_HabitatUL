package ca.ulaval.glo4003.shared;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DateTest {
  private static final Date BEFORE_DATE = new Date(LocalDateTime.now().minusDays(2));
  private static final Date AFTER_DATE = new Date(LocalDateTime.now().plusDays(2));

  private Date subject;

  @Before
  public void setUp() {
    subject = Date.now();
  }

  @Test
  public void checkingIfDateIsAfterAnother_whenNullDate_isNotAfter() {
    subject = Date.nullDate();

    assertFalse(subject.isAfter(AFTER_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_whenDateIsAfter_isAfter() {
    assertTrue(subject.isAfter(BEFORE_DATE));
  }

  @Test
  public void checkingIfDateIsAfterAnother_whenDateIsBefore_isNotAfter() {
    assertFalse(subject.isAfter(AFTER_DATE));
  }
}
