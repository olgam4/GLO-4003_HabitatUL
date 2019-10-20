package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.BuildingRequest;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

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

  public static Building createBuildingWithNumberOfUnits(int numberOfUnits) {
    return new Building(numberOfUnits, createPreventionSystems(), createCommercialUse());
  }

  private static int createNumberOfUnits() {
    return Faker.instance().number().numberBetween(MIN_NUMBER_OF_UNITS, MAX_NUMBER_OF_UNITS);
  }

  private static HashSet<PreventionSystem> createPreventionSystems() {
    return new HashSet<>(Arrays.asList(PreventionSystem.SPRINKLER));
  }

  private static Optional<String> createCommercialUse() {
    return Optional.of(Faker.instance().zelda().game());
  }
}
