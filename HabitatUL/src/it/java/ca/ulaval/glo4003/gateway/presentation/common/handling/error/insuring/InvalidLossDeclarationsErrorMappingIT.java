package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidLossDeclarationsError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidLossDeclarationsErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_VALUE = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidLossDeclarationsError(INVALID_VALUE));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_LOSS_DECLARATIONS";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, <%s> is not a valid loss declarations value", INVALID_VALUE);
  }
}
