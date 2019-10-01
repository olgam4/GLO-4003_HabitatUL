package ca.ulaval.glo4003.gateway.presentation.quote.request;

import ca.ulaval.glo4003.shared.domain.DateTime;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;

public class IdentityView {
  private String firstName;
  private String lastName;
  private DateTime birthDate;
  private Gender gender;

  public IdentityView(String firstName, String lastName, DateTime birthDate, Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.gender = gender;
  }

  // IMPORTANT - KEEP FOR JACKSON SERIALIZATION
  private IdentityView() {}

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public DateTime getBirthDate() {
    return birthDate;
  }

  public Gender getGender() {
    return gender;
  }
}