package ca.ulaval.glo4003.coverage.domain.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class UniversityProgram extends ValueObject {
  public static final UniversityProgram UNFILLED_UNIVERSITY_PROGRAM =
      new UniversityProgram(null, null, null);

  private final String cycle;
  private final String degree;
  private final String major;

  public UniversityProgram(String cycle, String degree, String major) {
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

  public boolean isFilled() {
    return !equals(UNFILLED_UNIVERSITY_PROGRAM);
  }
}
