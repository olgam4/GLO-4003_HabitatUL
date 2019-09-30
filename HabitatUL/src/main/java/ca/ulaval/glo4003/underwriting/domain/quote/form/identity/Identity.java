package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Identity extends ValueObject {
  private String firstName;
  private String lastName;
  private Gender gender;

  public Identity(String firstName, String lastName, Gender gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.gender = gender;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public Gender getGender() {
    return gender;
  }
}
