package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error;

import ca.ulaval.glo4003.shared.domain.Error;
import com.fasterxml.jackson.core.JsonProcessingException;

public class DeserializationError extends JsonProcessingException implements Error {
  private final String error;
  private final String description;

  DeserializationError(String error, String description) {
    super(description);
    this.error = error;
    this.description = description;
  }

  public String getError() {
    return error;
  }

  public String getDescription() {
    return description;
  }
}
