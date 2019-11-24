package ca.ulaval.glo4003.shared.application.threading;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.logging.Logger;

public class TaskSchedulerLoggingDecorator implements TaskScheduler {
  private TaskScheduler taskScheduler;
  private Logger logger;

  public TaskSchedulerLoggingDecorator(TaskScheduler taskScheduler) {
    this(taskScheduler, ServiceLocator.resolve(Logger.class));
  }

  public TaskSchedulerLoggingDecorator(TaskScheduler taskScheduler, Logger logger) {
    this.taskScheduler = taskScheduler;
    this.logger = logger;
  }

  @Override
  public void schedule(Comparable taskId, Runnable runnable, DateTime scheduledProcessingDateTime) {
    logger.info(
        String.format(
            "Scheduling task <%s> at <%s>", taskId, scheduledProcessingDateTime.getValue()));
    taskScheduler.schedule(taskId, runnable, scheduledProcessingDateTime);
  }

  @Override
  public void cancel(Comparable taskId) {
    logger.info(String.format("Canceling task <%s>", taskId));
    taskScheduler.cancel(taskId);
  }
}
