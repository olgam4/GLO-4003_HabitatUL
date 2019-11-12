package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.premium.formula.input.UniversityProgram;

import static ca.ulaval.glo4003.helper.calculator.UniversityProgramGenerator.*;

public class UniversityProgramBuilder {
  private static final String DEFAULT_CYCLE = createCycle();
  private static final String DEFAULT_DEGREE = createDegree();
  private static final String DEFAULT_MAJOR = createMajor();

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
