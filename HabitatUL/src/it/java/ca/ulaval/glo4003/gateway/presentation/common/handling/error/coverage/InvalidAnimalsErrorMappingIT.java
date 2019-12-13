package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAnimalsError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.coverage.helper.premium.QuotePremiumInputGenerator.createAnimals;

public class InvalidAnimalsErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_VALUE = createAnimals().toString();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidAnimalsError(INVALID_VALUE));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_ANIMALS";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, <%s> is not a valid animal list", INVALID_VALUE);
  }
}
