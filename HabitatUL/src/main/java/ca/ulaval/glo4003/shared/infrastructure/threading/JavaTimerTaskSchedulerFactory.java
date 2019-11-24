package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.application.threading.TaskSchedulerFactory;

public class JavaTimerTaskSchedulerFactory implements TaskSchedulerFactory {
  @Override
  public TaskScheduler create() {
    return new JavaTimerTaskScheduler();
  }
}
