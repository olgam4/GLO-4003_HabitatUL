package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;

public class ClaimExpirationTask implements Runnable {
  private ClaimId claimId;
  private ClaimRepository claimRepository;

  public ClaimExpirationTask(ClaimId claimId, ClaimRepository claimRepository) {
    this.claimId = claimId;
    this.claimRepository = claimRepository;
  }

  @Override
  public void run() {
    try {
      Claim claim = claimRepository.getById(claimId);
      claim.expire();
      claimRepository.update(claim);
    } catch (ClaimNotFoundException e) {
      throw new ClaimNotFoundError(claimId);
    }
  }
}
