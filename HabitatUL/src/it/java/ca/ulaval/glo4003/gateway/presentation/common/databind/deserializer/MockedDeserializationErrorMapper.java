package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class MockedDeserializationErrorMapper implements ExceptionMapper<JsonProcessingException> {
  @Override
  public Response toResponse(JsonProcessingException error) {
    return Response.serverError()
        .entity(new DeserializationErrorResponse(error.getCause().getClass().getName()))
        .build();
  }

  public static class DeserializationErrorResponse {
    private String cause;

    public DeserializationErrorResponse(String cause) {
      this.cause = cause;
    }

    public String getCause() {
      return cause;
    }
  }
}
