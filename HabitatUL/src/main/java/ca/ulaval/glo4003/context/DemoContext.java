package ca.ulaval.glo4003.context;

import ca.ulaval.glo4003.administration.context.DemoAdministrationContext;
import ca.ulaval.glo4003.coverage.context.DemoCoverageContext;
import ca.ulaval.glo4003.insuring.context.DemoInsuringContext;
import ca.ulaval.glo4003.mediator.Mediator;
import ca.ulaval.glo4003.shared.context.DemoSharedContext;
import ca.ulaval.glo4003.shared.infrastructure.io.ConfigFileReader;
import ca.ulaval.glo4003.underwriting.context.DemoUnderwritingContext;

import java.util.Properties;

public class DemoContext {
  private Properties properties;
  private Mediator mediator;

  public DemoContext() {
    ServiceLocator.reset();
    properties = new ConfigFileReader().read("config.properties");
    mediator = new Mediator();
  }

  public void execute() {
    MediatorChanneler.registerChannels(mediator);
    new DemoSharedContext().execute();
    new DemoCoverageContext().execute();
    new DemoAdministrationContext().execute(properties, mediator);
    new DemoUnderwritingContext().execute(mediator);
    new DemoInsuringContext().execute(mediator);
  }
}
