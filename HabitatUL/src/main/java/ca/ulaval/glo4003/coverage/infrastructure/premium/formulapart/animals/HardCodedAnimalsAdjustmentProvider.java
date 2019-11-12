package ca.ulaval.glo4003.coverage.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.coverage.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;

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
  public PremiumAdjustment getAdjustment(AnimalBreed breed, Integer count) {
    return Optional.ofNullable(LOOKUP_MAP.get(breed))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x * count))
        .orElse(new NullPremiumAdjustment());
  }
}
