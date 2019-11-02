package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.floor;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidFloorError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.gateway.presentation.IntegrationTestContext.VALID_FLOOR_VALUE;

@RunWith(Enclosed.class)
public class FloorDeserializerIT {
  @RunWith(Parameterized.class)
  public static class ValidTestCasesFloorCustomDeserializerIT
      extends ValidTestCasesCustomDeserializerIT {
    private Object value;

    public ValidTestCasesFloorCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_FLOOR_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new FloorDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCasesFloorCustomDeserializerIT
      extends InvalidTestCasesCustomDeserializerIT {
    private Object value;

    public InvalidTestCasesFloorCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {""},
            {Faker.instance().number().randomDigit()},
            {"INVALID"},
            {new JSONObject().put("test", VALID_FLOOR_VALUE)},
            {new JSONArray().put(VALID_FLOOR_VALUE)}
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new FloorDeserializationResource();
    }

    @Override
    protected String getDeserializationErrorCause() {
      return InvalidFloorError.class.getName();
    }
  }
}
