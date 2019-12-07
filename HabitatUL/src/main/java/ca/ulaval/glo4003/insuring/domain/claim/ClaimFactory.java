package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.RECEIVED;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.UNDER_ANALYSIS;
import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;

public class ClaimFactory {
  private ClockProvider clockProvider;

  public ClaimFactory(ClockProvider clockProvider) {
    this.clockProvider = clockProvider;
  }

  public Claim create(SinisterType sinisterType, LossDeclarations lossDeclarations) {
    ClaimId claimId = new ClaimId();
    Date declarationDate = Date.now(clockProvider.getClock());
    return new Claim(
        claimId,
        declarationDate,
        getClaimOpeningStatus(sinisterType),
        UNFILLED_AUTHORITY_NUMBER,
        sinisterType,
        lossDeclarations);
  }

  private ClaimStatus getClaimOpeningStatus(SinisterType sinisterType) {
    switch (sinisterType) {
      case FIRE:
        return UNDER_ANALYSIS;
      default:
        return RECEIVED;
    }
  }
}
