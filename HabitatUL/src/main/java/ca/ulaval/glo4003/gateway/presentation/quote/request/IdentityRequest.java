package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;

public class IdentityRequest {
  private String firstName;
  private String lastName;
  private Date birthDate;
  private Gender gender;

  private IdentityRequest() {}

  public IdentityRequest(String firstName, String lastName, Date birthDate, Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.gender = gender;
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
}
