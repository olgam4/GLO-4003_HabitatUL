package ca.ulaval.glo4003.shared.infrastructure.threading;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.threading.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import java.util.*;
import java.util.logging.Logger;

import static java.time.ZoneOffset.UTC;

public class JavaTimerTaskScheduler implements TaskScheduler {
  private Timer timer;
  private Map<Comparable, TimerTask> tasks;
  private ClockProvider clockProvider;
  private Logger logger;

  public JavaTimerTaskScheduler() {
    this(
        new Timer(),
        new HashMap<>(),
        ServiceLocator.resolve(ClockProvider.class),
        ServiceLocator.resolve(Logger.class));
  }

  public JavaTimerTaskScheduler(
      Timer timer, Map<Comparable, TimerTask> tasks, ClockProvider clockProvider, Logger logger) {
    this.timer = timer;
    this.tasks = tasks;
    this.clockProvider = clockProvider;
    this.logger = logger;
  }

  @Override
  public void schedule(Comparable taskId, Runnable runnable, DateTime scheduledProcessingDateTime) {
    TimerTask timerTask = createTimerTask(taskId, runnable);
    tasks.put(taskId, timerTask);
    long delay = computeDelay(scheduledProcessingDateTime);
    timer.schedule(timerTask, delay);
  }

  private TimerTask createTimerTask(Comparable taskId, Runnable runnable) {
    return new TimerTask() {
      @Override
      public void run() {
        try {
          runnable.run();
        } catch (Throwable e) {
          logger.severe(String.format("Error in TimerTask execution: <%s>", e.toString()));
        } finally {
          tasks.remove(taskId);
        }
      }
    };
  }

  private long computeDelay(DateTime scheduledProcessingDateTime) {
    long currentEpochMilli =
        DateTime.now(clockProvider.getClock()).getValue().toInstant(UTC).toEpochMilli();
    long scheduledEpochMilli = scheduledProcessingDateTime.getValue().toInstant(UTC).toEpochMilli();
    return scheduledEpochMilli - currentEpochMilli;
  }

  @Override
  public void cancel(Comparable taskId) {
    Optional.ofNullable(tasks.remove(taskId)).map(TimerTask::cancel);
  }
}
