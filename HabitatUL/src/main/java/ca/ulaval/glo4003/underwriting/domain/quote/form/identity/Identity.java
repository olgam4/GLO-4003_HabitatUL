package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class Identity extends ValueObject {
  private String firstName;
  private String lastName;
  private Date birthDate;
  private Gender gender;
  private UniversityProfile universityProfile;

  public Identity(
      String firstName,
      String lastName,
      Date birthDate,
      Gender gender,
      UniversityProfile universityProfile) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.gender = gender;
    this.universityProfile = universityProfile;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Date getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }

  public UniversityProfile getUniversityProfile() {
    return universityProfile;
  }
}
