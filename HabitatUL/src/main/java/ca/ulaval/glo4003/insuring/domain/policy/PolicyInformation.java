package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class PolicyInformation extends ValueObject {
  private final Identity namedInsuredIdentity;
  private final Identity additionalInsuredIdentity;
  private final Location location;
  private final Building building;
  private final Animals animals;
  private final Bicycle bicycle;

  public PolicyInformation(
      Identity namedInsuredIdentity,
      Identity additionalInsuredIdentity,
      Location location,
      Building building,
      Animals animals,
      Bicycle bicycle) {
    this.namedInsuredIdentity = namedInsuredIdentity;
    this.additionalInsuredIdentity = additionalInsuredIdentity;
    this.location = location;
    this.building = building;
    this.animals = animals;
    this.bicycle = bicycle;
  }

  public Identity getNamedInsuredIdentity() {
    return namedInsuredIdentity;
  }

  public Identity getAdditionalInsuredIdentity() {
    return additionalInsuredIdentity;
  }

  public Location getLocation() {
    return location;
  }

  public Building getBuilding() {
    return building;
  }

  public Animals getAnimals() {
    return animals;
  }

  public Bicycle getBicycle() {
    return bicycle;
  }
}
