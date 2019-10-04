package ca.ulaval.glo4003.management.infrastructure;

import ca.ulaval.glo4003.management.domain.PaymentProcessor;
import ca.ulaval.glo4003.shared.domain.Money;

public class AlwaysTruePaymentProcessor implements PaymentProcessor {
  @Override
  public void pay(String userKey, Money price) {
    System.out.println("Payment processed");
  }
}
