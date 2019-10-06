package ca.ulaval.glo4003.gateway.presentation.exception;

import ca.ulaval.glo4003.coverage.domain.policy.exception.NotDeclaredBicycleException;
import ca.ulaval.glo4003.management.domain.user.exception.UnauthorizedException;
import ca.ulaval.glo4003.shared.domain.BaseException;
import ca.ulaval.glo4003.underwriting.domain.quote.exception.*;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public class ExceptionResponseFactory {
  private static final Map<Class<?>, Status> STATUS_MAP = new HashMap<>();
  private static final Status DEFAULT_STATUS = Status.INTERNAL_SERVER_ERROR;

  static {
    registerGenericErrors();
    registerUnderwritingErrors();
    registerCoverageErrors();
  }

  private static void registerGenericErrors() {
    // 401
    STATUS_MAP.put(UnauthorizedException.class, Status.UNAUTHORIZED);
  }

  private static void registerCoverageErrors() {
    // 400
    STATUS_MAP.put(NotDeclaredBicycleException.class, Status.BAD_REQUEST);
  }

  private static void registerUnderwritingErrors() {
    // 400
    STATUS_MAP.put(QuoteAlreadyPersistedException.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteAlreadyPurchasedException.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteExpiredException.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteNotYetPersistedException.class, Status.BAD_REQUEST);

    // 404
    STATUS_MAP.put(QuoteNotFoundException.class, Status.NOT_FOUND);
  }

  public ExceptionResponse createExceptionView(BaseException exception) {
    Status status = getExceptionResponseStatus(exception);
    return new ExceptionResponse(status, exception.getError(), exception.getMessage());
  }

  private Status getExceptionResponseStatus(BaseException exception) {
    return STATUS_MAP.getOrDefault(exception.getClass(), DEFAULT_STATUS);
  }
}
