package ca.ulaval.glo4003.gateway.presentation.common.handling.error;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidCivilLiabilityAmountError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;
import com.github.javafaker.Faker;

import javax.ws.rs.core.Response;

public class InvalidCivilLiabilityAmountErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_CIVIL_LIABILITY_AMOUNT = Faker.instance().internet().uuid();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(
        new InvalidCivilLiabilityAmountError(INVALID_CIVIL_LIABILITY_AMOUNT));
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "INVALID_CIVIL_LIABILITY_AMOUNT";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, <%s> is not a valid civil liability amount value", INVALID_CIVIL_LIABILITY_AMOUNT);
  }
}
