package ca.ulaval.glo4003.gateway.presentation.common.handling;

import ca.ulaval.glo4003.administration.application.user.error.CouldNotAuthenticateUserError;
import ca.ulaval.glo4003.administration.application.user.error.CouldNotCreateUserError;
import ca.ulaval.glo4003.administration.application.user.error.InvalidCredentialsError;
import ca.ulaval.glo4003.administration.domain.user.error.UnauthorizedError;
import ca.ulaval.glo4003.coverage.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.coverage.application.policy.error.CouldNotOpenClaimError;
import ca.ulaval.glo4003.coverage.domain.policy.error.ClaimOutsideCoveragePeriodError;
import ca.ulaval.glo4003.coverage.domain.policy.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.coverage.domain.policy.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.coverage.domain.policy.error.PolicyNotFoundError;
import ca.ulaval.glo4003.gateway.presentation.common.databind.deserializer.error.*;
import ca.ulaval.glo4003.shared.domain.handling.Error;
import ca.ulaval.glo4003.underwriting.application.quote.error.CouldNotRequestQuoteError;
import ca.ulaval.glo4003.underwriting.application.quote.error.QuoteNotFoundError;
import ca.ulaval.glo4003.underwriting.domain.quote.error.*;

import javax.ws.rs.core.Response.Status;
import java.util.HashMap;
import java.util.Map;

public class ErrorResponseFactory {
  private static final Map<Class<?>, Status> STATUS_MAP = new HashMap<>();
  private static final Status DEFAULT_STATUS = Status.INTERNAL_SERVER_ERROR;

  static {
    registerGenericErrors();
    registerSharedErrors();
    registerAdministrationErrors();
    registerUnderwritingErrors();
    registerCoverageErrors();
  }

  private static void registerGenericErrors() {
    STATUS_MAP.put(UnauthorizedError.class, Status.UNAUTHORIZED);
  }

  private static void registerSharedErrors() {
    STATUS_MAP.put(InvalidAmountError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidDateError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidDateTimeError.class, Status.BAD_REQUEST);
  }

  private static void registerAdministrationErrors() {
    STATUS_MAP.put(CouldNotAuthenticateUserError.class, Status.INTERNAL_SERVER_ERROR);
    STATUS_MAP.put(CouldNotCreateUserError.class, Status.INTERNAL_SERVER_ERROR);
    STATUS_MAP.put(InvalidCredentialsError.class, Status.UNAUTHORIZED);
  }

  private static void registerCoverageErrors() {
    STATUS_MAP.put(ClaimNotFoundError.class, Status.NOT_FOUND);
    STATUS_MAP.put(ClaimOutsideCoveragePeriodError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(CouldNotOpenClaimError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidLossDeclarationsError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidSinisterTypeError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(LossDeclarationsExceedCoverageAmountError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(NotDeclaredBicycleError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(PolicyNotFoundError.class, Status.NOT_FOUND);
  }

  private static void registerUnderwritingErrors() {
    STATUS_MAP.put(CouldNotRequestQuoteError.class, Status.INTERNAL_SERVER_ERROR);
    STATUS_MAP.put(InvalidAnimalsError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidCivilLiabilityLimitError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidFloorError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidGenderError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidPreventionSystemsError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(InvalidZipCodeError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteAlreadyPurchasedError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteCivilLiabilityLimitError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteDifferentAdditionalInsuredError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteEffectiveDateError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteExpiredError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuotePositiveCoverageAmountError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteNotFoundError.class, Status.NOT_FOUND);
    STATUS_MAP.put(QuoteStudentNamedInsuredError.class, Status.BAD_REQUEST);
    STATUS_MAP.put(QuoteUniversityProfileError.class, Status.BAD_REQUEST);
  }

  public ErrorResponse createErrorResponse(Error error) {
    Status status = getErrorResponseStatus(error);
    return new ErrorResponse(status, error.getError(), error.getMessage());
  }

  private Status getErrorResponseStatus(Error error) {
    return STATUS_MAP.getOrDefault(error.getClass(), DEFAULT_STATUS);
  }
}
