package ca.ulaval.glo4003.shared.application;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DataTransferObjectTest {
  private static final String ATTR1 = Faker.instance().educator().campus();
  private static final String ATTR2 = Faker.instance().medical().diseaseName();
  private static final String ANOTHER_ATTR1 = Faker.instance().educator().university();
  private static final String ANOTHER_ATTR2 = Faker.instance().medical().hospitalName();

  private DataTransferObject subject;

  @Before
  public void setUp() {
    subject = new DummyDataTransferObject(ATTR1, ATTR2);
  }

  @Test
  public void dataTransferObjects_shouldBeValueComparable() {
    assertEquals(subject, new DummyDataTransferObject(ATTR1, ATTR2));
    assertNotEquals(subject, new DummyDataTransferObject(ANOTHER_ATTR1, ANOTHER_ATTR2));
  }

  class DummyDataTransferObject extends DataTransferObject {
    private final String attr1;
    private final String attr2;

    DummyDataTransferObject(String attr1, String attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }
}
