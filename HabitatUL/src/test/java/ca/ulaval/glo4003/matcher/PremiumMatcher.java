package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;
import org.hamcrest.Matcher;

import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;

public class PremiumMatcher {
  private PremiumMatcher() {}

  public static Matcher<QuotePremiumInput> matchesQuotePremiumInput(
      final QuoteFormDto quoteFormDto) {
    return allOf(
        hasProperty(
            "namedInsuredGender", equalTo(quoteFormDto.getPersonalInformation().getGender())),
        hasProperty(
            "namedInsuredUniversityProgram",
            matchesUniversityProgramInput(
                quoteFormDto.getPersonalInformation().getUniversityProfile())),
        hasProperty(
            "additionalInsuredGender", equalTo(quoteFormDto.getAdditionalInsured().getGender())),
        hasProperty(
            "additionalInsuredUniversityProgram",
            matchesUniversityProgramInput(
                quoteFormDto.getAdditionalInsured().getUniversityProfile())),
        hasProperty(
            "animals", matchesAnimalsInput(quoteFormDto.getPersonalProperty().getAnimals())),
        hasProperty(
            "civilLiabilityLimit",
            matchesCivilLiabilityLimitInput(quoteFormDto.getCivilLiability().getLimit())));
  }

  private static Matcher<UniversityProgramInput> matchesUniversityProgramInput(
      final UniversityProfile universityProfile) {
    return allOf(
        hasProperty("cycle", equalTo(universityProfile.getCycle())),
        hasProperty("degree", equalTo(universityProfile.getDegree())),
        hasProperty("program", equalTo(universityProfile.getProgram())));
  }

  private static Matcher<AnimalsInput> matchesAnimalsInput(final Animals animals) {
    return hasProperty("collection", matchesAnimalsInputCollection(animals.getCollection()));
  }

  private static Matcher<Map<? extends AnimalBreedInput, ? extends Integer>>
      matchesAnimalsInputCollection(Map<AnimalBreed, Integer> collection) {
    return allOf(
        collection.entrySet().stream()
            .map(PremiumMatcher::matchersAnimalsInputCollectionEntry)
            .collect(Collectors.toList()));
  }

  private static Matcher<Map<? extends AnimalBreedInput, ? extends Integer>>
      matchersAnimalsInputCollectionEntry(Map.Entry<AnimalBreed, Integer> entry) {
    return hasEntry(matchesAnimalBreedInput(entry.getKey()), equalTo(entry.getValue()));
  }

  private static Matcher<AnimalBreedInput> matchesAnimalBreedInput(final AnimalBreed animalBreed) {
    switch (animalBreed) {
      case CAT:
        return equalTo(AnimalBreedInput.CAT);
      case DOG:
        return equalTo(AnimalBreedInput.DOG);
      case GOLD_FISH:
        return equalTo(AnimalBreedInput.GOLD_FISH);
      case SNAKE:
        return equalTo(AnimalBreedInput.SNAKE);
      default:
        return equalTo(AnimalBreedInput.OTHER);
    }
  }

  private static Matcher<CivilLiabilityLimitInput> matchesCivilLiabilityLimitInput(
      final CivilLiabilityLimit civilLiabilityLimit) {
    switch (civilLiabilityLimit) {
      case ONE_MILLION:
        return equalTo(CivilLiabilityLimitInput.ONE_MILLION);
      case TWO_MILLION:
        return equalTo(CivilLiabilityLimitInput.TWO_MILLION);
      default:
        return not(instanceOf(CivilLiabilityLimitInput.class));
    }
  }
}
