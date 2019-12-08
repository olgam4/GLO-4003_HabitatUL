package ca.ulaval.glo4003.insuring.application.policy.claimexpiration;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class ClaimExpirationProcessorLoggingDecorator implements ClaimExpirationProcessor {
  private ClaimExpirationProcessor claimExpirationProcessor;
  private Logger logger;

  public ClaimExpirationProcessorLoggingDecorator(
      ClaimExpirationProcessor claimExpirationProcessor) {
    this(claimExpirationProcessor, ServiceLocator.resolve(Logger.class));
  }

  public ClaimExpirationProcessorLoggingDecorator(
      ClaimExpirationProcessor claimExpirationProcessor, Logger logger) {
    this.claimExpirationProcessor = claimExpirationProcessor;
    this.logger = logger;
  }

  @Override
  public void scheduleExpiration(ClaimId claimId, DateTime expirationDateTime) {
    logger.info(
        String.format(
            "Scheduling Claim <%s> to expire at <%s>",
            claimId.toRepresentation(), expirationDateTime.getValue()));
    claimExpirationProcessor.scheduleExpiration(claimId, expirationDateTime);
  }

  @Override
  public void cancelExpiration(ClaimId claimId) {
    logger.info(String.format("Canceling expiration for Claim <%s>", claimId.toRepresentation()));
    claimExpirationProcessor.cancelExpiration(claimId);
  }
}
