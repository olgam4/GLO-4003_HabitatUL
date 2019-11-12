package ca.ulaval.glo4003.helper.claim;

import ca.ulaval.glo4003.coverage.domain.claim.*;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.*;
import static ca.ulaval.glo4003.helper.shared.EnumSampler.sample;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountBetween;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThan;

public class ClaimBuilder {
  private static final ClaimId DEFAULT_CLAIM_ID = createClaimId();
  private static final ClaimStatus DEFAULT_CLAIM_STATUS = createClaimStatus();
  private static final SinisterType DEFAULT_SINISTER_TYPE = createSinisterType();
  private static final LossDeclarations DEFAULT_LOSS_DECLARATIONS = createLossDeclarations();

  private ClaimId claimId = DEFAULT_CLAIM_ID;
  private ClaimStatus claimStatus = DEFAULT_CLAIM_STATUS;
  private SinisterType sinisterType = DEFAULT_SINISTER_TYPE;
  private LossDeclarations lossDeclarations = DEFAULT_LOSS_DECLARATIONS;
  private Amount minimumTotalAmount = Amount.ZERO;
  private Amount maximumTotalAmount = new Amount(new BigDecimal(Integer.MAX_VALUE));

  private ClaimBuilder() {}

  public static ClaimBuilder aClaim() {
    return new ClaimBuilder();
  }

  public ClaimBuilder withMinimumTotalAmount(Amount minimumTotalAmount) {
    this.minimumTotalAmount = minimumTotalAmount;
    this.maximumTotalAmount = Amount.max(maximumTotalAmount, minimumTotalAmount);
    return this;
  }

  public ClaimBuilder withMaximumTotalAmount(Amount maximumTotalAmount) {
    this.minimumTotalAmount = Amount.min(minimumTotalAmount, maximumTotalAmount);
    this.maximumTotalAmount = maximumTotalAmount;
    return this;
  }

  public ClaimBuilder withBicycleLossDeclaration() {
    Map<LossCategory, Amount> lossDeclarationsCollection = lossDeclarations.getCollection();
    if (!lossDeclarationsCollection.containsKey(LossCategory.BICYCLE)) {
      lossDeclarationsCollection.put(LossCategory.BICYCLE, createAmountGreaterThan(Amount.ZERO));
      lossDeclarations = new LossDeclarations(lossDeclarationsCollection);
    }
    return this;
  }

  public ClaimBuilder withoutBicycleLossDeclaration() {
    Map<LossCategory, Amount> lossDeclarationsCollection = lossDeclarations.getCollection();
    lossDeclarationsCollection.remove(LossCategory.BICYCLE);
    if (lossDeclarationsCollection.size() == 0) {
      lossDeclarationsCollection.put(
          sample(LossCategory.class, Arrays.asList(LossCategory.BICYCLE)),
          createAmountGreaterThan(Amount.ZERO));
    }
    lossDeclarations = new LossDeclarations(lossDeclarationsCollection);
    return this;
  }

  public Claim build() {
    Map<LossCategory, Amount> lossDeclarationsCollection = lossDeclarations.getCollection();
    int size = lossDeclarationsCollection.size();
    Amount minimumLossAmountPerLoss =
        new Amount(BigDecimal.valueOf(minimumTotalAmount.getValue().intValue() / size + 1));
    Amount maximumLossAmountPerLoss =
        new Amount(BigDecimal.valueOf(maximumTotalAmount.getValue().intValue() / size - 1));
    lossDeclarationsCollection
        .entrySet()
        .forEach(
            x -> {
              Amount lossAmount = x.getValue();
              if (!lossAmount.isBetween(minimumLossAmountPerLoss, maximumLossAmountPerLoss)) {
                x.setValue(createAmountBetween(minimumLossAmountPerLoss, maximumLossAmountPerLoss));
              }
            });
    return new Claim(claimId, claimStatus, sinisterType, lossDeclarations);
  }
}
