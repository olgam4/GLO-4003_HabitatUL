package ca.ulaval.glo4003.gateway.presentation.claim.response;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "status"})
public class ClaimResponse {
  private ClaimId id;
  private ClaimStatus status;

  private ClaimResponse() {}

  public ClaimResponse(ClaimId claimId, ClaimStatus claimStatus) {
    this.id = claimId;
    this.status = claimStatus;
  }

  public ClaimId getId() {
    return id;
  }

  public ClaimStatus getStatus() {
    return status;
  }
}
