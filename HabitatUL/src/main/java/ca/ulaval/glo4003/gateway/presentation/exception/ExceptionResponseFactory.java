package ca.ulaval.glo4003.gateway.presentation.exception;

import ca.ulaval.glo4003.shared.domain.BaseException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.*;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

public class ExceptionResponseFactory {
  // TODO: might want to use enum instead?
  private static final List<Class> NOT_FOUND = Arrays.asList(QuoteNotFoundException.class);
  private static final List<Class> BAD_REQUEST =
      Arrays.asList(
          QuoteExpiredException.class,
          QuoteAlreadyPersistedException.class,
          QuoteAlreadyPurchasedException.class,
          QuoteNotYetPersistedException.class);

  public ExceptionResponse createExceptionView(BaseException exception) {
    Response.Status status = getExceptionResponseStatus(exception);
    return new ExceptionResponse(status, exception.getError(), exception.getMessage());
  }

  private Response.Status getExceptionResponseStatus(BaseException exception) {
    Class exceptionClass = exception.getClass();

    if (BAD_REQUEST.contains(exceptionClass)) return Response.Status.BAD_REQUEST;
    if (NOT_FOUND.contains(exceptionClass)) return Response.Status.NOT_FOUND;
    return Response.Status.INTERNAL_SERVER_ERROR;
  }
}
