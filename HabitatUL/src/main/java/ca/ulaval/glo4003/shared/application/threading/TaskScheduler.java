package ca.ulaval.glo4003.shared.application.threading;

import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public interface TaskScheduler {
  void schedule(Comparable taskId, Runnable runnable, DateTime scheduledProcessingDateTime);

  void cancel(Comparable taskId);
}
