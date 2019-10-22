package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;

import static ca.ulaval.glo4003.helper.quote.form.IdentityGenerator.*;
import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.createUniversityProfile;

public class IdentityBuilder {
  private static final String DEFAULT_FIRST_NAME = createFirstName();
  private static final String DEFAULT_LAST_NAME = createLastName();
  private static final Date DEFAULT_BIRTH_DATE = createBirthDate();
  private static final Gender DEFAULT_GENDER = createGender();
  private static final UniversityProfile DEFAULT_UNIVERSITY_PROFILE = createUniversityProfile();

  private String firstName = DEFAULT_FIRST_NAME;
  private String lastName = DEFAULT_LAST_NAME;
  private Date birthDate = DEFAULT_BIRTH_DATE;
  private Gender gender = DEFAULT_GENDER;
  private UniversityProfile universityProfile = DEFAULT_UNIVERSITY_PROFILE;

  private IdentityBuilder() {}

  public static IdentityBuilder anIdentity() {
    return new IdentityBuilder();
  }

  public IdentityBuilder withoutUniversityProfile() {
    this.universityProfile = null;
    return this;
  }

  public IdentityBuilder withUniversityProfile(UniversityProfile universityProfile) {
    this.universityProfile = universityProfile;
    return this;
  }

  public Identity build() {
    return new Identity(firstName, lastName, birthDate, gender, universityProfile);
  }
}
