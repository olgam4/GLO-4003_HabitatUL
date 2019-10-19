package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.building.Building;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.underwriting.domain.quote.form.studentinformation.StudentInformation;

public class QuoteForm {
  private Identity identity;
  private Location location;
  private Date effectiveDate;
  private Building building;
  private PersonalProperty personalProperty;
  private StudentInformation studentInformation;

  public QuoteForm(
      Identity identity,
      Location location,
      Date effectiveDate,
      Building building,
      PersonalProperty personalProperty,
      StudentInformation studentInformation) {
    this.identity = identity;
    this.location = location;
    this.effectiveDate = effectiveDate;
    this.building = building;
    this.personalProperty = personalProperty;
    this.studentInformation = studentInformation;
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

  public StudentInformation getStudentInformation() {
    return studentInformation;
  }
}
