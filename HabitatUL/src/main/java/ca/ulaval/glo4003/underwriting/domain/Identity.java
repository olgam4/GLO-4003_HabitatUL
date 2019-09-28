package ca.ulaval.glo4003.underwriting.domain;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Identity extends ValueObject {
  private String firstName;
  private String lastName;

  public Identity(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }
}
