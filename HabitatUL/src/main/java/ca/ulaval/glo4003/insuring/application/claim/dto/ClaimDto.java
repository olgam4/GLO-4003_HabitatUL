package ca.ulaval.glo4003.insuring.application.claim.dto;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus;
import ca.ulaval.glo4003.insuring.domain.claim.LossDeclarations;
import ca.ulaval.glo4003.insuring.domain.claim.SinisterType;
import ca.ulaval.glo4003.shared.application.DataTransferObject;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;

public class ClaimDto extends DataTransferObject {
  private final ClaimId claimId;
  private final ClaimStatus claimStatus;
  private final AuthorityNumber authorityNumber;
  private final SinisterType sinisterType;
  private final LossDeclarations lossDeclarations;

  public ClaimDto(
      ClaimId claimId,
      ClaimStatus claimStatus,
      AuthorityNumber authorityNumber,
      SinisterType sinisterType,
      LossDeclarations lossDeclarations) {
    this.claimId = claimId;
    this.claimStatus = claimStatus;
    this.authorityNumber = authorityNumber;
    this.sinisterType = sinisterType;
    this.lossDeclarations = lossDeclarations;
  }

  public ClaimId getClaimId() {
    return claimId;
  }

  public ClaimStatus getClaimStatus() {
    return claimStatus;
  }

  public AuthorityNumber getAuthorityNumber() {
    return authorityNumber;
  }

  public SinisterType getSinisterType() {
    return sinisterType;
  }

  public LossDeclarations getLossDeclarations() {
    return lossDeclarations;
  }
}
