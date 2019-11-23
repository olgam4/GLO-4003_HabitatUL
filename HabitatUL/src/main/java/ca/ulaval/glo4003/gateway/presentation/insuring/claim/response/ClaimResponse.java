package ca.ulaval.glo4003.gateway.presentation.insuring.claim.response;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "status", "authorityNumber"})
public class ClaimResponse {
  private ClaimId id;
  private ClaimStatus status;
  private AuthorityNumber authorityNumber;

  private ClaimResponse() {}

  public ClaimResponse(ClaimId claimId, ClaimStatus claimStatus, AuthorityNumber authorityNumber) {
    this.id = claimId;
    this.status = claimStatus;
    this.authorityNumber = authorityNumber;
  }

  public ClaimId getId() {
    return id;
  }

  public ClaimStatus getStatus() {
    return status;
  }

  public AuthorityNumber getAuthorityNumber() {
    return authorityNumber.isFilled() ? authorityNumber : null;
  }
}
