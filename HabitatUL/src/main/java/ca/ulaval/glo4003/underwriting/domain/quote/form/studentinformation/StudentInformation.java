package ca.ulaval.glo4003.underwriting.domain.quote.form.studentinformation;

public class StudentInformation {
  private String idul;
  private String identificationNumber;
  private String program;

  public StudentInformation(String idul, String identificationNumber, String program) {
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
