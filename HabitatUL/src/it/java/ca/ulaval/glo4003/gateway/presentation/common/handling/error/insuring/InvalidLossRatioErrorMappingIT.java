package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidLossRatioError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidLossRatioErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_VALUE = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidLossRatioError(INVALID_VALUE));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_LOSS_RATIO";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, <%s> is not a valid loss ratio value", INVALID_VALUE);
  }
}
