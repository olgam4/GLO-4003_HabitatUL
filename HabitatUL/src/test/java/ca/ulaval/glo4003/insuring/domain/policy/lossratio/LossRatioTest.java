package ca.ulaval.glo4003.insuring.domain.policy.lossratio;

import ca.ulaval.glo4003.helper.policy.LossRatioGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.policy.LossRatioGenerator.createLossRatioGreaterThan;
import static ca.ulaval.glo4003.helper.policy.LossRatioGenerator.createLossRatioSmallerThan;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LossRatioTest {
  private static final LossRatio VALUE = LossRatioGenerator.createLossRatio();
  private static final LossRatio SMALLER_VALUE = createLossRatioSmallerThan(VALUE);
  private static final LossRatio EVEN_SMALLER_VALUE = createLossRatioSmallerThan(SMALLER_VALUE);
  private static final LossRatio SAME_VALUE = new LossRatio(VALUE.getValue());
  private static final LossRatio GREATER_VALUE = createLossRatioGreaterThan(VALUE);
  private static final LossRatio EVEN_GREATER_VALUE = createLossRatioGreaterThan(GREATER_VALUE);

  private LossRatio subject;

  @Before
  public void setUp() {
    subject = VALUE;
  }

  @Test
  public void checkingIfLossRatioIsSmallerThan_withSmallerValue_shouldBeGreater() {
    assertFalse(subject.isSmallerThan(SMALLER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsSmallerThan_withSameValue_shouldBeEqual() {
    assertFalse(subject.isSmallerThan(SAME_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsSmallerThan_withGreaterValue_shouldBeSmaller() {
    assertTrue(subject.isSmallerThan(GREATER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsGreaterThan_withSmallerValue_shouldBeGreater() {
    assertTrue(subject.isGreaterThan(SMALLER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsGreaterThan_withSameValue_shouldBeEqual() {
    assertFalse(subject.isGreaterThan(SAME_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsGreaterThan_withGreaterValue_shouldBeSmaller() {
    assertFalse(subject.isGreaterThan(GREATER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsBetween_withValueBetween_shouldBeBetween() {
    assertTrue(subject.isBetween(SMALLER_VALUE, GREATER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsBetween_withTooSmallValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(GREATER_VALUE, EVEN_GREATER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsBetween_withLowerBoundValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(VALUE, GREATER_VALUE));
  }

  @Test
  public void checkingIfLossRatioIsBetween_withUpperBoundValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(SMALLER_VALUE, VALUE));
  }

  @Test
  public void checkingIfLossRatioIsBetween_withTooGreatValue_shouldNotBeBetween() {
    assertFalse(subject.isBetween(EVEN_SMALLER_VALUE, SMALLER_VALUE));
  }
}
