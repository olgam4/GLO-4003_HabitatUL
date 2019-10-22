package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.PreventionSystem;

import java.util.Optional;
import java.util.Set;

import static ca.ulaval.glo4003.helper.quote.form.BuildingGenerator.*;

public class BuildingBuilder {
  private static final int DEFAULT_NUMBER_OF_UNITS = createNumberOfUnits();
  private static final Set<PreventionSystem> DEFAULT_PREVENTION_SYSTEMS = createPreventionSystems();
  private static final Optional<String> DEFAULT_COMMERCIAL_USE = createCommercialUse();

  private int numberOfUnits = DEFAULT_NUMBER_OF_UNITS;
  private Set<PreventionSystem> preventionSystems = DEFAULT_PREVENTION_SYSTEMS;
  private Optional<String> commercialUse = DEFAULT_COMMERCIAL_USE;

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
