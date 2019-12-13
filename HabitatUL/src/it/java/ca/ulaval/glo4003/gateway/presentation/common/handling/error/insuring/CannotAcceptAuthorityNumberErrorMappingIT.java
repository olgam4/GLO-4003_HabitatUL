package ca.ulaval.glo4003.gateway.presentation.common.handling.error.insuring;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.error.CannotAcceptAuthorityNumberError;

import javax.ws.rs.core.Response;

import static ca.ulaval.glo4003.insuring.helper.claim.ClaimGenerator.createClaimId;

public class CannotAcceptAuthorityNumberErrorMappingIT extends ErrorMappingIT {
  private static final ClaimId CLAIM_ID = createClaimId();

  @Override
  public Throwable getError() {
    return new CannotAcceptAuthorityNumberError(CLAIM_ID);
  }

  @Override
  public int getStatusCode() {
    return Response.Status.BAD_REQUEST.getStatusCode();
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CANNOT_ACCEPT_AUTHORITY_NUMBER";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format(
        "sorry, you can't provide an authority number for claim with id <%s>",
        CLAIM_ID.toRepresentation());
  }
}
