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

@RunWith(Enclosed.class)
public class DateTimeDeserializerIT {
  private static final String VALID_VALUE = "1987-07-19 19:56:32";

  @RunWith(Parameterized.class)
  public static class ValidTestCasesDateCustomDeserializerIT
      extends ValidTestCasesCustomDeserializerIT {
    private Object value;

    public ValidTestCasesDateCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new DateTimeDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCasesDateCustomDeserializerIT
      extends InvalidTestCasesCustomDeserializerIT {
    private Object value;

    public InvalidTestCasesDateCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {""},
            {Faker.instance().number().randomDigit()},
            {"2019-10-25"},
            {new JSONObject().put("test", VALID_VALUE).toString()},
            {new JSONArray().put(VALID_VALUE).toString()}
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new DateTimeDeserializationResource();
    }

    @Override
    protected String getDeserializationErrorCause() {
      return InvalidDateTimeError.class.getName();
    }
  }
}
