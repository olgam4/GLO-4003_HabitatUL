package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class UniversityProfile extends ValueObject {
  public static final UniversityProfile UNFILLED_UNIVERSITY_PROFILE =
      new UniversityProfile(null, null, null);

  private String idul;
  private String identificationNumber;
  private String program;

  public UniversityProfile(String idul, String identificationNumber, String program) {
    this.idul = idul;
    this.identificationNumber = identificationNumber;
    this.program = program;
  }

  public String getIdul() {
    return idul;
  }

  public String getIdentificationNumber() {
    return identificationNumber;
  }

  public String getProgram() {
    return program;
  }

  public boolean isFilled() {
    return !equals(UNFILLED_UNIVERSITY_PROFILE);
  }
}
