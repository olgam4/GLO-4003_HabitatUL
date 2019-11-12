package ca.ulaval.glo4003.coverage.application;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bicycleendorsement.BasicBlockCoverageMaximumBicyclePriceProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.Optional;

public class AdditionalCoverageResolver {
  private BasicBlockCoverageMaximumBicyclePriceProvider
      basicBlockCoverageMaximumBicyclePriceProvider;

  public AdditionalCoverageResolver() {
    this(ServiceLocator.resolve(BasicBlockCoverageMaximumBicyclePriceProvider.class));
  }

  public AdditionalCoverageResolver(
      BasicBlockCoverageMaximumBicyclePriceProvider basicBlockCoverageMaximumBicyclePriceProvider) {
    this.basicBlockCoverageMaximumBicyclePriceProvider =
        basicBlockCoverageMaximumBicyclePriceProvider;
  }

  public boolean shouldIncludeBicycleEndorsement(QuoteForm quoteForm) {
    Amount basicBlockCoverageMaximumBicyclePrice =
        basicBlockCoverageMaximumBicyclePriceProvider.getMaximumBicyclePrice();
    return Optional.ofNullable(quoteForm.getPersonalProperty().getBicycle().getPrice())
        .map(x -> x.isGreaterOrEqual(basicBlockCoverageMaximumBicyclePrice))
        .orElse(false);
  }
}
