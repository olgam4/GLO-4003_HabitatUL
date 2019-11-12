package ca.ulaval.glo4003.insuring.persistence.claim;

import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepositoryIT;

public class InMemoryClaimRepositoryIT extends ClaimRepositoryIT {
  @Override
  protected ClaimRepository createSubject() {
    return new InMemoryClaimRepository();
  }
}
