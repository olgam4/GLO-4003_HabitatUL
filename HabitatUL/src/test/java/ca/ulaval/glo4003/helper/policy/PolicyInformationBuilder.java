package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

import static ca.ulaval.glo4003.helper.coverage.form.building.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.helper.coverage.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createAnimals;

public class PolicyInformationBuilder {
  private final Identity DEFAULT_NAMED_INSURED = createIdentity();
  private final Identity DEFAULT_ADDITIONAL_INSURED = createIdentity();
  private final Location DEFAULT_LOCATION = createLocation();
  private final Building DEFAULT_BUILDING = createBuilding();
  private final Animals DEFAULT_ANIMALS = createAnimals();
  private final Bicycle DEFAULT_BICYCLE = createBicycle();

  private Identity namedInsured = DEFAULT_NAMED_INSURED;
  private Identity additionalInsured = DEFAULT_ADDITIONAL_INSURED;
  private Location location = DEFAULT_LOCATION;
  private Building building = DEFAULT_BUILDING;
  private Animals animals = DEFAULT_ANIMALS;
  private Bicycle bicycle = DEFAULT_BICYCLE;

  private PolicyInformationBuilder() {}

  public static PolicyInformationBuilder aPolicyInformation() {
    return new PolicyInformationBuilder();
  }

  public PolicyInformationBuilder withNamedInsured(Identity namedInsured) {
    this.namedInsured = namedInsured;
    return this;
  }

  public PolicyInformationBuilder withAdditionalInsured(Identity additionalInsured) {
    this.additionalInsured = additionalInsured;
    return this;
  }

  public PolicyInformationBuilder withLocation(Location location) {
    this.location = location;
    return this;
  }

  public PolicyInformationBuilder withBuilding(Building building) {
    this.building = building;
    return this;
  }

  public PolicyInformationBuilder withAnimals(Animals animals) {
    this.animals = animals;
    return this;
  }

  public PolicyInformationBuilder withBicycle(Bicycle bicycle) {
    this.bicycle = bicycle;
    return this;
  }

  public PolicyInformation build() {
    return new PolicyInformation(
        namedInsured, additionalInsured, location, building, animals, bicycle);
  }
}
