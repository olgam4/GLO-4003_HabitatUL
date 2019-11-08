package ca.ulaval.glo4003.calculator.infrastructure.premium.formulapart.animals;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.MultiplicativePremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.NullPremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formulapart.animals.AnimalsAdjustmentProvider;
import ca.ulaval.glo4003.calculator.domain.premium.input.AnimalBreedInput;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class HardCodedAnimalsAdjustmentProvider implements AnimalsAdjustmentProvider {
  private static final Map<AnimalBreedInput, Float> LOOKUP_MAP =
      new EnumMap<>(AnimalBreedInput.class);

  static {
    LOOKUP_MAP.put(AnimalBreedInput.CAT, 0.01f);
    LOOKUP_MAP.put(AnimalBreedInput.DOG, 0.05f);
    LOOKUP_MAP.put(AnimalBreedInput.GOLD_FISH, -0.01f);
    LOOKUP_MAP.put(AnimalBreedInput.SNAKE, 1f);
  }

  @Override
  public PremiumAdjustment getAdjustment(AnimalBreedInput breed, Integer count) {
    return Optional.ofNullable(LOOKUP_MAP.get(breed))
        .map(x -> (PremiumAdjustment) new MultiplicativePremiumAdjustment(x * count))
        .orElse(new NullPremiumAdjustment());
  }
}
