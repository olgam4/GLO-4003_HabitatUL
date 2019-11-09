package ca.ulaval.glo4003.helper.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.PersonalPropertyRequest;
import ca.ulaval.glo4003.helper.EnumSampler;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.PersonalProperty;
import com.github.javafaker.Faker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static ca.ulaval.glo4003.helper.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.helper.quote.form.BikeGenerator.createBike;
import static ca.ulaval.glo4003.helper.quote.form.BikeGenerator.createBikeRequest;

public class PersonalPropertyGenerator {
  private PersonalPropertyGenerator() {}

  public static PersonalPropertyRequest createPersonalPropertyRequest() {
    return new PersonalPropertyRequest(
        createCoverageAmount(), createAnimals(), createBikeRequest());
  }

  public static PersonalProperty createPersonalProperty() {
    return new PersonalProperty(createCoverageAmount(), createAnimals(), createBike());
  }

  public static Amount createCoverageAmount() {
    return createAmountGreaterThan(Amount.ZERO);
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
    return Stream.generate(PersonalPropertyGenerator::createAnimalBreed)
        .limit(Faker.instance().number().randomDigitNotZero())
        .collect(Collectors.toList());
  }

  public static AnimalBreed createAnimalBreed() {
    return EnumSampler.sample(AnimalBreed.class);
  }
}
