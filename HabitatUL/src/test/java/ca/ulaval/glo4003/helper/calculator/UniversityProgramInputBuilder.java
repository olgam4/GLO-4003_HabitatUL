package ca.ulaval.glo4003.helper.calculator;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;

import static ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator.*;

public class UniversityProgramInputBuilder {
  private static final String DEFAULT_CYCLE = createCycle();
  private static final String DEFAULT_DEGREE = createDegree();
  private static final String DEFAULT_PROGRAM = createProgram();

  private String cycle = DEFAULT_CYCLE;
  private String degree = DEFAULT_DEGREE;
  private String program = DEFAULT_PROGRAM;

  private UniversityProgramInputBuilder() {}

  public static UniversityProgramInputBuilder aUniversityProgramInput() {
    return new UniversityProgramInputBuilder();
  }

  public UniversityProgramInputBuilder withCycle(String cycle) {
    this.cycle = cycle;
    return this;
  }

  public UniversityProgramInputBuilder withDegree(String degree) {
    this.degree = degree;
    return this;
  }

  public UniversityProgramInputBuilder withProgram(String program) {
    this.program = program;
    return this;
  }

  public UniversityProgramInput build() {
    return new UniversityProgramInput(cycle, degree, program);
  }
}
