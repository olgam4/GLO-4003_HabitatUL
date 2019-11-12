package ca.ulaval.glo4003.insuring.application.claim.error;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;

public class ClaimNotFoundError extends ClaimAppServiceError {
  private static final String ERROR = "CLAIM_NOT_FOUND";
  private static final String MESSAGE = "sorry, can't find claim with id <%s>";

  public ClaimNotFoundError(ClaimId claimId) {
    super(ERROR, String.format(MESSAGE, claimId.toRepresentation()));
  }
}
