package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Gender;
import ca.ulaval.glo4003.underwriting.domain.quote.price.RoommateAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedRoommateAdjustmentProvider implements RoommateAdjustmentProvider {
  private static final Map<Map.Entry<Gender, Gender>, Float> LOOKUP_MAP = new HashMap<>();

  static {
    LOOKUP_MAP.put(new AbstractMap.SimpleEntry<>(Gender.MALE, Gender.MALE), 0.1f);
  }

  @Override
  public QuotePriceAdjustment getAdjustment(Gender namedInsuredGender, Gender roommateGender) {
    AbstractMap.SimpleEntry<Gender, Gender> key =
        new AbstractMap.SimpleEntry<>(namedInsuredGender, roommateGender);
    return Optional.ofNullable(LOOKUP_MAP.get(key))
        .map(x -> (QuotePriceAdjustment) new MultiplicativeQuotePriceAdjustment(x))
        .orElse(new NoQuotePriceAdjustment());
  }
}
