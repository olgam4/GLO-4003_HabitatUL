package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class PolicyInformation extends ValueObject {
  private final Identity namedInsuredIdentity;
  private final Identity additionalInsuredIdentity;
  private final Location location;
  private final Date effectiveDate;
  private final Building building;
  private final PersonalProperty personalProperty;
  private final CivilLiability civilLiability;

  public PolicyInformation(
      Identity namedInsuredIdentity,
      Identity additionalInsuredIdentity,
      Location location,
      Date effectiveDate,
      Building building,
      PersonalProperty personalProperty,
      CivilLiability civilLiability) {
    this.namedInsuredIdentity = namedInsuredIdentity;
    this.additionalInsuredIdentity = additionalInsuredIdentity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability;
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

  public Date getEffectiveDate() {
    return effectiveDate;
  }

  public Building getBuilding() {
    return building;
  }

  public PersonalProperty getPersonalProperty() {
    return personalProperty;
  }

  public CivilLiability getCivilLiability() {
    return civilLiability;
  }
}
