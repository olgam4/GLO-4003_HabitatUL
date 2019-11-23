package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class TaskSchedulerPolicyRenewalProcessor implements PolicyRenewalProcessor {
  // TODO: add logger wrapper
  private TaskScheduler taskScheduler;
  private PolicyRepository policyRepository;

  public TaskSchedulerPolicyRenewalProcessor() {
    this(
        ServiceLocator.resolve(TaskScheduler.class),
        ServiceLocator.resolve(PolicyRepository.class));
  }

  public TaskSchedulerPolicyRenewalProcessor(
      TaskScheduler taskScheduler, PolicyRepository policyRepository) {
    this.taskScheduler = taskScheduler;
    this.policyRepository = policyRepository;
  }

  @Override
  public void scheduleRenewal(
      PolicyId policyId, PolicyRenewalId policyRenewalId, DateTime renewalEffectiveDateTime) {
    PolicyRenewalTask policyRenewalTask =
        new PolicyRenewalTask(policyRepository, policyId, policyRenewalId);
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
