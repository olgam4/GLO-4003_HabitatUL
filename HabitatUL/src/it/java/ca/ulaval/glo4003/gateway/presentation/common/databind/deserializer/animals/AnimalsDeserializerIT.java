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
import java.util.List;

import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createAnimalBreed;

@RunWith(Enclosed.class)
public class AnimalsDeserializerIT {
  private static final String VALID_BREED = createAnimalBreed().toString();
  private static final int VALID_QUANTITY = Faker.instance().number().randomDigitNotZero();
  private static final List<JSONObject> VALID_VALUE =
      Arrays.asList(toJson(VALID_BREED, VALID_QUANTITY));

  private static JSONObject toJson(Object breed, Object quantity) {
    JSONObject animal = new JSONObject();
    animal.put("breed", breed);
    animal.put("quantity", quantity);
    return animal;
  }

  @RunWith(Parameterized.class)
  public static class ValidTestCasesAnimalsCustomDeserializerIT
      extends ValidTestCasesCustomDeserializerIT {

    private Object value;

    public ValidTestCasesAnimalsCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new AnimalsDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCasesAnimalsCustomDeserializerIT
      extends InvalidTestCasesCustomDeserializerIT {

    private Object value;

    public InvalidTestCasesAnimalsCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {Faker.instance().number().randomDigit()},
            {"INVALID"},
            {new JSONObject().put("test", VALID_VALUE).toString()},
            {new JSONArray().put(VALID_VALUE).toString()},
            {new JSONArray().put(toJson(null, VALID_QUANTITY))},
            {new JSONArray().put(toJson(123, VALID_QUANTITY))},
            {new JSONArray().put(toJson("", VALID_QUANTITY))},
            {new JSONArray().put(toJson(VALID_BREED, -1))},
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new AnimalsDeserializationResource();
    }

    @Override
    protected String getDeserializationErrorCause() {
      return InvalidAnimalsError.class.getName();
    }
  }
}