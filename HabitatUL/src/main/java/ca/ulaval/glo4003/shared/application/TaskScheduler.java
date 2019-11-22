package ca.ulaval.glo4003.shared.application;

import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

public interface TaskScheduler {
  // TODO: add logger wrapper
  String schedule(Runnable runnable, DateTime scheduledProcessingDateTime);

  void cancel(String taskId);
}
