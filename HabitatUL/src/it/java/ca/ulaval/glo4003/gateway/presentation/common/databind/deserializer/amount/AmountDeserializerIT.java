package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.amount;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAmountError;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@RunWith(Enclosed.class)
public class AmountDeserializerIT {
  private static final double VALID_VALUE = new Random().nextDouble();

  @RunWith(Parameterized.class)
  public static class ValidTestCasesAmountCustomDeserializerIT
      extends ValidTestCasesCustomDeserializerIT {
    private Object value;

    public ValidTestCasesAmountCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(new Object[][] {{VALID_VALUE}});
    }

    @Override
    public Object createDeserializationResource() {
      return new AmountDeserializationResource();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCasesAmountCustomDeserializerIT
      extends InvalidTestCasesCustomDeserializerIT {
    private Object value;

    public InvalidTestCasesAmountCustomDeserializerIT(Object value) {
      super(value);
    }

    @Parameterized.Parameters
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {""},
            {"INVALID"},
            {new JSONObject().put("test", VALID_VALUE)},
            {new JSONArray().put(VALID_VALUE)}
          });
    }

    @Override
    public Object createDeserializationResource() {
      return new AmountDeserializationResource();
    }

    @Override
    protected Class getDeserializationErrorCause() {
      return InvalidAmountError.class;
    }
  }
}
