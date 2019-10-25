package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.BuildingRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashSet;

public class BuildingGenerator {
  private static int MIN_NUMBER_OF_UNITS = 1;
  private static int MAX_NUMBER_OF_UNITS = 200;

  public static BuildingRequest createBuildingRequest() {
    return new BuildingRequest(
        createNumberOfUnits(), createPreventionSystems(), createCommercialUse());
  }

  public static Building createBuilding() {
    return new Building(createNumberOfUnits(), createPreventionSystems(), createCommercialUse());
  }

  public static int createNumberOfUnits() {
    return Faker.instance().number().numberBetween(MIN_NUMBER_OF_UNITS, MAX_NUMBER_OF_UNITS);
  }

  public static HashSet<PreventionSystem> createPreventionSystems() {
    return new HashSet<>(Arrays.asList(PreventionSystem.SPRINKLER));
  }

  public static String createCommercialUse() {
    return Faker.instance().zelda().game();
  }
}
