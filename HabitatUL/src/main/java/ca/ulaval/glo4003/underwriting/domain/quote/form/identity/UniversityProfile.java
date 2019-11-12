package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram;
import ca.ulaval.glo4003.shared.domain.ValueObject;

import static ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram.UNFILLED_UNIVERSITY_PROGRAM;

public class UniversityProfile extends ValueObject {
  public static final UniversityProfile UNFILLED_UNIVERSITY_PROFILE =
      new UniversityProfile(null, null, UNFILLED_UNIVERSITY_PROGRAM);

  private final String idul;
  private final String identificationNumber;
  private final UniversityProgram program;

  public UniversityProfile(String idul, String identificationNumber, UniversityProgram program) {
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

  public UniversityProgram getProgram() {
    return program;
  }

  public boolean isFilled() {
    return !equals(UNFILLED_UNIVERSITY_PROFILE);
  }
}
