package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;

public class ClaimAppServiceLoggingDecorator implements ClaimAppService {
  private ClaimAppService claimAppService;
  private Logger logger;

  public ClaimAppServiceLoggingDecorator(ClaimAppService claimAppService) {
    this(claimAppService, ServiceLocator.resolve(Logger.class));
  }

  public ClaimAppServiceLoggingDecorator(ClaimAppService claimAppService, Logger logger) {
    this.claimAppService = claimAppService;
    this.logger = logger;
  }

  @Override
  public ClaimDto getClaim(ClaimId claimId) {
    logger.info(String.format("Getting Claim <%s>", claimId));
    ClaimDto claimDto = this.claimAppService.getClaim(claimId);
    logger.info(String.format("Claim <%s> found", claimDto));
    return claimDto;
  }

  @Override
  public ClaimDto provideAuthorityNumber(ClaimId claimId, AuthorityNumber authorityNumber) {
    logger.info(String.format("Providing authority number for Claim <%s>", claimId));
    ClaimDto claimDto = this.claimAppService.provideAuthorityNumber(claimId, authorityNumber);
    logger.info(String.format("Authority number provided for Claim <%s>", claimDto));
    return claimDto;
  }
}
