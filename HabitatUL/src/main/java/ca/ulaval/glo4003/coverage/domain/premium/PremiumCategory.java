package ca.ulaval.glo4003.coverage.domain.premium;

public enum PremiumCategory {
  BASIC_BLOCK("basic block"),
  BICYCLE_ENDORSEMENT("bicycle endorsement");

  private final String name;

  PremiumCategory(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
