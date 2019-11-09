package ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class UniversityProgramInput extends ValueObject {
  private final String cycle;
  private final String degree;
  private final String program;

  public UniversityProgramInput(String cycle, String degree, String program) {
    this.cycle = cycle;
    this.degree = degree;
    this.program = program;
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

  public boolean isCompleted() {
    return cycle != null && degree != null && program != null;
  }
}
