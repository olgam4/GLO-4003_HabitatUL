package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.animals;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAnimalsError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.calculator.premium.QuotePremiumInputGenerator.createAnimalBreed;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class AnimalsDeserializerIT {
  private static final String VALID_BREED = createAnimalBreed().toString();
  private static final int VALID_QUANTITY = Faker.instance().number().randomDigitNotZero();
  private static final JSONObject VALID_ANIMAL = toJson(VALID_BREED, VALID_QUANTITY);
  private static final String ANOTHER_VALID_BREED = createAnimalBreed().toString();
  private static final int ANOTHER_VALID_QUANTITY = Faker.instance().number().randomDigitNotZero();
  private static final JSONObject ANOTHER_VALID_ANIMAL =
      toJson(ANOTHER_VALID_BREED, ANOTHER_VALID_QUANTITY);
  private static final JSONArray VALID_VALUE =
      new JSONArray().put(VALID_ANIMAL).put(ANOTHER_VALID_ANIMAL);

  private static JSONObject toJson(Object breed, Object quantity) {
    JSONObject animal = new JSONObject();
    animal.put("breed", breed);
    animal.put("quantity", quantity);
    return animal;
  }

  @RunWith(Parameterized.class)
  public static class ValidTestCases extends ValidTestCasesCustomDeserializerIT {
    private Object value;

    public ValidTestCases(Object value) {
      super(value);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new AnimalsDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCases extends InvalidTestCasesCustomDeserializerIT {
    private Object value;

    public InvalidTestCases(Object value) {
      super(value);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {""},
            {Faker.instance().number().randomDigit()},
            {"INVALID"},
            {VALID_ANIMAL},
            {new JSONArray().put(new JSONObject().put("breed", VALID_BREED))},
            {new JSONArray().put(new JSONObject().put("quantity", VALID_QUANTITY))},
            {new JSONArray().put(toJson(null, VALID_QUANTITY))},
            {new JSONArray().put(toJson(VALID_BREED, null))},
            {new JSONArray().put(toJson("", VALID_QUANTITY))},
            {new JSONArray().put(toJson(Faker.instance().number().randomDigit(), VALID_QUANTITY))},
            {
              new JSONArray()
                  .put(toJson(VALID_BREED, -Faker.instance().number().randomDigitNotZero()))
            },
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new AnimalsDeserializationResource();
    }

    @Override
    protected Class getDeserializationErrorCause() {
      return InvalidAnimalsError.class;
    }
  }
}
