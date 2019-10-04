package ca.ulaval.glo4003.management.domain;

import ca.ulaval.glo4003.shared.domain.Money;

public interface PaymentProcessor {
  void pay(String user, Money price);
}
