package ca.ulaval.glo4003.gateway.presentation.common.handling.error.shared;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAuthorityNumber;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidAuthorityNumberErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_VALUE = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidAuthorityNumber(INVALID_VALUE));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_AUTHORITY_NUMBER";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, <%s> is not a valid authority number", INVALID_VALUE);
  }
}
