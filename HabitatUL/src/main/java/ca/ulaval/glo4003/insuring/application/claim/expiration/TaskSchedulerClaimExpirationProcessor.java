package ca.ulaval.glo4003.insuring.application.claim.expiration;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public class TaskSchedulerClaimExpirationProcessor implements ClaimExpirationProcessor {
  // TODO: add logger wrapper
  private TaskScheduler taskScheduler;
  private ClaimRepository claimRepository;

  public TaskSchedulerClaimExpirationProcessor() {
    this(
        ServiceLocator.resolve(TaskScheduler.class), ServiceLocator.resolve(ClaimRepository.class));
  }

  public TaskSchedulerClaimExpirationProcessor(
      TaskScheduler taskScheduler, ClaimRepository claimRepository) {
    this.taskScheduler = taskScheduler;
    this.claimRepository = claimRepository;
  }

  @Override
  public void scheduleExpiration(ClaimId claimId, DateTime expirationDateTime) {
    ClaimExpirationTask claimExpirationTask = new ClaimExpirationTask(claimRepository, claimId);
    taskScheduler.schedule(claimId, claimExpirationTask, expirationDateTime);
  }

  @Override
  public void cancelExpiration(ClaimId claimId) {
    taskScheduler.cancel(claimId);
  }
}
