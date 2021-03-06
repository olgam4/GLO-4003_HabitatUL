package ca.ulaval.glo4003.coverage.domain.form;

import ca.ulaval.glo4003.coverage.domain.form.building.Building;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.location.Location;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class QuoteForm extends ValueObject {
  private final Identity personalInformation;
  private final Identity additionalInsured;
  private final Location location;
  private final Date effectiveDate;
  private final Building building;
  private final PersonalProperty personalProperty;
  private final CivilLiability civilLiability;

  public QuoteForm(
      Identity personalInformation,
      Identity additionalInsured,
      Location location,
      Date effectiveDate,
      Building building,
      PersonalProperty personalProperty,
      CivilLiability civilLiability) {
    this.personalInformation = personalInformation;
    this.additionalInsured = additionalInsured;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
    this.civilLiability = civilLiability.completeWithDefaultValues(building.getNumberOfUnits());
  }

  public Identity getPersonalInformation() {
    return personalInformation;
  }

  public Identity getAdditionalInsured() {
    return additionalInsured;
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
