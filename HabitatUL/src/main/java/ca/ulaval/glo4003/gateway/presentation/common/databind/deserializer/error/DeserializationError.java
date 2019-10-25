package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

import ca.ulaval.glo4003.shared.domain.handling.Error;
import com.fasterxml.jackson.core.JsonProcessingException;

public abstract class DeserializationError extends JsonProcessingException implements Error {
  private final String error;
  private final String message;

  DeserializationError(String error, String message) {
    super(message);
    this.error = error;
    this.message = message;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }
}
