package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.PersonalPropertyRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PersonalPropertyGenerator {
  private static final int MIN_COVERAGE_AMOUNT = 8000;
  private static final int MAX_COVERAGE_AMOUNT = 100000;

  public static PersonalPropertyRequest createPersonalPropertyRequest() {
    return new PersonalPropertyRequest(createCoverageAmount(), createAnimals());
  }

  public static PersonalProperty createPersonalProperty() {
    return new PersonalProperty(createCoverageAmount(), createAnimals());
  }

  private static Amount createCoverageAmount() {
    double randomDouble =
        Faker.instance().number().randomDouble(0, MIN_COVERAGE_AMOUNT, MAX_COVERAGE_AMOUNT);
    return new Amount(BigDecimal.valueOf(randomDouble));
  }

  private static Animals createAnimals() {
    return new Animals(getRandomAnimalMap());
  }

  private static Map<AnimalBreed, Integer> getRandomAnimalMap() {
    Map<AnimalBreed, Integer> animals = new HashMap<>();
    getRandomAnimalList()
        .forEach(animal -> animals.put(animal, animals.getOrDefault(animal, 0) + 1));
    return animals;
  }

  private static List<AnimalBreed> getRandomAnimalList() {
    return Stream.generate(PersonalPropertyGenerator::getRandomAnimal)
        .limit(Faker.instance().number().randomDigitNotZero())
        .collect(Collectors.toList());
  }

  private static AnimalBreed getRandomAnimal() {
    return EnumSampler.sample(AnimalBreed.class);
  }
}
