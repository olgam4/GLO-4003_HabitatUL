package ca.ulaval.glo4003.coverage.persistence.claim;

import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;

import java.util.HashMap;
import java.util.Map;

public class InMemoryClaimRepository implements ClaimRepository {
  private Map<ClaimId, Claim> claims = new HashMap<>();

  @Override
  public void create(Claim claim) {
    claims.put(claim.getClaimId(), claim);
  }
}
