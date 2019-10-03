package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

public class QuoteForm extends ValueObject {
  private Identity identity;
  private Location location;
  private Date effectiveDate;
  private Building building;
  private PersonalProperty personalProperty;

  public QuoteForm(
      Identity identity,
      Location location,
      Date effectiveDate,
      Building building,
      PersonalProperty personalProperty) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
  }

  public Identity getIdentity() {
    return identity;
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
}
