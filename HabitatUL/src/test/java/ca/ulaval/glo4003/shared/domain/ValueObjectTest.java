package ca.ulaval.glo4003.shared.domain;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ValueObjectTest {
  private ValueObject subject;

  @Before
  public void setUp() {
    subject = new DummyValueObject();
  }

  @Test
  public void valueObjects_shouldBeValueComparable() {
    assertTrue(subject instanceof ValueComparableObject);
  }

  class DummyValueObject extends ValueObject {}
}
