package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.*;

public class UniversityProfileBuilder {
  private static final String DEFAULT_IDUL = createIdul();
  private static final String DEFAULT_IDENTIFICATION_NUMBER = createIdentificationNumber();
  private static final String DEFAULT_PROGRAM = createProgram();

  private String idul = DEFAULT_IDUL;
  private String identificationNumber = DEFAULT_IDENTIFICATION_NUMBER;
  private String program = DEFAULT_PROGRAM;

  private UniversityProfileBuilder() {}

  public static UniversityProfileBuilder aUniversity() {
    return new UniversityProfileBuilder();
  }

  public UniversityProfileBuilder withProgram(String program) {
    this.program = program;
    return this;
  }

  public UniversityProfile build() {
    return new UniversityProfile(idul, identificationNumber, program);
  }
}
