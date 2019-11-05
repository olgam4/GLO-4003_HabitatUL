package ca.ulaval.glo4003.coverage.persistence.claim;

import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepositoryIT;

public class InMemoryClaimRepositoryIT extends ClaimRepositoryIT {
  @Override
  protected ClaimRepository createSubject() {
    return new InMemoryClaimRepository();
  }
}
