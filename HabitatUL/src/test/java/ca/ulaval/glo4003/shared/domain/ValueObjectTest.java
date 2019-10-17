package ca.ulaval.glo4003.shared.domain;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ValueObjectTest {
  private static final String ATTR1 = Faker.instance().educator().campus();
  private static final String ATTR2 = Faker.instance().medical().diseaseName();
  private static final String ANOTHER_ATTR1 = Faker.instance().educator().university();
  private static final String ANOTHER_ATTR2 = Faker.instance().medical().hospitalName();

  private ValueObject subject;

  @Before
  public void setUp() {
    subject = new DummyValueObject(ATTR1, ATTR2);
  }

  @Test
  public void valueObjects_shouldBeValueComparable() {
    assertEquals(subject, new DummyValueObject(ATTR1, ATTR2));
    assertNotEquals(subject, new DummyValueObject(ANOTHER_ATTR1, ANOTHER_ATTR2));
  }

  class DummyValueObject extends ValueObject {
    private String attr1;
    private String attr2;

    DummyValueObject(String attr1, String attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }
}
