package ca.ulaval.glo4003.gateway.presentation.coverage.request;

import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;

import javax.validation.constraints.NotNull;

public class UniversityProgramRequest {
  @NotNull private Cycle cycle;
  @NotNull private Degree degree;
  @NotNull private String major;

  private UniversityProgramRequest() {}

  public UniversityProgramRequest(Cycle cycle, Degree degree, String major) {
    this.cycle = cycle;
    this.degree = degree;
    this.major = major;
  }

  public Cycle getCycle() {
    return cycle;
  }

  public Degree getDegree() {
    return degree;
  }

  public String getMajor() {
    return major;
  }
}
