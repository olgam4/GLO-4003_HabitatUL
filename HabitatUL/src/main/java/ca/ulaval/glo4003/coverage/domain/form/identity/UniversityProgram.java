package ca.ulaval.glo4003.coverage.domain.form.identity;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.shared.domain.identity.Cycle;
import ca.ulaval.glo4003.shared.domain.identity.Degree;

public class UniversityProgram extends ValueObject {
  public static final UniversityProgram UNFILLED_UNIVERSITY_PROGRAM =
      new UniversityProgram(null, null, null);

  private final Cycle cycle;
  private final Degree degree;
  private final String major;

  public UniversityProgram(Cycle cycle, Degree degree, String major) {
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

  public boolean isFilled() {
    return !equals(UNFILLED_UNIVERSITY_PROGRAM);
  }
}
