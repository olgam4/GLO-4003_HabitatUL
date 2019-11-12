package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.preventionsystems;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidPreventionSystemsError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createPreventionSystem;
import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class PreventionSystemsDeserializerIT {
  private static final String VALID_PREVENTION_SYSTEM = createPreventionSystem().toString();
  private static final String ANOTHER_VALID_PREVENTION_SYSTEM = createPreventionSystem().toString();
  private static final JSONArray VALID_VALUE =
      new JSONArray().put(VALID_PREVENTION_SYSTEM).put(ANOTHER_VALID_PREVENTION_SYSTEM);

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
      return new PreventionSystemsDeserializationResource();
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
            {VALID_PREVENTION_SYSTEM},
            {new JSONObject().put("test", VALID_VALUE)},
            {new JSONArray().put((Collection<?>) null)},
            {new JSONArray().put("")},
            {new JSONArray().put(Faker.instance().number().randomDigit())},
            {new JSONArray().put("INVALID")},
            {new JSONArray().put(new JSONObject().put("test", VALID_PREVENTION_SYSTEM))},
            {new JSONArray().put(VALID_VALUE)}
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new PreventionSystemsDeserializationResource();
    }

    @Override
    protected Class getDeserializationErrorCause() {
      return InvalidPreventionSystemsError.class;
    }
  }
}
