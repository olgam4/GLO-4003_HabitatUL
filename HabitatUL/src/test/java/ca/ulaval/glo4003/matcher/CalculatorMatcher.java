package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class CalculatorMatcher {
  private CalculatorMatcher() {}

  public static Matcher<QuotePremiumInput> matchesQuotePremiumInput(
      final QuoteFormDto quoteFormDto) {
    return allOf(
        hasProperty(
            "namedInsuredGender", equalTo(quoteFormDto.getPersonalInformation().getGender())),
        hasProperty(
            "namedInsuredUniversityProgram",
            equalTo(quoteFormDto.getPersonalInformation().getUniversityProfile().getProgram())),
        hasProperty(
            "additionalInsuredGender", equalTo(quoteFormDto.getAdditionalInsured().getGender())),
        hasProperty(
            "additionalInsuredUniversityProgram",
            equalTo(quoteFormDto.getAdditionalInsured().getUniversityProfile().getProgram())),
        hasProperty("animals", equalTo(quoteFormDto.getPersonalProperty().getAnimals())),
        hasProperty("civilLiabilityLimit", equalTo(quoteFormDto.getCivilLiability().getLimit())));
  }
}
