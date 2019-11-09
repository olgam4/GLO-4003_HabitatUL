package ca.ulaval.glo4003.helper.premium;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.GenderInput;
import ca.ulaval.glo4003.helper.EnumSampler;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QuotePremiumInputGenerator {
  private QuotePremiumInputGenerator() {}

  public static QuotePremiumInput create() {
    return new QuotePremiumInput(
        createGenderInput(),
        UniversityProgramInputGenerator.create(),
        createGenderInput(),
        UniversityProgramInputGenerator.create(),
        createAnimalsInput(),
        createCivilLiabilityLimitInput());
  }

  public static GenderInput createGenderInput() {
    return EnumSampler.sample(GenderInput.class);
  }

  public static AnimalsInput createAnimalsInput() {
    return new AnimalsInput(getRandomAnimalMap());
  }

  private static Map<AnimalBreedInput, Integer> getRandomAnimalMap() {
    Map<AnimalBreedInput, Integer> animals = new HashMap<>();
    getRandomAnimalList()
        .forEach(animal -> animals.put(animal, animals.getOrDefault(animal, 0) + 1));
    return animals;
  }

  private static List<AnimalBreedInput> getRandomAnimalList() {
    return Stream.generate(QuotePremiumInputGenerator::createAnimalBreedInput)
        .limit(Faker.instance().number().randomDigitNotZero())
        .collect(Collectors.toList());
  }

  public static AnimalBreedInput createAnimalBreedInput() {
    return EnumSampler.sample(AnimalBreedInput.class);
  }

  public static CivilLiabilityLimitInput createCivilLiabilityLimitInput() {
    return EnumSampler.sample(CivilLiabilityLimitInput.class);
  }
}
