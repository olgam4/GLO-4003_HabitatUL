package ca.ulaval.glo4003.shared.domain;

import com.github.javafaker.Faker;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ValueComparableObjectTest {
  private static final String A_STRING = new Faker().internet().uuid();
  private static final String ANOTHER_STRING = new Faker().internet().uuid();
  private static final long AN_INTEGER = new Faker().number().randomNumber();
  private static final long ANOTHER_INTEGER = new Faker().number().randomNumber();
  private static final Date A_DATE = new Faker().date().birthday();

  @Test
  public void checkingForEquality_withValueComparableObjectsOfDifferentClasses_shouldBeDifferent() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);

    assertNotEquals(ValueComparableObject1, ValueComparableObject2);
  }

  @Test
  public void
      checkingForEquality_withValueComparableObjectsOfSameClassButDifferentAttributes_shouldBeDifferent() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject(ANOTHER_STRING, AN_INTEGER);

    assertNotEquals(ValueComparableObject1, ValueComparableObject2);
  }

  @Test
  public void
      checkingForEquality_withValueComparableObjectsOfSameClassesAndAttributes_shouldBeEqual() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    assertEquals(ValueComparableObject1, ValueComparableObject2);
  }

  @Test
  public void
      checkingForEquality_withNestedValueComparableObjectWithDifferentAttributes_shouldBeDifferent() {
    SecondValueComparableObject attr2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueComparableObject ValueComparableObject1 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);
    ValueComparableObject ValueComparableObject2 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(ANOTHER_STRING, AN_INTEGER), attr2);

    assertNotEquals(ValueComparableObject1, ValueComparableObject2);
  }

  @Test
  public void
      checkingForEquality_withNestedValueComparableObjectWithSameAttributes_shouldBeEqual() {
    SecondValueComparableObject attr2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueComparableObject ValueComparableObject1 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);
    ValueComparableObject ValueComparableObject2 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);

    assertEquals(ValueComparableObject1, ValueComparableObject2);
  }

  @Test
  public void
      computingHashCode_withObjectsOfSameClassWithDifferentAttributes_shouldProduceDifferentHashCodes() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject(ANOTHER_STRING, AN_INTEGER);

    assertNotEquals(ValueComparableObject1.hashCode(), ValueComparableObject2.hashCode());
  }

  @Test
  public void computingHashCode_withObjectsOfSameClassAndAttributes_shouldProduceSameHashCode() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    assertEquals(ValueComparableObject1.hashCode(), ValueComparableObject2.hashCode());
  }

  @Test
  public void
      computingHashCode_withNestedObjectsWithDifferentAttributes_shouldProduceDifferentHashCodes() {
    SecondValueComparableObject attr2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueComparableObject ValueComparableObject1 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);
    ValueComparableObject ValueComparableObject2 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(ANOTHER_STRING, AN_INTEGER), attr2);

    assertNotEquals(ValueComparableObject1.hashCode(), ValueComparableObject2.hashCode());
  }

  @Test
  public void computingHashCode_withNestedObjectsWithSameAttributes_shouldProduceSameHashCode() {
    SecondValueComparableObject attr2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueComparableObject ValueComparableObject1 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);
    ValueComparableObject ValueComparableObject2 =
        new NestedValueComparableObject(
            new FirstValueComparableObject(A_STRING, AN_INTEGER), attr2);

    assertEquals(ValueComparableObject1.hashCode(), ValueComparableObject2.hashCode());
  }

  @Test(expected = ClassCastException.class)
  public void comparingValueComparableObjects_withObjectsOfDifferentClass_shouldThrow() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);

    ValueComparableObject1.compareTo(ValueComparableObject2);
  }

  @Test
  public void
      comparingValueComparableObjects_withObjectsOfSameClassWithDifferentAttributes_shouldOrderObjectsBasedOnAttributes() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject("A_STRING", AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject("B_STRING", ANOTHER_INTEGER);

    assertTrue(ValueComparableObject1.compareTo(ValueComparableObject2) < 0);
  }

  @Test
  public void
      comparingValueComparableObjects_withObjectsOfSameClassButDifferentAttributes_shouldOrderObjectsBasedOnAttributeOrder() {
    ValueComparableObject ValueComparableObject1 = new FirstValueComparableObject(A_STRING, 65l);
    ValueComparableObject ValueComparableObject2 = new FirstValueComparableObject(A_STRING, 56l);

    assertTrue(ValueComparableObject1.compareTo(ValueComparableObject2) > 0);
  }

  @Test
  public void
      comparingValueComparableObjects_withObjectsOfSameClassAndAttributes_shouldOrderObjectsEqually() {
    ValueComparableObject ValueComparableObject1 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);
    ValueComparableObject ValueComparableObject2 =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    assertTrue(ValueComparableObject1.compareTo(ValueComparableObject2) == 0);
  }

  @Test
  public void
      comparingValueComparableObjects_withNestedObjectsWithDifferentAttributes_shouldOrderObjectsBasedOnAttributes() {
    SecondValueComparableObject attr2 =
        new SecondValueComparableObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueComparableObject ValueComparableObject1 =
        new NestedValueComparableObject(
            new FirstValueComparableObject("A_STRING", AN_INTEGER), attr2);
    ValueComparableObject ValueComparableObject2 =
        new NestedValueComparableObject(
            new FirstValueComparableObject("B_STRING", ANOTHER_INTEGER), attr2);

    assertTrue(ValueComparableObject1.compareTo(ValueComparableObject2) < 0);
  }

  @Test
  public void stringifyingValueComparableObject_shouldShowClassName() {
    ValueComparableObject ValueComparableObject =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    String observed = ValueComparableObject.toString();

    assertTrue(observed.contains("FirstValueComparableObject"));
  }

  @Test
  public void stringifyingValueComparableObject_shouldShowAttributesName() {
    ValueComparableObject ValueComparableObject =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    String observed = ValueComparableObject.toString();

    assertTrue(observed.contains("attr1"));
    assertTrue(observed.contains("attr2"));
  }

  @Test
  public void stringifyingValueComparableObject_shouldShowAttributesValue() {
    ValueComparableObject ValueComparableObject =
        new FirstValueComparableObject(A_STRING, AN_INTEGER);

    String observed = ValueComparableObject.toString();

    assertTrue(observed.contains(A_STRING));
    assertTrue(observed.contains(String.format("%d", AN_INTEGER)));
  }

  class FirstValueComparableObject extends ValueComparableObject {
    private String attr1;
    private Long attr2;

    FirstValueComparableObject(String attr1, Long attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }

  class SecondValueComparableObject extends ValueComparableObject {
    private String attr1;
    private String attr2;
    private Date attr3;

    SecondValueComparableObject(String attr1, String attr2, Date attr3) {
      this.attr1 = attr1;
      this.attr2 = attr2;
      this.attr3 = attr3;
    }
  }

  class NestedValueComparableObject extends ValueComparableObject {
    private FirstValueComparableObject attr1;
    private SecondValueComparableObject attr2;

    NestedValueComparableObject(
        FirstValueComparableObject attr1, SecondValueComparableObject attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }
}
