package ca.ulaval.glo4003.shared;

import com.github.javafaker.Faker;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class ValueObjectTest {
  private static final String A_STRING = new Faker().dragonBall().character();
  private static final String ANOTHER_STRING = new Faker().dragonBall().character();
  private static final int AN_INTEGER = new Faker().number().randomDigit();
  private static final int ANOTHER_INTEGER = new Faker().number().randomDigit();
  private static final Date A_DATE = new Faker().date().birthday();

  @Test
  public void checkingForEquality_whenValueObjectsOfDifferentClasses_thenObjectsAreDifferent() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);

    assertNotEquals(valueObject1, valueObject2);
  }

  @Test
  public void
      checkingForEquality_whenValueObjectsOfSameClassButDifferentAttributes_thenObjectsAreDifferent() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(ANOTHER_STRING, AN_INTEGER);

    assertNotEquals(valueObject1, valueObject2);
  }

  @Test
  public void checkingForEquality_whenValueObjectsOfSameClassesAndAttributes_thenObjectsAreEqual() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, AN_INTEGER);

    assertEquals(valueObject1, valueObject2);
  }

  @Test
  public void
      checkingForEquality_whenNestedValueObjectWithDifferentAttributes_thenObjectsAreDifferent() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);
    ValueObject valueObject2 =
        new NestedValueObject(new FirstValueObject(ANOTHER_STRING, AN_INTEGER), attr2);

    assertNotEquals(valueObject1, valueObject2);
  }

  @Test
  public void checkingForEquality_whenNestedValueObjectWithSameAttributes_thenObjectsAreEqual() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);
    ValueObject valueObject2 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);

    assertEquals(valueObject1, valueObject2);
  }

  @Test
  public void
      computingHashCode_whenObjectsOfSameClassWithDifferentAttributes_thenHashCodesAreDifferent() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(ANOTHER_STRING, AN_INTEGER);

    assertNotEquals(valueObject1.hashCode(), valueObject2.hashCode());
  }

  @Test
  public void computingHashCode_whenObjectsOfSameClassAndAttributes_thenHashCodesAreEqual() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, AN_INTEGER);

    assertEquals(valueObject1.hashCode(), valueObject2.hashCode());
  }

  @Test
  public void
      computingHashCode_whenNestedObjectsWithDifferentAttributes_thenHashCodesAreDifferent() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);
    ValueObject valueObject2 =
        new NestedValueObject(new FirstValueObject(ANOTHER_STRING, AN_INTEGER), attr2);

    assertNotEquals(valueObject1.hashCode(), valueObject2.hashCode());
  }

  @Test
  public void computingHashCode_whenNestedObjectsWithSameAttributes_thenHashCodesAreEqual() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);
    ValueObject valueObject2 =
        new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);

    assertEquals(valueObject1.hashCode(), valueObject2.hashCode());
  }

  @Test(expected = ClassCastException.class)
  public void comparingValueObjects_whenObjectsOfDifferentClass_thenThrows() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);

    valueObject1.compareTo(valueObject2);
  }

  @Test
  public void
      comparingValueObjects_whenObjectsOfSameClassWithDifferentAttributes_thenOrderBasedOnAttributes() {
    ValueObject valueObject1 = new FirstValueObject("A_STRING", AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject("B_STRING", ANOTHER_INTEGER);

    assertTrue(valueObject1.compareTo(valueObject2) < 0);
  }

  @Test
  public void
      comparingValueObjects_whenObjectsOfSameClassButDifferentAttributes_thenOrderBasedOnAttributeOrder() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, 65);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, 56);

    assertTrue(valueObject1.compareTo(valueObject2) > 0);
  }

  @Test
  public void comparingValueObjects_whenObjectsOfSameClassAndAttributes_thenObjectsHaveSameOrder() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, AN_INTEGER);

    assertTrue(valueObject1.compareTo(valueObject2) == 0);
  }

  @Test
  public void
      comparingValueObjects_whenNestedObjectsWithDifferentAttributes_thenOrderBasedOnAttributes() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 =
        new NestedValueObject(new FirstValueObject("A_STRING", AN_INTEGER), attr2);
    ValueObject valueObject2 =
        new NestedValueObject(new FirstValueObject("B_STRING", ANOTHER_INTEGER), attr2);

    assertTrue(valueObject1.compareTo(valueObject2) < 0);
  }

  @Test
  public void stringifyingValueObject_thenShowsClassName() {
    ValueObject valueObject = new FirstValueObject(A_STRING, AN_INTEGER);

    String observed = valueObject.toString();

    assertTrue(observed.contains("FirstValueObject"));
  }

  @Test
  public void stringifyingValueObject_thenShowsAttributesName() {
    ValueObject valueObject = new FirstValueObject(A_STRING, AN_INTEGER);

    String observed = valueObject.toString();

    assertTrue(observed.contains("attr1"));
    assertTrue(observed.contains("attr2"));
  }

  @Test
  public void stringifyingValueObject_thenShowsAttributesValue() {
    ValueObject valueObject = new FirstValueObject(A_STRING, AN_INTEGER);

    String observed = valueObject.toString();

    assertTrue(observed.contains(A_STRING));
    assertTrue(observed.contains(String.format("%d", AN_INTEGER)));
  }

  class FirstValueObject extends ValueObject {
    private String attr1;
    private Integer attr2;

    FirstValueObject(String attr1, Integer attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }

  class SecondValueObject extends ValueObject {
    private String attr1;
    private String attr2;
    private Date attr3;

    SecondValueObject(String attr1, String attr2, Date attr3) {
      this.attr1 = attr1;
      this.attr2 = attr2;
      this.attr3 = attr3;
    }
  }

  class NestedValueObject extends ValueObject {
    private FirstValueObject attr1;
    private SecondValueObject attr2;

    public NestedValueObject(FirstValueObject attr1, SecondValueObject attr2) {
      this.attr1 = attr1;
      this.attr2 = attr2;
    }
  }
}
