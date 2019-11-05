package ca.ulaval.glo4003.coverage.domain.claim;

import ca.ulaval.glo4003.coverage.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.coverage.domain.claim.exception.ClaimNotFoundException;

public interface ClaimRepository {
  Claim getById(ClaimId claimId) throws ClaimNotFoundException;

  void create(Claim claim) throws ClaimAlreadyCreatedException;
}
