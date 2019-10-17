package ca.ulaval.glo4003.mediator;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EventTest {
  private static final String ATTR1 = Faker.instance().educator().campus();
  private static final String ATTR2 = Faker.instance().medical().diseaseName();
  private static final String ANOTHER_ATTR1 = Faker.instance().educator().university();
  private static final String ANOTHER_ATTR2 = Faker.instance().medical().hospitalName();

  private Event subject;

  @Before
  public void setUp() {
    subject = new DummyEvent(ATTR1, ATTR2);
  }

  @Test
  public void events_shouldBeValueComparable() {
    assertEquals(subject, new DummyEvent(ATTR1, ATTR2));
    assertNotEquals(subject, new DummyEvent(ANOTHER_ATTR1, ANOTHER_ATTR2));
  }

  class DummyEvent extends Event {
    private String attr1;
    private String attr2;

    DummyEvent(String attr1, String attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }
}
