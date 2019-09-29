package ca.ulaval.glo4003.gateway.presentation.exception;

import ca.ulaval.glo4003.shared.domain.BaseException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class BaseExceptionMapper implements ExceptionMapper<Throwable> {
  private ExceptionResponseFactory exceptionResponseFactory;

  public BaseExceptionMapper() {
    exceptionResponseFactory = new ExceptionResponseFactory();
  }

  @Override
  public Response toResponse(Throwable throwable) {
    BaseException exception;

    if (throwable instanceof BaseException) exception = (BaseException) throwable;
    else exception = new BaseException();

    ExceptionResponse exceptionResponse = exceptionResponseFactory.createExceptionView(exception);

    return Response.status(exceptionResponse.getStatus())
        .entity(exceptionResponse.getMessage())
        .type(MediaType.APPLICATION_JSON_TYPE)
        .build();
  }
}
