package ca.ulaval.glo4003.gateway.presentation.common.error;

import ca.ulaval.glo4003.coverage.domain.policy.exception.NotDeclaredBicycleError;
import ca.ulaval.glo4003.management.domain.user.exception.UnauthorizedError;
import ca.ulaval.glo4003.shared.domain.BaseError;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.InvalidEffectiveDateError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteAlreadyPurchasedError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteExpiredError;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponseFactory {
  private static final Map<Class<?>, Status> STATUS_MAP = new HashMap<>();
  private static final Status DEFAULT_STATUS = Status.INTERNAL_SERVER_ERROR;

  static {
    registerGenericErrors();
    registerUnderwritingErrors();
    registerCoverageErrors();
  }

  private static void registerGenericErrors() {
    // 401
    STATUS_MAP.put(UnauthorizedError.class, Status.UNAUTHORIZED);
  }

  private static void registerCoverageErrors() {
    // 400
    STATUS_MAP.put(NotDeclaredBicycleError.class, Status.BAD_REQUEST);
  }

  private static void registerUnderwritingErrors() {
    // 400
    STATUS_MAP.put(CouldNotRequestQuoteError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidEffectiveDateError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteAlreadyPurchasedError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteExpiredError.class, Status.BAD_REQUEST);

    // 404
    STATUS_MAP.put(QuoteNotFoundError.class, Status.NOT_FOUND);
  }

  public ErrorResponse createExceptionView(BaseError exception) {
    Status status = getExceptionResponseStatus(exception);
    return new ErrorResponse(status, exception.getError(), exception.getMessage());
  }

  private Status getExceptionResponseStatus(BaseError exception) {
    return STATUS_MAP.getOrDefault(exception.getClass(), DEFAULT_STATUS);
  }
}
