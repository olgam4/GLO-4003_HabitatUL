package ca.ulaval.glo4003.coverage.domain;

public enum CoverageCategory {
  BASIC_BLOCK("basic block"),
  BIKE_ENDORSEMENT("bike endorsement"),
  CIVIL_LIABILITY("civil liability"),
  PERSONAL_PROPERTY("personal property"),
  OTHER("other");

  private final String name;

  CoverageCategory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
