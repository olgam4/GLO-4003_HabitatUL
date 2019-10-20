package ca.ulaval.glo4003.underwriting.infrastructure.quote.price;

import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.price.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.MultiplicativeQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.NoQuotePriceAdjustment;
import ca.ulaval.glo4003.underwriting.domain.quote.price.adjustment.QuotePriceAdjustment;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedAnimalsAdjustmentProvider implements AnimalsAdjustmentProvider {
  private static final Map<AnimalBreed, Float> LOOKUP_MAP = new EnumMap<>(AnimalBreed.class);

  static {
    LOOKUP_MAP.put(AnimalBreed.CAT, 0.01f);
    LOOKUP_MAP.put(AnimalBreed.DOG, 0.05f);
    LOOKUP_MAP.put(AnimalBreed.GOLD_FISH, -0.01f);
    LOOKUP_MAP.put(AnimalBreed.SNAKE, 1f);
  }

  @Override
  public QuotePriceAdjustment getAdjustment(AnimalBreed breed, Integer count) {
    return Optional.ofNullable(LOOKUP_MAP.get(breed))
        .map(x -> (QuotePriceAdjustment) new MultiplicativeQuotePriceAdjustment(x * count))
        .orElse(new NoQuotePriceAdjustment());
  }
}
