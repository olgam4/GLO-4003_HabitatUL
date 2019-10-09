package ca.ulaval.glo4003.management.domain.user;

import ca.ulaval.glo4003.shared.domain.Money;

public interface PaymentProcessor {
  void process(String user, Money payment);
}
