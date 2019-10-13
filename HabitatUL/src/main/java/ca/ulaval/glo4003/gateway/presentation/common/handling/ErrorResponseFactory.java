package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.administration.domain.user.exception.UnauthorizedError;
import ca.ulaval.glo4003.coverage.domain.policy.exception.NotDeclaredBicycleError;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidFloorError;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidPreventionSystemError;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidSinisterTypeError;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.InvalidZipCodeError;
import ca.ulaval.glo4003.shared.domain.Error;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.InvalidEffectiveDateError;
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
    STATUS_MAP.put(InvalidFloorError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidPreventionSystemError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidSinisterTypeError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidZipCodeError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteAlreadyPurchasedError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteExpiredError.class, Status.BAD_REQUEST);

    // 404
    STATUS_MAP.put(QuoteNotFoundError.class, Status.NOT_FOUND);
  }

  public ErrorResponse createExceptionView(Error error) {
    Status status = getErrorResponseStatus(error);
    return new ErrorResponse(status, error.getError(), error.getMessage());
  }

  private Status getErrorResponseStatus(Error error) {
    return STATUS_MAP.getOrDefault(error.getClass(), DEFAULT_STATUS);
  }
}
