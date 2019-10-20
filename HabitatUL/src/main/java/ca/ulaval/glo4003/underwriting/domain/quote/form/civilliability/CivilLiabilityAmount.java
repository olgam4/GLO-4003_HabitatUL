package ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability;

import ca.ulaval.glo4003.shared.domain.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public enum CivilLiabilityAmount {
  ONE_MILLION("1M", new Amount(BigDecimal.valueOf(1000000))),
  TWO_MILLION("2M", new Amount(BigDecimal.valueOf(2000000)));

  private static final Map<String, CivilLiabilityAmount> LOOKUP_MAP = new HashMap<>();

  static {
    for (CivilLiabilityAmount civilLiabilityAmount : values()) {
      LOOKUP_MAP.put(civilLiabilityAmount.representation.toLowerCase(), civilLiabilityAmount);
    }
  }

  private final String representation;
  private final Amount value;

  CivilLiabilityAmount(String representation, Amount value) {
    this.representation = representation;
    this.value = value;
  }

  public static CivilLiabilityAmount getEnum(String rawValue) throws InvalidArgumentException {
    return Optional.ofNullable(LOOKUP_MAP.get(rawValue.toLowerCase()))
        .orElseThrow(InvalidArgumentException::new);
  }

  public String getRepresentation() {
    return representation;
  }

  public Amount getValue() {
    return value;
  }
}
