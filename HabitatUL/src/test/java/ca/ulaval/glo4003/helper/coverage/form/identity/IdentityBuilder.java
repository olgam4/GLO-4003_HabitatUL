package ca.ulaval.glo4003.helper.coverage.form.identity;

import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.shared.domain.identity.Gender;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator.*;
import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProfileGenerator.createUniversityProfile;

public class IdentityBuilder {
  private final String DEFAULT_FIRST_NAME = createFirstName();
  private final String DEFAULT_LAST_NAME = createLastName();
  private final Date DEFAULT_BIRTH_DATE = createBirthDate();
  private final Gender DEFAULT_GENDER = createGender();
  private final UniversityProfile DEFAULT_UNIVERSITY_PROFILE = createUniversityProfile();

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private Date birthDate = DEFAULT_BIRTH_DATE;
  private Gender gender = DEFAULT_GENDER;
  private UniversityProfile universityProfile = DEFAULT_UNIVERSITY_PROFILE;

  private IdentityBuilder() {}

  public static IdentityBuilder anIdentity() {
    return new IdentityBuilder();
  }

  public IdentityBuilder withUniversityProfile(UniversityProfile universityProfile) {
    this.universityProfile = universityProfile;
    return this;
  }

  public Identity build() {
    return new Identity(firstName, lastName, birthDate, gender, universityProfile);
  }
}
