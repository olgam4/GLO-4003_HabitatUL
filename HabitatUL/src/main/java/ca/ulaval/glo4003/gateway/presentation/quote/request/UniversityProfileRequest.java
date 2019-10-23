package ca.ulaval.glo4003.gateway.presentation.quote.request;

public class UniversityProfileRequest {
  private String idul;
  private String ni;
  private String cycle;
  private String degree;
  private String program;

  private UniversityProfileRequest() {}

  public UniversityProfileRequest(
      String idul, String ni, String cycle, String degree, String program) {
    this.idul = idul;
    this.ni = ni;
    this.cycle = cycle;
    this.degree = degree;
    this.program = program;
  }

  public String getIdul() {
    return idul;
  }

  public String getNi() {
    return ni;
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
}
