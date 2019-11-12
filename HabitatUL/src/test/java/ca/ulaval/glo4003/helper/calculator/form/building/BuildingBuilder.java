package ca.ulaval.glo4003.helper.calculator.form.building;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.building.PreventionSystems;

import static ca.ulaval.glo4003.helper.calculator.form.building.BuildingGenerator.*;

public class BuildingBuilder {
  private static final int DEFAULT_NUMBER_OF_UNITS = createNumberOfUnits();
  private static final PreventionSystems DEFAULT_PREVENTION_SYSTEMS = createPreventionSystems();
  private static final String DEFAULT_COMMERCIAL_USE = createCommercialUse();

  private int numberOfUnits = DEFAULT_NUMBER_OF_UNITS;
  private PreventionSystems preventionSystems = DEFAULT_PREVENTION_SYSTEMS;
  private String commercialUse = DEFAULT_COMMERCIAL_USE;

  private BuildingBuilder() {}

  public static BuildingBuilder aBuilding() {
    return new BuildingBuilder();
  }

  public BuildingBuilder withNumberOfUnits(int numberOfUnits) {
    this.numberOfUnits = numberOfUnits;
    return this;
  }

  public Building build() {
    return new Building(numberOfUnits, preventionSystems, commercialUse);
  }
}
