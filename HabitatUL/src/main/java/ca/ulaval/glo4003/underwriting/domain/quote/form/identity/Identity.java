package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Identity extends ValueObject {
  private String firstName;
  private String lastName;
  private Date birthDate;
  private Gender gender;

  public Identity(String firstName, String lastName, Date birthDate, Gender gender) {
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
