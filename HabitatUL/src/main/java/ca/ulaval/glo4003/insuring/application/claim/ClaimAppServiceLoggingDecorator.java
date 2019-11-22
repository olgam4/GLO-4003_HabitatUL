package ca.ulaval.glo4003.insuring.application.claim;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.claim.dto.ClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;

import java.util.logging.Logger;

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
    logger.info(String.format("Get claim <%s>", claimId));
    ClaimDto claimDto = this.claimAppService.getClaim(claimId);
    logger.info(String.format("Found claim <%s>", claimDto));
    return claimDto;
  }
}
