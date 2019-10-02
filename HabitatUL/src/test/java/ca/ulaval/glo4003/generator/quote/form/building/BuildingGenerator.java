package ca.ulaval.glo4003.generator.quote.form.building;

import ca.ulaval.glo4003.gateway.presentation.quote.request.BuildingView;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;
import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

public class BuildingGenerator {
  private static int MIN_NUMBER_OF_UNITS = 2;
  private static int MAX_NUMBER_OF_UNITS = 200;

  public static BuildingView createBuildingView() {
    return new BuildingView(
        Faker.instance().number().numberBetween(MIN_NUMBER_OF_UNITS, MAX_NUMBER_OF_UNITS),
        new HashSet<>(Arrays.asList(PreventionSystem.SPRINKLER)),
        Optional.empty());
  }

  public static Building createBuilding() {
    return new Building(
        Faker.instance().number().numberBetween(MIN_NUMBER_OF_UNITS, MAX_NUMBER_OF_UNITS),
        new HashSet<>(Arrays.asList(PreventionSystem.SPRINKLER)),
        Optional.empty());
  }
}
