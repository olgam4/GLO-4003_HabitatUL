package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.zipcode;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidZipCodeError;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.gateway.presentation.IntegrationTestContext.VALID_ZIP_CODE_VALUE;

@RunWith(Enclosed.class)
public class ZipCodeDeserializerIT {
  @RunWith(Parameterized.class)
  public static class ValidTestCasesDateCustomDeserializerIT
      extends ValidTestCasesCustomDeserializerIT {
    private Object value;

    public ValidTestCasesDateCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_ZIP_CODE_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new ZipCodeDeserializationResource();
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
            {"INVALID"},
            {new JSONObject().put("test", VALID_ZIP_CODE_VALUE)},
            {new JSONArray().put(VALID_ZIP_CODE_VALUE)}
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new ZipCodeDeserializationResource();
    }

    @Override
    protected Class getDeserializationErrorCause() {
      return InvalidZipCodeError.class;
    }
  }
}
