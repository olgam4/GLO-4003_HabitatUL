package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;

import java.util.Optional;

public interface ClaimRepository {
  Claim getById(ClaimId claimId) throws ClaimNotFoundException;

  Optional<Claim> findById(ClaimId claimId);

  void create(Claim claim) throws ClaimAlreadyCreatedException;

  void update(Claim claim) throws ClaimNotFoundException;
}
