package ca.ulaval.glo4003.coverage.application.coverage;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.premium.formula.bike.BasicBlockCoverageMaximumBikePriceProvider;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.util.Optional;

public class AdditionalCoverageResolver {
  private BasicBlockCoverageMaximumBikePriceProvider basicBlockCoverageMaximumBikePriceProvider;

  public AdditionalCoverageResolver() {
    this(ServiceLocator.resolve(BasicBlockCoverageMaximumBikePriceProvider.class));
  }

  public AdditionalCoverageResolver(
      BasicBlockCoverageMaximumBikePriceProvider basicBlockCoverageMaximumBikePriceProvider) {
    this.basicBlockCoverageMaximumBikePriceProvider = basicBlockCoverageMaximumBikePriceProvider;
  }

  public boolean shouldIncludeBikeEndorsement(QuoteForm quoteForm) {
    Amount basicBlockCoverageMaximumBikePrice =
        basicBlockCoverageMaximumBikePriceProvider.getMaximumBikePrice();
    return Optional.ofNullable(quoteForm.getPersonalProperty().getBike().getPrice())
        .map(x -> x.isGreaterThan(basicBlockCoverageMaximumBikePrice))
        .orElse(false);
  }
}
