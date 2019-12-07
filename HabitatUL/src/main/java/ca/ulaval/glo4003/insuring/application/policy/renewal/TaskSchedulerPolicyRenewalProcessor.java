package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.application.threading.TaskSchedulerFactory;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class TaskSchedulerPolicyRenewalProcessor implements PolicyRenewalProcessor {
  private TaskScheduler taskScheduler;
  private PolicyRepository policyRepository;
  private Logger logger;

  public TaskSchedulerPolicyRenewalProcessor() {
    this(
        ServiceLocator.resolve(TaskSchedulerFactory.class).create(),
        ServiceLocator.resolve(PolicyRepository.class),
        ServiceLocator.resolve(Logger.class));
  }

  public TaskSchedulerPolicyRenewalProcessor(
      TaskScheduler taskScheduler, PolicyRepository policyRepository, Logger logger) {
    this.taskScheduler = taskScheduler;
    this.policyRepository = policyRepository;
    this.logger = logger;
  }

  @Override
  public void scheduleRenewal(
      PolicyId policyId, PolicyRenewalId policyRenewalId, DateTime renewalEffectiveDateTime) {
    PolicyRenewalTask policyRenewalTask =
        new PolicyRenewalTask(policyId, policyRenewalId, policyRepository, logger);
    RenewalTaskKey taskKey = new RenewalTaskKey(policyId, policyRenewalId);
    taskScheduler.schedule(taskKey, policyRenewalTask, renewalEffectiveDateTime);
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    taskScheduler.cancel(new RenewalTaskKey(policyId, policyRenewalId));
  }

  static class RenewalTaskKey extends ValueComparableObject {
    private PolicyId policyId;
    private PolicyRenewalId policyRenewalId;

    RenewalTaskKey(PolicyId policyId, PolicyRenewalId policyRenewalId) {
      this.policyId = policyId;
      this.policyRenewalId = policyRenewalId;
    }
  }
}
