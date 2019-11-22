package ca.ulaval.glo4003.insuring.application.policy.renewal;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class TaskSchedulerPolicyRenewalProcessor implements PolicyRenewalProcessor {
  // TODO: add logger wrapper
  private TaskScheduler taskScheduler;
  private Map<Map.Entry<PolicyId, PolicyRenewalId>, String> scheduledRenewals;

  public TaskSchedulerPolicyRenewalProcessor() {
    this(ServiceLocator.resolve(TaskScheduler.class), new HashMap<>());
  }

  public TaskSchedulerPolicyRenewalProcessor(
      TaskScheduler taskScheduler,
      Map<Map.Entry<PolicyId, PolicyRenewalId>, String> scheduledRenewals) {
    this.taskScheduler = taskScheduler;
    this.scheduledRenewals = scheduledRenewals;
  }

  @Override
  public void scheduleRenewal(
      PolicyAppService policyAppService,
      PolicyId policyId,
      PolicyRenewalId policyRenewalId,
      DateTime renewalEffectiveDate) {
    String taskId =
        taskScheduler.schedule(
            new PolicyRenewalTask(policyAppService, policyId, policyRenewalId),
            renewalEffectiveDate);
    scheduledRenewals.put(getKey(policyId, policyRenewalId), taskId);
  }

  @Override
  public void cancelRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    String taskId = scheduledRenewals.get(getKey(policyId, policyRenewalId));
    taskScheduler.cancel(taskId);
  }

  @Override
  public void completeRenewal(PolicyId policyId, PolicyRenewalId policyRenewalId) {
    scheduledRenewals.remove(getKey(policyId, policyRenewalId));
  }

  private AbstractMap.SimpleEntry<PolicyId, PolicyRenewalId> getKey(
      PolicyId policyId, PolicyRenewalId policyRenewalId) {
    return new AbstractMap.SimpleEntry<>(policyId, policyRenewalId);
  }
}
