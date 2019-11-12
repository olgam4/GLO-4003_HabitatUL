package ca.ulaval.glo4003.helper.calculator.premium;

import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Animals;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.helper.calculator.form.identity.UniversityProgramGenerator;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ca.ulaval.glo4003.helper.calculator.form.identity.IdentityGenerator.createGender;
import static ca.ulaval.glo4003.helper.calculator.form.personalproperty.BikeGenerator.createBikePrice;

public class QuotePremiumInputGenerator {
  private QuotePremiumInputGenerator() {}

  public static QuotePremiumInput create() {
    return new QuotePremiumInput(
        createGender(),
        UniversityProgramGenerator.createUniversityProgram(),
        createGender(),
        UniversityProgramGenerator.createUniversityProgram(),
        createAnimals(),
        createBikePrice(),
        createCivilLiabilityLimit());
  }

  public static Animals createAnimals() {
    return new Animals(getRandomAnimalMap());
  }

  private static Map<AnimalBreed, Integer> getRandomAnimalMap() {
    Map<AnimalBreed, Integer> animals = new HashMap<>();
    getRandomAnimalList()
        .forEach(animal -> animals.put(animal, animals.getOrDefault(animal, 0) + 1));
    return animals;
  }

  private static List<AnimalBreed> getRandomAnimalList() {
    return Stream.generate(QuotePremiumInputGenerator::createAnimalBreed)
        .limit(Faker.instance().number().randomDigitNotZero())
        .collect(Collectors.toList());
  }

  public static AnimalBreed createAnimalBreed() {
    return EnumSampler.sample(AnimalBreed.class);
  }

  public static CivilLiabilityLimit createCivilLiabilityLimit() {
    return EnumSampler.sample(CivilLiabilityLimit.class);
  }
}
