package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.price.GraduateStudentAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NullQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedGraduateStudentAdjustmentProvider
    implements GraduateStudentAdjustmentProvider {
  private static final Map<String, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put("2e", -0.1273f);
    LOOKUP_MAP.put("3e", -0.1273f);
  }

  @Override
  public QuotePriceAdjustment getAdjustment(String cycle) {
    return Optional.ofNullable(LOOKUP_MAP.get(cycle))
        .map(x -> (QuotePriceAdjustment) new MultiplicativeQuotePriceAdjustment(x))
        .orElse(new NullQuotePriceAdjustment());
  }
}
