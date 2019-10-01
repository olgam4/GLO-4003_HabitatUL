package ca.ulaval.glo4003.underwriting.domain.premium;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.math.BigDecimal;

public class Premium extends ValueObject {
  private BigDecimal value;

  public Premium(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal getValue() {
    return value;
  }
}
