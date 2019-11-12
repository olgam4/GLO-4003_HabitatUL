package ca.ulaval.glo4003.calculator.domain.premium.formula.input;

import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum CivilLiabilityLimit {
  ONE_MILLION("1M", new Amount(BigDecimal.valueOf(1000000))),
  TWO_MILLION("2M", new Amount(BigDecimal.valueOf(2000000)));

  private static final Map<String, CivilLiabilityLimit> LOOKUP_MAP = new HashMap<>();

  static {
    for (CivilLiabilityLimit civilLiabilityLimit : values()) {
      LOOKUP_MAP.put(civilLiabilityLimit.representation.toLowerCase(), civilLiabilityLimit);
    }
  }

  private final String representation;
  private final Amount amount;

  CivilLiabilityLimit(String representation, Amount amount) {
    this.representation = representation;
    this.amount = amount;
  }

  public static CivilLiabilityLimit getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }

  public String getRepresentation() {
    return representation;
  }

  public Amount getAmount() {
    return amount;
  }
}
