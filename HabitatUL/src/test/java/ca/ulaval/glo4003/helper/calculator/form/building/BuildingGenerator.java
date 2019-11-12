package ca.ulaval.glo4003.helper.calculator.form.building;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystem;
import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems;
import ca.ulaval.glo4003.gateway.presentation.quote.request.BuildingRequest;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import com.github.javafaker.Faker;

import java.util.HashSet;

public class BuildingGenerator {
  private static int MIN_NUMBER_OF_UNITS = 4;
  private static int MAX_NUMBER_OF_UNITS = 50;

  private BuildingGenerator() {}

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

  public static PreventionSystems createPreventionSystems() {
    return new PreventionSystems(
        new HashSet<>(
            EnumSampler.sample(
                PreventionSystem.class, Faker.instance().number().randomDigitNotZero())));
  }

  public static PreventionSystem createPreventionSystem() {
    return EnumSampler.sample(PreventionSystem.class);
  }

  public static String createCommercialUse() {
    return Faker.instance().zelda().game();
  }
}
