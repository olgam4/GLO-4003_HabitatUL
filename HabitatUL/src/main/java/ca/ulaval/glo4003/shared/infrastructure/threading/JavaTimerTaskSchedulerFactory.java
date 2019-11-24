package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.application.threading.TaskSchedulerFactory;
import ca.ulaval.glo4003.shared.application.threading.TaskSchedulerLoggingDecorator;

public class JavaTimerTaskSchedulerFactory implements TaskSchedulerFactory {
  @Override
  public TaskScheduler create() {
    return new TaskSchedulerLoggingDecorator(new JavaTimerTaskScheduler());
  }
}
