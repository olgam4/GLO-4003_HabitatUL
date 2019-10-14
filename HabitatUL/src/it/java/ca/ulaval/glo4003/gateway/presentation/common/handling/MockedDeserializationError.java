package ca.ulaval.glo4003.gateway.presentation.common.handling;

import com.fasterxml.jackson.core.JsonProcessingException;

public class MockedDeserializationError extends JsonProcessingException {
  public MockedDeserializationError(Throwable rootCause) {
    super(rootCause);
  }
}
