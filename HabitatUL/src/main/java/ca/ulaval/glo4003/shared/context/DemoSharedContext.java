package ca.ulaval.glo4003.shared.context;

import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.application.threading.TaskSchedulerFactory;
import ca.ulaval.glo4003.shared.domain.address.FloorFormatter;
import ca.ulaval.glo4003.shared.domain.address.ZipCodeFormatter;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberFormatter;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberParser;
import ca.ulaval.glo4003.shared.domain.authority.SpvqAuthorityNumberFormatter;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.address.CanadianZipCodeFormatter;
import ca.ulaval.glo4003.shared.infrastructure.address.UsCanadianConventionFloorFormatter;
import ca.ulaval.glo4003.shared.infrastructure.logging.JavaUtilLogger;
import ca.ulaval.glo4003.shared.infrastructure.temporal.SystemDefaultZoneClockProvider;
import ca.ulaval.glo4003.shared.infrastructure.threading.JavaTimerTaskSchedulerFactory;
import ca.ulaval.glo4003.shared.presentation.temporal.ConfigBasedLocalZoneIdProvider;
import ca.ulaval.glo4003.shared.presentation.temporal.LocalZoneIdProvider;

import static ca.ulaval.glo4003.context.ServiceLocator.register;

public class DemoSharedContext {
  public void execute() {
    register(Logger.class, new JavaUtilLogger("Habitat-UL", "demo.log"));
    register(TaskSchedulerFactory.class, new JavaTimerTaskSchedulerFactory());
    SpvqAuthorityNumberFormatter spvqAuthorityNumberFormatter = new SpvqAuthorityNumberFormatter();
    register(AuthorityNumberFormatter.class, spvqAuthorityNumberFormatter);
    register(AuthorityNumberParser.class, spvqAuthorityNumberFormatter);
    register(ClockProvider.class, new SystemDefaultZoneClockProvider());
    register(FloorFormatter.class, new UsCanadianConventionFloorFormatter());
    register(LocalZoneIdProvider.class, new ConfigBasedLocalZoneIdProvider());
    register(ZipCodeFormatter.class, new CanadianZipCodeFormatter());
  }
}
