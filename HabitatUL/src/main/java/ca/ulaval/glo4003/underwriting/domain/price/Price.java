package ca.ulaval.glo4003.underwriting.domain.price;

import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;

public class Price extends Amount {
  public Price(BigDecimal value) {
    super(value);
  }
}
