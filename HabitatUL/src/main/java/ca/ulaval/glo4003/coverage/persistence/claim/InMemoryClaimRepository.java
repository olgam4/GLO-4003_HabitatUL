package ca.ulaval.glo4003.coverage.persistence.claim;

import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.claim.exception.ClaimAlreadyCreatedException;
import ca.ulaval.glo4003.coverage.domain.claim.exception.ClaimNotFoundException;

import java.util.HashMap;
import java.util.Map;

public class InMemoryClaimRepository implements ClaimRepository {
  private Map<ClaimId, Claim> claims = new HashMap<>();

  @Override
  public Claim getById(ClaimId claimId) throws ClaimNotFoundException {
    if (isExistingClaim(claimId)) return claims.get(claimId);

    throw new ClaimNotFoundException();
  }

  @Override
  public void create(Claim claim) throws ClaimAlreadyCreatedException {
    ClaimId claimId = claim.getClaimId();

    if (isExistingClaim(claimId)) throw new ClaimAlreadyCreatedException();

    claims.put(claim.getClaimId(), claim);
  }

  private boolean isExistingClaim(ClaimId claimId) {
    return claims.containsKey(claimId);
  }
}
