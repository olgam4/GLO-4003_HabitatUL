package ca.ulaval.glo4003.coverage.domain.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

public class Identity extends ValueObject {
  public static final Identity UNFILLED_IDENTITY =
      new Identity(null, null, null, null, UniversityProfile.UNFILLED_UNIVERSITY_PROFILE);

  private final String firstName;
  private final String lastName;
  private final Date birthDate;
  private final Gender gender;
  private final UniversityProfile universityProfile;

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

  public boolean isFilled() {
    return !equals(UNFILLED_IDENTITY);
  }
}
