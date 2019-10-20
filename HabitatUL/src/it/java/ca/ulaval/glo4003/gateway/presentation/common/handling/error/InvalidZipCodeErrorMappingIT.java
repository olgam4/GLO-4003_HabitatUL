package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidZipCodeError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidZipCodeErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_ZIP_CODE_VALUE = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidZipCodeError(INVALID_ZIP_CODE_VALUE));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_ZIP_CODE";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, <%s> is not a valid zip code value", INVALID_ZIP_CODE_VALUE);
  }
}
