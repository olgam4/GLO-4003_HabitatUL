package ca.ulaval.glo4003.underwriting.domain.quote.form.identity;

public class UniversityProfile {
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
}
