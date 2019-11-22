package ca.ulaval.glo4003.shared.application.concurrency;

import com.github.javafaker.Faker;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ConcurrentHashSetTest {
  private static final String VALUE = Faker.instance().internet().uuid();

  private ConcurrentHashSet<String> subject;

  @Before
  public void setUp() {
    subject = new ConcurrentHashSet<>();
  }

  @Test
  public void addingValue_withValueNotInSet_shouldBeFalse() {
    assertTrue(subject.add(VALUE));
  }

  @Test
  public void addingValue_withValueInSet_shouldBeTrue() {
    subject.add(VALUE);

    assertFalse(subject.add(VALUE));
  }

  @Test
  public void removingValue_withValueInSet_shouldBeTrue() {
    subject.add(VALUE);

    assertTrue(subject.remove(VALUE));
  }

  @Test
  public void removingValue_withValueNotInSet_shouldBeFalse() {
    assertFalse(subject.remove(VALUE));
  }
}
