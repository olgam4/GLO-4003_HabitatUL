package ca.ulaval.glo4003.underwriting.application.quote;

import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalBreedInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.AnimalsInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.CivilLiabilityLimitInput;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.UniversityProgramInput;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.AnimalBreed;
import ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty.Animals;

import java.util.EnumMap;
import java.util.Map;

public class QuotePremiumAssembler {
  public QuotePremiumInput from(QuoteForm quoteForm) {
    return new QuotePremiumInput(
        quoteForm.getPersonalInformation().getGender(),
        from(quoteForm.getPersonalInformation().getUniversityProfile()),
        quoteForm.getAdditionalInsured().getGender(),
        from(quoteForm.getAdditionalInsured().getUniversityProfile()),
        from(quoteForm.getPersonalProperty().getAnimals()),
        from(quoteForm.getCivilLiability().getLimit()));
  }

  private UniversityProgramInput from(UniversityProfile universityProfile) {
    return new UniversityProgramInput(
        universityProfile.getCycle(),
        universityProfile.getDegree(),
        universityProfile.getProgram());
  }

  private AnimalsInput from(Animals animals) {
    Map<AnimalBreedInput, Integer> collection = new EnumMap<>(AnimalBreedInput.class);
    animals.getCollection().forEach((key, value) -> collection.put(from(key), value));
    return new AnimalsInput(collection);
  }

  private AnimalBreedInput from(AnimalBreed animalBreed) {
    switch (animalBreed) {
      case CAT:
        return AnimalBreedInput.CAT;
      case DOG:
        return AnimalBreedInput.DOG;
      case GOLD_FISH:
        return AnimalBreedInput.GOLD_FISH;
      case SNAKE:
        return AnimalBreedInput.SNAKE;
      default:
        return AnimalBreedInput.OTHER;
    }
  }

  private CivilLiabilityLimitInput from(CivilLiabilityLimit civilLiabilityLimit) {
    switch (civilLiabilityLimit) {
      case ONE_MILLION:
        return CivilLiabilityLimitInput.ONE_MILLION;
      case TWO_MILLION:
        return CivilLiabilityLimitInput.TWO_MILLION;
      default:
        // TODO: raise exception or something
        return null;
    }
  }
}
