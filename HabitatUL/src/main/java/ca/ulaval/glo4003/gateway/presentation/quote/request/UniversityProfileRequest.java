package ca.ulaval.glo4003.gateway.presentation.quote.request;

public class UniversityProfileRequest {
  private String idul;
  private String ni;
  private String program;

  private UniversityProfileRequest() {}

  public UniversityProfileRequest(String idul, String ni, String program) {
    this.idul = idul;
    this.ni = ni;
    this.program = program;
  }

  public String getIdul() {
    return idul;
  }

  public String getNi() {
    return ni;
  }

  public String getProgram() {
    return program;
  }
}
