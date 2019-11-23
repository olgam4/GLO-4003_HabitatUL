package ca.ulaval.glo4003.insuring.domain.claim.error;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;

public class CannotAcceptAuthorityNumberError extends ClaimError {
  private static final String ERROR = "CANNOT_ACCEPT_AUTHORITY_NUMBER";
  private static final String MESSAGE =
      "sorry, you can't provide an authority number for claim with id <%s>";

  public CannotAcceptAuthorityNumberError(ClaimId claimId) {
    super(ERROR, String.format(MESSAGE, claimId.toRepresentation()));
  }
}
