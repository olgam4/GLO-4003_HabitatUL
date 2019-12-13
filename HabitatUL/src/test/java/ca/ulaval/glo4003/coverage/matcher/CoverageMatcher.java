package ca.ulaval.glo4003.coverage.matcher;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.matcher.CommonMatcher;
import org.hamcrest.Matcher;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;
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

  public static Matcher<BicycleEndorsementPremiumInput> matchesBicycleEndorsementPremiumInput(
      final BicycleEndorsementForm bicycleEndorsementForm) {
    return hasProperty("bicyclePrice", equalTo(bicycleEndorsementForm.getBicycle().getPrice()));
  }

  public static Matcher<CoverageModificationPremiumInput>
      matchesCurrentCoverageModificationPremiumInput(
          final CoverageModificationForm coverageModificationForm) {
    try {
      return hasProperty(
          "civilLiabilityLimit",
          equalTo(
              CivilLiabilityLimit.fromAmount(
                  coverageModificationForm
                      .getCurrentCoverageDetails()
                      .getCoverageAmount(CIVIL_LIABILITY))));
    } catch (InvalidArgumentException e) {
      return new CommonMatcher.AlwaysFalseMatcher<>();
    }
  }

  public static Matcher<CoverageModificationPremiumInput>
      matchesUpdatedCoverageModificationPremiumInput(
          final CoverageModificationForm coverageModificationForm) {
    return hasProperty(
        "civilLiabilityLimit", equalTo(coverageModificationForm.getCivilLiabilityLimit()));
  }
}
