package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import javax.validation.constraints.NotNull;

public class UniversityProgramRequest {
  @NotNull private String cycle;
  @NotNull private String degree;
  @NotNull private String major;

  private UniversityProgramRequest() {}

  public UniversityProgramRequest(String cycle, String degree, String major) {
    this.cycle = cycle;
    this.degree = degree;
    this.major = major;
  }

  public String getCycle() {
    return cycle;
  }

  public String getDegree() {
    return degree;
  }

  public String getMajor() {
    return major;
  }
}
