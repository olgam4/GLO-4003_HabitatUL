package ca.ulaval.glo4003.shared.domain;

import java.math.BigDecimal;

public class Amount extends ValueObject {
  private BigDecimal value;

  public Amount(BigDecimal value) {
    this.value = value;
  }

  public BigDecimal getValue() {
    return value.setScale(2, BigDecimal.ROUND_HALF_UP);
  }

  public Amount add(Amount newValue) {
    return new Amount(value.add(newValue.value));
  }
}
