package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class CoverageMatcher {
  private CoverageMatcher() {}

  public static Matcher<QuotePremiumInput> matchesQuotePremiumInput(final QuoteForm quoteForm) {
    return allOf(
        hasProperty("namedInsuredGender", equalTo(quoteForm.getPersonalInformation().getGender())),
        hasProperty(
            "namedInsuredUniversityProgram",
            equalTo(quoteForm.getPersonalInformation().getUniversityProfile().getProgram())),
        hasProperty(
            "additionalInsuredGender", equalTo(quoteForm.getAdditionalInsured().getGender())),
        hasProperty(
            "additionalInsuredUniversityProgram",
            equalTo(quoteForm.getAdditionalInsured().getUniversityProfile().getProgram())),
        hasProperty("animals", equalTo(quoteForm.getPersonalProperty().getAnimals())),
        hasProperty("civilLiabilityLimit", equalTo(quoteForm.getCivilLiability().getLimit())));
  }
}
