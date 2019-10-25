package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCivilLiabilityLimitError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidCivilLiabilityLimitErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_CIVIL_LIABILITY_LIMIT = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(
        new InvalidCivilLiabilityLimitError(INVALID_CIVIL_LIABILITY_LIMIT));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_CIVIL_LIABILITY_LIMIT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, <%s> is not a valid civil liability limit value", INVALID_CIVIL_LIABILITY_LIMIT);
  }
}
