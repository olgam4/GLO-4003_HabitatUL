package ca.ulaval.glo4003.gateway.presentation;

import ca.ulaval.glo4003.context.Context;
import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.gateway.presentation.common.databind.LocalZoneIdProvider;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.FloorFormatter;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.ZipCodeFormatter;
import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IntegrationTestContext implements Context {
  public static final String VALID_FLOOR_VALUE = "RC";
  public static final String VALID_ZIP_CODE_VALUE = "G3A 0G4";

  public IntegrationTestContext() {
    ServiceLocator.reset();
  }

  private static void disableLogging() {
    Logger.getLogger("org.glassfish.jersey.internal.inject.Providers").setLevel(Level.SEVERE);
    System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
    System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
  }

  private static FloorFormatter getDummyFloorFormatter() {
    return floorValue -> {
      if (floorValue.equals(VALID_FLOOR_VALUE)) return Faker.instance().number().randomDigit();
      throw new InvalidArgumentException();
    };
  }

  private static ZipCodeFormatter getDummyZipCodeFormatter() {
    return zipCodeValue -> {
      if (zipCodeValue.equals(VALID_ZIP_CODE_VALUE)) return VALID_ZIP_CODE_VALUE;
      throw new InvalidArgumentException();
    };
  }

  @Override
  public void execute() {
    disableLogging();
    registerServices();
  }

  private void registerServices() {
    ServiceLocator.register(FloorFormatter.class, getDummyFloorFormatter());
    ServiceLocator.register(LocalZoneIdProvider.class, () -> ZoneId.of("UTC"));
    ServiceLocator.register(ZipCodeFormatter.class, getDummyZipCodeFormatter());
  }
}
