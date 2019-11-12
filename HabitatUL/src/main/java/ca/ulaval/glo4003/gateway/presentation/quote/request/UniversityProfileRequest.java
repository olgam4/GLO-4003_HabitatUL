package ca.ulaval.glo4003.gateway.presentation.quote.request;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UniversityProfileRequest {
  @NotNull private String idul;
  @NotNull private String ni;
  @NotNull @Valid private UniversityProgramRequest program;

  private UniversityProfileRequest() {}

  public UniversityProfileRequest(String idul, String ni, UniversityProgramRequest program) {
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

  public UniversityProgramRequest getProgram() {
    return program;
  }
}
