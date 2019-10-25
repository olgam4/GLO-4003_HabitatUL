package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

public class InvalidAnimalsError extends DeserializationError {
  private static final String ERROR = "INVALID_ANIMALS";
  private static final String MESSAGE = "sorry, <%s> is not a valid animal list";

  public InvalidAnimalsError(String invalidAnimals) {
    super(ERROR, String.format(MESSAGE, invalidAnimals));
  }
}
