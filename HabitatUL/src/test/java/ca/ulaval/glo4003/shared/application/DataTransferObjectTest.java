package ca.ulaval.glo4003.shared.application;

import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class DataTransferObjectTest {
  private DataTransferObject subject;

  @Before
  public void setUp() {
    subject = new DummyDataTransferObject();
  }

  @Test
  public void dataTransferObjects_shouldBeValueComparable() {
    assertTrue(subject instanceof ValueComparableObject);
  }

  class DummyDataTransferObject extends DataTransferObject {}
}
