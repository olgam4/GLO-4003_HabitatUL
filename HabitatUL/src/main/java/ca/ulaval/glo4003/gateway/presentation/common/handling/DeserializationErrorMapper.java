package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.DeserializationError;
import ca.ulaval.glo4003.shared.domain.BaseError;
import ca.ulaval.glo4003.shared.domain.Error;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
@Produces(MediaType.APPLICATION_JSON)
public class DeserializationErrorMapper implements ExceptionMapper<JsonMappingException> {
  private ErrorResponseFactory errorResponseFactory;

  public DeserializationErrorMapper() {
    errorResponseFactory = new ErrorResponseFactory();
  }

  @Override
  public Response toResponse(JsonMappingException e) {
    Error error = convertError(e);
    ErrorResponse errorResponse = errorResponseFactory.createErrorResponse(error);
    return Response.status(errorResponse.getStatus())
        .entity(errorResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }

  private Error convertError(JsonMappingException e) {
    return isDeserializationError(e) ? (DeserializationError) e.getCause() : new BaseError();
  }

  private boolean isDeserializationError(JsonMappingException e) {
    return e.getCause() instanceof DeserializationError;
  }
}
