package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Optional;

public class IdentityRequest {
  @NotNull private String firstName;
  @NotNull private String lastName;
  @NotNull private Date birthDate;
  @NotNull private Gender gender;
  @Valid private UniversityProfileRequest universityProfile;

  private IdentityRequest() {}

  public IdentityRequest(
      String firstName,
      String lastName,
      Date birthDate,
      Gender gender,
      UniversityProfileRequest universityProfile) {
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

  public Optional<UniversityProfileRequest> getUniversityProfile() {
    return Optional.ofNullable(universityProfile);
  }
}
