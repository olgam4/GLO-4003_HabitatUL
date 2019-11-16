package ca.ulaval.glo4003.coverage.application.premium;

import ca.ulaval.glo4003.coverage.application.coverage.AdditionalCoverageResolver;
import ca.ulaval.glo4003.coverage.application.premium.assembler.BicycleEndorsementPremiumFormulaAssembler;
import ca.ulaval.glo4003.coverage.application.premium.assembler.PremiumAssembler;
import ca.ulaval.glo4003.coverage.application.premium.assembler.QuoteBasicBlockPremiumFormulaAssembler;
import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BasicBlockCoveragePremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.BicycleEndorsementPremiumDetail;
import ca.ulaval.glo4003.coverage.domain.premium.detail.PremiumDetails;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BicycleEndorsementPremiumInput;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuoteBasicBlockPremiumFormula;
import ca.ulaval.glo4003.coverage.domain.premium.formula.quote.QuotePremiumInput;
import ca.ulaval.glo4003.shared.domain.money.Money;

public class PremiumCalculator {
  private PremiumAssembler premiumAssembler;
  private AdditionalCoverageResolver additionalCoverageResolver;
  private QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula;
  private BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula;

  public PremiumCalculator() {
    this(
        new PremiumAssembler(),
        new AdditionalCoverageResolver(),
        QuoteBasicBlockPremiumFormulaAssembler.assemble(),
        BicycleEndorsementPremiumFormulaAssembler.assemble());
  }

  public PremiumCalculator(
      PremiumAssembler premiumAssembler,
      AdditionalCoverageResolver additionalCoverageResolver,
      QuoteBasicBlockPremiumFormula quoteBasicBlockPremiumFormula,
      BicycleEndorsementPremiumFormula bicycleEndorsementPremiumFormula) {
    this.premiumAssembler = premiumAssembler;
    this.additionalCoverageResolver = additionalCoverageResolver;
    this.quoteBasicBlockPremiumFormula = quoteBasicBlockPremiumFormula;
    this.bicycleEndorsementPremiumFormula = bicycleEndorsementPremiumFormula;
  }

  public PremiumDetails computeQuotePremium(QuoteForm quoteForm) {
    QuotePremiumInput quotePremiumInput = premiumAssembler.toQuotePremiumInput(quoteForm);
    Money basicBlockPremium = quoteBasicBlockPremiumFormula.compute(quotePremiumInput);
    PremiumDetails premiumDetails =
        new PremiumDetails(new BasicBlockCoveragePremiumDetail(basicBlockPremium));
    premiumDetails = addBicycleEndorsementOnDemand(quoteForm, premiumDetails);
    return premiumDetails;
  }

  private PremiumDetails addBicycleEndorsementOnDemand(
      QuoteForm quoteForm, PremiumDetails premiumDetails) {
    if (additionalCoverageResolver.shouldIncludeBicycleEndorsement(quoteForm)) {
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput =
          premiumAssembler.toBicycleEndorsementPremiumInput(quoteForm);
      premiumDetails = addBicycleEndorsement(bicycleEndorsementPremiumInput, premiumDetails);
    }
    return premiumDetails;
  }

  private PremiumDetails addBicycleEndorsement(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput,
      PremiumDetails premiumDetails) {
    Money bicycleEndorsementPremium =
        computeBicycleEndorsementPremium(bicycleEndorsementPremiumInput);
    premiumDetails =
        premiumDetails.addPremiumDetail(
            new BicycleEndorsementPremiumDetail(bicycleEndorsementPremium));
    return premiumDetails;
  }

  public PremiumDetails computeBicycleEndorsementPremium(
      BicycleEndorsementForm bicycleEndorsementForm) {
    BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput =
        premiumAssembler.toBicycleEndorsementPremiumInput(bicycleEndorsementForm);
    Money bicycleEndorsementPremium =
        bicycleEndorsementPremiumFormula.compute(bicycleEndorsementPremiumInput);
    // PremiumDetails premiumDetails =
    //    new PremiumDetails(new BasicBlockCoveragePremiumDetail(basicBlockPremium));
    // return premiumDetails;
    return null;
  }

  private Money computeBicycleEndorsementPremium(
      BicycleEndorsementPremiumInput bicycleEndorsementPremiumInput) {
    return bicycleEndorsementPremiumFormula.compute(bicycleEndorsementPremiumInput);
  }
}
