package ca.ulaval.glo4003.shared.domain.money;

import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.MoneyGenerator.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AmountTest {
  private static Amount VALUE = createAmount();
  private static Amount SMALLER_VALUE = createAmountSmallerThan(VALUE);
  private static Amount EVEN_SMALLER_VALUE = createAmountSmallerThan(SMALLER_VALUE);
  private static Amount SAME_VALUE = new Amount(VALUE.getValue());
  private static Amount GREATER_VALUE = createAmountGreaterThan(VALUE);
  private static Amount EVEN_GREATER_VALUE = createAmountGreaterThan(GREATER_VALUE);

  private Amount subject;

  @Before
  public void setUp() {
    subject = VALUE;
  }

  @Test
  public void checkingIfAmountIsSmallerThanAnother_withSmallerValue_shouldBeGreater() {
    assertFalse(subject.isSmallerThan(SMALLER_VALUE));
  }

  @Test
  public void checkingIfAmountIsSmallerThanAnother_withSameValue_shouldBeEqual() {
    assertFalse(subject.isSmallerThan(SAME_VALUE));
  }

  @Test
  public void checkingIfAmountIsSmallerThanAnother_withGreaterValue_shouldBeSmaller() {
    assertTrue(subject.isSmallerThan(GREATER_VALUE));
  }

  @Test
  public void checkingIfAmountIsGreaterThanAnother_withSmallerValue_shouldBeGreater() {
    assertTrue(subject.isGreaterThan(SMALLER_VALUE));
  }

  @Test
  public void checkingIfAmountIsGreaterThanAnother_withSameValue_shouldBeEqual() {
    assertFalse(subject.isGreaterThan(SAME_VALUE));
  }

  @Test
  public void checkingIfAmountIsGreaterThanAnother_withGreaterValue_shouldBeSmaller() {
    assertFalse(subject.isGreaterThan(GREATER_VALUE));
  }

  @Test
  public void checkingIfAmountIsBetweenTwoOthers_withValueBetween_shouldBeBetween() {
    assertTrue(subject.isBetween(SMALLER_VALUE, GREATER_VALUE));
  }

  @Test
  public void checkingIfAmountIsBetweenTwoOthers_withTooSmallValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(GREATER_VALUE, EVEN_GREATER_VALUE));
  }

  @Test
  public void checkingIfAmountIsBetweenTwoOthers_withLowerBoundValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(VALUE, GREATER_VALUE));
  }

  @Test
  public void checkingIfAmountIsBetweenTwoOthers_withUpperBoundValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(SMALLER_VALUE, VALUE));
  }

  @Test
  public void checkingIfAmountIsBetweenTwoOthers_withTooGreatValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(EVEN_SMALLER_VALUE, SMALLER_VALUE));
  }
}
