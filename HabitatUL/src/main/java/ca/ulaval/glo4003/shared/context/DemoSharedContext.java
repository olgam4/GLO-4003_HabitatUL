package ca.ulaval.glo4003.shared.context;

import ca.ulaval.glo4003.shared.application.TaskScheduler;
import ca.ulaval.glo4003.shared.domain.address.FloorFormatter;
import ca.ulaval.glo4003.shared.domain.address.ZipCodeFormatter;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.address.CanadianZipCodeFormatter;
import ca.ulaval.glo4003.shared.infrastructure.address.UsCanadianConventionFloorFormatter;
import ca.ulaval.glo4003.shared.infrastructure.temporal.SystemDefaultZoneClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.threading.JavaTimerTaskScheduler;
import ca.ulaval.glo4003.shared.presentation.temporal.ConfigBasedLocalZoneIdProvider;
import ca.ulaval.glo4003.shared.presentation.temporal.LocalZoneIdProvider;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoSharedContext {
  public void execute() {
    register(ClockProvider.class, new SystemDefaultZoneClockProvider());
    register(FloorFormatter.class, new UsCanadianConventionFloorFormatter());
    register(ZipCodeFormatter.class, new CanadianZipCodeFormatter());
    register(LocalZoneIdProvider.class, new ConfigBasedLocalZoneIdProvider());
    register(TaskScheduler.class, new JavaTimerTaskScheduler());
  }
}
