package ca.ulaval.glo4003.domain.commons;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

public class ValueObjectTest {
  private static final String A_STRING = "A_STRING";
  private static final Integer AN_INTEGER = 383;
  private static final String ANOTHER_STRING = "ANOTHER_STRING";
  private static final Date A_DATE = new Date();
  public static final int ANOTHER_INTEGER = 2;

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

  @Test
  public void comparingValueObjects_whenObjectsOfDifferentClass_thenThrows() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);

    Executable executable = () -> valueObject1.compareTo(valueObject2);

    assertThrows(ClassCastException.class, executable);
  }

  @Test
  public void
      comparingValueObjects_whenObjectsOfSameClassWithDifferentAttributes_thenOrderBasedOnAttributes() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, ANOTHER_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(ANOTHER_STRING, AN_INTEGER);

    assertTrue(valueObject1.compareTo(valueObject2) > 0);
  }

  @Test
  public void
      comparingValueObjects_whenObjectsOfSameClassButDifferentAttributes_thenOrderBasedOnAllAttributes() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, ANOTHER_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, AN_INTEGER);

    assertTrue(valueObject1.compareTo(valueObject2) < 0);
  }

  @Test
  public void comparingValueObjects_whenObjectsOfSameClassAndAttributes_thenObjectsHaveSameOrder() {
    ValueObject valueObject1 = new FirstValueObject(A_STRING, AN_INTEGER);
    ValueObject valueObject2 = new FirstValueObject(A_STRING, AN_INTEGER);

    assertTrue(valueObject1.compareTo(valueObject2) == 0);
  }

  @Test
  public void comparingValueObjects_whenNestedObjectsWithDifferentAttributes_thenOrderBasedOnAttributes() {
    SecondValueObject attr2 = new SecondValueObject(A_STRING, ANOTHER_STRING, A_DATE);
    ValueObject valueObject1 = new NestedValueObject(new FirstValueObject(A_STRING, AN_INTEGER), attr2);
    ValueObject valueObject2 = new NestedValueObject(new FirstValueObject(ANOTHER_STRING, ANOTHER_INTEGER), attr2);

    assertTrue(valueObject1.compareTo(valueObject2) > 0);
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
    assertTrue(observed.contains(AN_INTEGER.toString()));
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
