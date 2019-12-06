package ca.ulaval.glo4003.coverage.application.premium.error;

public class CannotComputeModificationPremiumAdjustmentError extends PremiumCalculatorError {
  private static final String ERROR = "CANNOT_COMPUTE_MODIFICATION_PREMIUM_ADJUSTMENT";
  private static final String MESSAGE =
      "sorry, something went wrong while computing modification premium adjustment";

  public CannotComputeModificationPremiumAdjustmentError() {
    super(ERROR, MESSAGE);
  }

  public CannotComputeModificationPremiumAdjustmentError(Throwable cause) {
    super(ERROR, MESSAGE, cause);
  }
}
