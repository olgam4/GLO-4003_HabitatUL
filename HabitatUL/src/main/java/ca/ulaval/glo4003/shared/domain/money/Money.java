package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.math.BigDecimal;

public class Money extends ValueObject {
  private Amount amount;

  public Money(BigDecimal value) {
    amount = new Amount(value);
  }

  public Amount getAmount() {
    return amount;
  }
}
