package ca.ulaval.glo4003.calculator.domain;

public enum Coverage {
  BASIC_BLOCK("basic block"),
  BIKE_ENDORSEMENT("bike endorsement"),
  OTHER("other");

  private final String name;

  Coverage(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
