package ca.ulaval.glo4003.coverage.domain.claim;

public interface ClaimRepository {
  void create(Claim claim);

  Claim getById(ClaimId claimId);
}
