package ca.ulaval.glo4003.administration.domain.user;

import ca.ulaval.glo4003.shared.domain.money.Money;

public interface PaymentProcessor {
  void process(String user, Money payment);
}
