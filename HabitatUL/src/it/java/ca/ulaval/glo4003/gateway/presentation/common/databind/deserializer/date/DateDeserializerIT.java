package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.date;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDateError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.shared.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class DateDeserializerIT {
  private static final String VALID_VALUE = "1980-01-01";

  @RunWith(Parameterized.class)
  public static class ValidTestCases extends ValidTestCasesCustomDeserializerIT {
    public ValidTestCases(Object value) {
      super(value);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection<Object> parameters() {
      return Arrays.asList(VALID_VALUE);
    }

    @Override
    public Object createDeserializationResource() {
      return new DateDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCases extends InvalidTestCasesCustomDeserializerIT {
    public InvalidTestCases(Object value) {
      super(value);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection<Object> parameters() {
      return Arrays.asList(
          "",
          Faker.instance().number().randomDigit(),
          "INVALID",
          "2019-10-25T15:50:39+00:00",
          new JSONObject().put("test", VALID_VALUE),
          new JSONArray().put(VALID_VALUE));
    }

    @Override
    public Object createDeserializationResource() {
      return new DateDeserializationResource();
    }

    @Override
    protected Class<?> getDeserializationErrorCause() {
      return InvalidDateError.class;
    }
  }
}
