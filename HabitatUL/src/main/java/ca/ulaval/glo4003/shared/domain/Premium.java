package ca.ulaval.glo4003.shared.domain;

import java.math.BigDecimal;

public class Premium extends ValueObject {
  private BigDecimal value;

  public Premium(BigDecimal value) {
    this.value = value;
  }
}
