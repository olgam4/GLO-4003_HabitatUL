package ca.ulaval.glo4003.gateway.presentation.insuring.policy.response;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonPropertyOrder({"exceedingClaims"})
public class ConfigureMaximumLossRatioResponse {
  private List<ExceedingClaimsByPolicyResponse> exceedingClaims;

  public ConfigureMaximumLossRatioResponse(List<ExceedingClaimsByPolicyResponse> exceedingClaims) {
    this.exceedingClaims = exceedingClaims;
  }

  public List<ExceedingClaimsByPolicyResponse> getExceedingClaims() {
    return exceedingClaims;
  }

  @JsonPropertyOrder({"policyId", "claimIds"})
  public static class ExceedingClaimsByPolicyResponse {
    private PolicyId policyId;
    private List<ClaimId> claimIds;

    public ExceedingClaimsByPolicyResponse(PolicyId policyId, List<ClaimId> claimIds) {
      this.policyId = policyId;
      this.claimIds = claimIds;
    }

    public PolicyId getPolicyId() {
      return policyId;
    }

    public List<ClaimId> getClaimIds() {
      return claimIds;
    }
  }
}
