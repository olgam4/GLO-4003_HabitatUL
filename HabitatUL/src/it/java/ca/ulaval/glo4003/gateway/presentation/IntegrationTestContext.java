package ca.ulaval.glo4003.gateway.presentation;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import ca.ulaval.glo4003.shared.domain.address.FloorFormatter;
import ca.ulaval.glo4003.shared.domain.address.ZipCodeFormatter;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberFormatter;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumberParser;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.presentation.temporal.LocalZoneIdProvider;
import com.github.javafaker.Faker;

import java.time.ZoneId;
import java.util.logging.Level;

import static ca.ulaval.glo4003.context.ServiceLocator.register;
import static ca.ulaval.glo4003.helper.shared.LoggingGenerator.createNullLogger;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createDate;

public class IntegrationTestContext {
  public static final String VALID_AUTHORITY_NUMBER_VALUE = "QUE111111001";
  public static final String VALID_FLOOR_VALUE = "RC";
  public static final String VALID_ZIP_CODE_VALUE = "G3A 0G4";

  public IntegrationTestContext() {
    ServiceLocator.reset();
  }

  private static void disableLogging() {
    java.util.logging.Logger.getLogger("org.glassfish.jersey.internal.inject.Providers")
        .setLevel(Level.SEVERE);
    System.setProperty("org.eclipse.jetty.util.log.class", "org.eclipse.jetty.util.log.StdErrLog");
    System.setProperty("org.eclipse.jetty.LEVEL", "OFF");
  }

  private static AuthorityNumberFormatter getDummyAuthorityNumberFormatter() {
    return authorityNumberValue -> {
      if (authorityNumberValue.equals(VALID_AUTHORITY_NUMBER_VALUE))
        return VALID_AUTHORITY_NUMBER_VALUE;
      throw new InvalidArgumentException();
    };
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

  public void execute() {
    disableLogging();
    registerServices();
  }

  private void registerServices() {
    register(Logger.class, createNullLogger());
    register(AuthorityNumberFormatter.class, getDummyAuthorityNumberFormatter());
    register(AuthorityNumberParser.class, number -> createDate());
    register(FloorFormatter.class, getDummyFloorFormatter());
    register(LocalZoneIdProvider.class, () -> ZoneId.of("UTC"));
    register(ZipCodeFormatter.class, getDummyZipCodeFormatter());
  }
}
