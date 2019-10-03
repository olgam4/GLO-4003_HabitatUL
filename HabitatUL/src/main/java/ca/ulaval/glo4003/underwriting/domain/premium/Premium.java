package ca.ulaval.glo4003.underwriting.domain.premium;

import ca.ulaval.glo4003.shared.domain.Amount;

import java.math.BigDecimal;

public class Premium extends Amount {
  public Premium(BigDecimal value) {
    super(value);
  }
}
