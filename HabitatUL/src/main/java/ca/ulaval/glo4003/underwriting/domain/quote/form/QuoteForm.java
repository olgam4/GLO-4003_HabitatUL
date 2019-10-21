package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiability;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;

public class QuoteForm {
  private Identity personalInformation;
  private Identity additionalInsured;
  private Location location;
  private Date effectiveDate;
  private Building building;
  private PersonalProperty personalProperty;
  private CivilLiability civilLiability;

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
    this.civilLiability = civilLiability;
    completeWithDefaultValues();
  }

  private void completeWithDefaultValues() {
    civilLiability.completeWithDefaultValues(building.getNumberOfUnits());
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
