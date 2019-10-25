package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class UniversityProfile extends ValueObject {
  public static final UniversityProfile UNFILLED_UNIVERSITY_PROFILE =
      new UniversityProfile(null, null, null, null, null);

  private final String idul;
  private final String identificationNumber;
  private final String cycle;
  private final String degree;
  private final String program;

  public UniversityProfile(
      String idul, String identificationNumber, String cycle, String degree, String program) {
    this.idul = idul;
    this.identificationNumber = identificationNumber;
    this.cycle = cycle;
    this.degree = degree;
    this.program = program;
  }

  public String getIdul() {
    return idul;
  }

  public String getIdentificationNumber() {
    return identificationNumber;
  }

  public String getCycle() {
    return cycle;
  }

  public String getDegree() {
    return degree;
  }

  public String getProgram() {
    return program;
  }

  public boolean isFilled() {
    return !equals(UNFILLED_UNIVERSITY_PROFILE);
  }
}
