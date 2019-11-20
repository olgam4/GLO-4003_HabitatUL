package ca.ulaval.glo4003.coverage.application.premium.assembler;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.civilliability.CivilLiabilityLimit;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.coveragemodification.CoverageModificationPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.handling.InvalidArgumentException;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.CIVIL_LIABILITY;

public class PremiumAssembler {
  public QuotePremiumInput toQuotePremiumInput(QuoteForm quoteForm) {
    return new QuotePremiumInput(
        quoteForm.getPersonalInformation().getGender(),
        quoteForm.getPersonalInformation().getUniversityProfile().getProgram(),
        quoteForm.getAdditionalInsured().getGender(),
        quoteForm.getAdditionalInsured().getUniversityProfile().getProgram(),
        quoteForm.getPersonalProperty().getAnimals(),
        quoteForm.getPersonalProperty().getBicycle().getPrice(),
        quoteForm.getCivilLiability().getLimit());
  }

  public BicycleEndorsementPremiumInput toBicycleEndorsementPremiumInput(QuoteForm quoteForm) {
    return new BicycleEndorsementPremiumInput(
        quoteForm.getPersonalProperty().getBicycle().getPrice());
  }

  public BicycleEndorsementPremiumInput toBicycleEndorsementPremiumInput(
      BicycleEndorsementForm bicycleEndorsementForm) {
    return new BicycleEndorsementPremiumInput(bicycleEndorsementForm.getBicycle().getPrice());
  }

  public CoverageModificationPremiumInput toCurrentCoverageModificationPremiumInput(
      CoverageModificationForm coverageModificationForm) throws InvalidArgumentException {
    Amount civilLiabilityLimitAmount =
        coverageModificationForm.getCurrentCoverageDetails().getCoverageAmount(CIVIL_LIABILITY);
    return new CoverageModificationPremiumInput(
        CivilLiabilityLimit.fromAmount(civilLiabilityLimitAmount));
  }

  public CoverageModificationPremiumInput toUpdatedCoverageModificationPremiumInput(
      CoverageModificationForm coverageModificationForm) {
    // TODO: CHECK IF NULL VALUE OTHERWISE SET SAME VALUE AS CURRENT
    return new CoverageModificationPremiumInput(coverageModificationForm.getCivilLiabilityLimit());
  }
}
