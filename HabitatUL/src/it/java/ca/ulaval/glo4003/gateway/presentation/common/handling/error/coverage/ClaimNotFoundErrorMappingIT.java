package ca.ulaval.glo4003.gateway.presentation.common.handling.error.coverage;

import ca.ulaval.glo4003.gateway.presentation.common.handling.ErrorMappingIT;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;

import javax.ws.rs.core.Response;

public class ClaimNotFoundErrorMappingIT extends ErrorMappingIT {

  public static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  @Override
  public int getStatusCode() {
    return Response.Status.NOT_FOUND.getStatusCode();
  }

  @Override
  public Throwable getError() {
    return new ClaimNotFoundError(CLAIM_ID);
  }

  @Override
  public String getErrorCodeMatcher() {
    return "CLAIM_NOT_FOUND";
  }

  @Override
  public String getErrorMessageMatcher() {
    return String.format("sorry, can't find claim with id <%s>", CLAIM_ID.toRepresentation());
  }
}
