package ca.ulaval.glo4003.helper.coverage.form.identity;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;

import static ca.ulaval.glo4003.helper.coverage.form.identity.UniversityProgramGenerator.*;

public class UniversityProgramBuilder {
  private final String DEFAULT_CYCLE = createCycle();
  private final String DEFAULT_DEGREE = createDegree();
  private final String DEFAULT_MAJOR = createMajor();

  private String cycle = DEFAULT_CYCLE;
  private String degree = DEFAULT_DEGREE;
  private String major = DEFAULT_MAJOR;

  private UniversityProgramBuilder() {}

  public static UniversityProgramBuilder aUniversityProgram() {
    return new UniversityProgramBuilder();
  }

  public UniversityProgramBuilder withCycle(String cycle) {
    this.cycle = cycle;
    return this;
  }

  public UniversityProgramBuilder withDegree(String degree) {
    this.degree = degree;
    return this;
  }

  public UniversityProgramBuilder withMajor(String major) {
    this.major = major;
    return this;
  }

  public UniversityProgram build() {
    return new UniversityProgram(cycle, degree, major);
  }
}
