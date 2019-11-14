package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.datetime;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidDateTimeError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.shared.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class DateTimeDeserializerIT {
  private static final String VALID_VALUE = "1987-07-19 19:56:32";

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
      return new DateTimeDeserializationResource();
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
          "2019-10-25",
          new JSONObject().put("test", VALID_VALUE),
          new JSONArray().put(VALID_VALUE));
    }

    @Override
    public Object createDeserializationResource() {
      return new DateTimeDeserializationResource();
    }

    @Override
    protected Class<?> getDeserializationErrorCause() {
      return InvalidDateTimeError.class;
    }
  }
}
