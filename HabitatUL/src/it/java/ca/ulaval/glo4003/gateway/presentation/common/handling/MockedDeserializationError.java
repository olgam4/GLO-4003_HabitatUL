package ca.ulaval.glo4003.gateway.presentation.common.handling;

import com.fasterxml.jackson.databind.JsonMappingException;

public class MockedDeserializationError extends JsonMappingException {
  public MockedDeserializationError(Throwable rootCause) {
    super(() -> {}, "", rootCause);
  }
}
