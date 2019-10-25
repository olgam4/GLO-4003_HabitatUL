package ca.ulaval.glo4003.gateway.presentation.common.handling.error.underwriting;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidAnimalsError;
import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.gateway.presentation.common.handling.MockedDeserializationError;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.helper.quote.form.PersonalPropertyGenerator.createAnimals;

public class InvalidAnimalsErrorMappingIT extends ErrorMappingIT {
  private static final String INVALID_ANIMALS_VALUE = createAnimals().toString();

  @Override
  public Throwable getError() {
    return new MockedDeserializationError(new InvalidAnimalsError(INVALID_ANIMALS_VALUE));
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
    return String.format("sorry, <%s> is not a valid animal list", INVALID_ANIMALS_VALUE);
  }
}
