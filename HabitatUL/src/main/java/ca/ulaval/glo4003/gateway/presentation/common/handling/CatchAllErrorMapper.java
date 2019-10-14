package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.shared.domain.BaseError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class CatchAllErrorMapper implements ExceptionMapper<Throwable> {
  private ErrorResponseFactory errorResponseFactory;

  public CatchAllErrorMapper() {
    errorResponseFactory = new ErrorResponseFactory();
  }

  @Override
  public Response toResponse(Throwable throwable) {
    BaseError error = new BaseError();
    ErrorResponse errorResponse = errorResponseFactory.createErrorResponse(error);
    return Response.status(errorResponse.getStatus())
        .entity(errorResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
