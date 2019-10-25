package ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer;

import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class MockedDeserializationErrorMapper implements ExceptionMapper<JsonMappingException> {
  @Override
  public Response toResponse(JsonMappingException error) {
    String cause = error.getCause().getClass().getName();
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
        .entity(new DeserializationErrorResponse(cause))
        .type(MediaType.APPLICATION_JSON_TYPE)
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
