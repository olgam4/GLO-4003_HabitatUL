package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.lossdeclarations;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.InvalidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.ValidTestCasesCustomDeserializerIT;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidLossDeclarationsError;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import com.github.javafaker.Faker;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.insuring.helper.claim.LossDeclarationsGenerator.createLossCategory;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createAmountSmallerThan;
import static ca.ulaval.glo4003.shared.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;

@RunWith(Enclosed.class)
public class LossDeclarationsDeserializerIT {
  private static final String VALID_LOSS_CATEGORY = createLossCategory().toString();
  private static final float VALID_LOSS_AMOUNT =
      createAmountGreaterThan(Amount.ZERO).getValue().floatValue();
  private static final JSONObject VALID_LOSS_DECLARATION =
      toJson(VALID_LOSS_CATEGORY, VALID_LOSS_AMOUNT);
  private static final String ANOTHER_VALID_LOSS_CATEGORY = createLossCategory().toString();
  private static final float ANOTHER_VALID_LOSS_AMOUNT =
      createAmountGreaterThan(Amount.ZERO).getValue().floatValue();
  private static final JSONObject ANOTHER_VALID_LOSS_DECLARATION =
      toJson(ANOTHER_VALID_LOSS_CATEGORY, ANOTHER_VALID_LOSS_AMOUNT);
  private static final JSONArray VALID_VALUE =
      new JSONArray().put(VALID_LOSS_DECLARATION).put(ANOTHER_VALID_LOSS_DECLARATION);

  private static JSONObject toJson(Object category, Object amount) {
    JSONObject lossDeclaration = new JSONObject();
    lossDeclaration.put("category", category);
    lossDeclaration.put("amount", amount);
    return lossDeclaration;
  }

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
      return new LossDeclarationsDeserializationResource();
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
          VALID_LOSS_DECLARATION,
          new JSONArray().put(new JSONObject().put("category", VALID_LOSS_CATEGORY)),
          new JSONArray().put(new JSONObject().put("amount", VALID_LOSS_AMOUNT)),
          new JSONArray().put(toJson(null, VALID_LOSS_AMOUNT)),
          new JSONArray().put(toJson(VALID_LOSS_CATEGORY, null)),
          new JSONArray().put(toJson("", VALID_LOSS_AMOUNT)),
          new JSONArray().put(toJson(Faker.instance().number().randomDigit(), VALID_LOSS_AMOUNT)),
          new JSONArray()
              .put(
                  toJson(
                      VALID_LOSS_CATEGORY,
                      createAmountSmallerThan(Amount.ZERO).getValue().floatValue())));
    }

    @Override
    public Object createDeserializationResource() {
      return new LossDeclarationsDeserializationResource();
    }

    @Override
    protected Class<?> getDeserializationErrorCause() {
      return InvalidLossDeclarationsError.class;
    }
  }
}
