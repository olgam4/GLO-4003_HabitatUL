package ca.ulaval.glo4003.gateway.presentation.common.error;

import ca.ulaval.glo4003.shared.domain.BaseError;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseErrorMapper implements ExceptionMapper<BaseError> {
  private ErrorResponseFactory errorResponseFactory;

  public BaseErrorMapper() {
    errorResponseFactory = new ErrorResponseFactory();
  }

  @Override
  public Response toResponse(BaseError baseError) {
    ErrorResponse errorResponse = errorResponseFactory.createExceptionView(baseError);
    return Response.status(errorResponse.getStatus())
        .entity(errorResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
