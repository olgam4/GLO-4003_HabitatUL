package ca.ulaval.glo4003.coverage.helper.form.identity;

import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;

import static ca.ulaval.glo4003.coverage.helper.form.identity.UniversityProgramGenerator.*;

public class UniversityProgramBuilder {
  private final Cycle DEFAULT_CYCLE = createCycle();
  private final Degree DEFAULT_DEGREE = createDegree();
  private final String DEFAULT_MAJOR = createMajor();

  private Cycle cycle = DEFAULT_CYCLE;
  private Degree degree = DEFAULT_DEGREE;
  private String major = DEFAULT_MAJOR;

  private UniversityProgramBuilder() {}

  public static UniversityProgramBuilder aUniversityProgram() {
    return new UniversityProgramBuilder();
  }

  public UniversityProgramBuilder withCycle(Cycle cycle) {
    this.cycle = cycle;
    return this;
  }

  public UniversityProgramBuilder withDegree(Degree degree) {
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
