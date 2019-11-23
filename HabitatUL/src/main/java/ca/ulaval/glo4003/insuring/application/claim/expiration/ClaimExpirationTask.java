package ca.ulaval.glo4003.insuring.application.claim.expiration;

import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;

public class ClaimExpirationTask implements Runnable {
  private ClaimRepository claimRepository;
  private ClaimId claimId;

  public ClaimExpirationTask(ClaimRepository claimRepository, ClaimId claimId) {
    this.claimRepository = claimRepository;
    this.claimId = claimId;
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
