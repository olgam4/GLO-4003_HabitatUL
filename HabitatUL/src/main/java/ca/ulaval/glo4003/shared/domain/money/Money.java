package ca.ulaval.glo4003.shared.domain.money;

import ca.ulaval.glo4003.shared.domain.ValueObject;

public class Money extends ValueObject {
  private Amount amount;

  public Money(Amount amount) {
    this.amount = amount;
  }

  public Amount getAmount() {
    return amount;
  }

  public Money add(Money money) {
    Amount newAmount = amount.add(money.amount);
    return new Money(newAmount);
  }

  public Money multiply(double factor) {
    return new Money(amount.multiply(factor));
  }
}
