package ca.ulaval.glo4003.coverage.domain.coverage;

public enum CoverageCategory {
  PERSONAL_PROPERTY("personal property"),
  CIVIL_LIABILITY("civil liability"),
  BICYCLE_ENDORSEMENT("bicycle endorsement");

  private final String name;

  CoverageCategory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
