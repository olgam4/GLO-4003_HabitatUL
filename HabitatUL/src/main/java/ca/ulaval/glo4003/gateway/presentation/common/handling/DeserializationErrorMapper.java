package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.DeserializationError;
import ca.ulaval.glo4003.shared.domain.BaseError;
import ca.ulaval.glo4003.shared.domain.Error;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DeserializationErrorMapper implements ExceptionMapper<JsonProcessingException> {
  private ErrorResponseFactory errorResponseFactory;

  public DeserializationErrorMapper() {
    errorResponseFactory = new ErrorResponseFactory();
  }

  @Override
  public Response toResponse(JsonProcessingException jpe) {
    Error error = convertError(jpe);
    ErrorResponse errorResponse = errorResponseFactory.createExceptionView(error);
    return Response.status(errorResponse.getStatus())
        .entity(errorResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }

  private Error convertError(JsonProcessingException jpe) {
    return isDeserializationError(jpe) ? (DeserializationError) jpe.getCause() : new BaseError();
  }

  private boolean isDeserializationError(JsonProcessingException jpe) {
    return jpe.getCause() instanceof DeserializationError;
  }
}
