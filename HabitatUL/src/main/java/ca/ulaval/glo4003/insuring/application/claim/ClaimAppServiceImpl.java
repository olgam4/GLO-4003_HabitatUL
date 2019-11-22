package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.application.claim.error.ClaimNotFoundError;
import ca.ulaval.glo4003.insuring.domain.claim.Claim;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.insuring.domain.claim.exception.ClaimNotFoundException;

public class ClaimAppServiceImpl implements ClaimAppService {
  private ClaimRepository claimRepository;
  private ClaimAssembler claimAssembler;

  public ClaimAppServiceImpl() {
    this(ServiceLocator.resolve(ClaimRepository.class), new ClaimAssembler());
  }

  public ClaimAppServiceImpl(ClaimRepository claimRepository, ClaimAssembler claimAssembler) {
    this.claimRepository = claimRepository;
    this.claimAssembler = claimAssembler;
  }

  @Override
  public ClaimDto getClaim(ClaimId claimId) {
    try {
      Claim claim = claimRepository.getById(claimId);
      return claimAssembler.from(claim);
    } catch (ClaimNotFoundException e) {
      throw new ClaimNotFoundError(claimId);
    }
  }
}
