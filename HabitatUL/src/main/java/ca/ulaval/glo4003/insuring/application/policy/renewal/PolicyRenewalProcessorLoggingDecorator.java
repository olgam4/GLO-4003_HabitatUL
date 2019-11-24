package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.logging.Logger;

public class PolicyRenewalProcessorLoggingDecorator implements PolicyRenewalProcessor {
  private PolicyRenewalProcessor policyRenewalProcessor;
  private Logger logger;

  public PolicyRenewalProcessorLoggingDecorator(PolicyRenewalProcessor policyRenewalProcessor) {
    this(policyRenewalProcessor, ServiceLocator.resolve(Logger.class));
  }

  public PolicyRenewalProcessorLoggingDecorator(
      PolicyRenewalProcessor policyRenewalProcessor, Logger logger) {
    this.policyRenewalProcessor = policyRenewalProcessor;
    this.logger = logger;
  }

  @Override
  public void scheduleRenewal(
      PolicyId policyId, PolicyRenewalId policyRenewalId, DateTime renewalEffectiveDateTime) {
    logger.info(
        String.format(
            "Scheduling Renewal <%s> of Policy <%s> at <%s>",
            policyRenewalId.toRepresentation(),
            policyId.toRepresentation(),
            renewalEffectiveDateTime.getValue()));
    policyRenewalProcessor.scheduleRenewal(policyId, policyRenewalId, renewalEffectiveDateTime);
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    logger.info(
        String.format(
            "Canceling Renewal <%s> of Policy <%s>",
            policyRenewalId.toRepresentation(), policyId.toRepresentation()));
    policyRenewalProcessor.cancelRenewal(policyId, policyRenewalId);
  }
}
