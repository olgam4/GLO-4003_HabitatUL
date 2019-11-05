package ca.ulaval.glo4003.coverage.application.claim;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.coverage.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.coverage.domain.claim.Claim;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimId;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.claim.exception.ClaimNotFoundException;

public class ClaimAppService {
  private ClaimRepository claimRepository;
  private ClaimAssembler claimAssembler;

  public ClaimAppService() {
    this(ServiceLocator.resolve(ClaimRepository.class), new ClaimAssembler());
  }

  public ClaimAppService(ClaimRepository claimRepository, ClaimAssembler claimAssembler) {
    this.claimRepository = claimRepository;
    this.claimAssembler = claimAssembler;
  }

  public ClaimDto getClaim(ClaimId claimId) {
    try {
      Claim claim = claimRepository.getById(claimId);
      return claimAssembler.from(claim);
    } catch (ClaimNotFoundException e) {
      throw new ClaimNotFoundError(claimId);
    }
  }
}
