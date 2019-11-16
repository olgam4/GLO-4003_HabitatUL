package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.insuring.domain.claim.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.claim.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.money.Amount;

import static ca.ulaval.glo4003.coverage.domain.CoverageCategory.BICYCLE_ENDORSEMENT;
import static ca.ulaval.glo4003.coverage.domain.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;

public class Claim {
  private ClaimId claimId;
  private ClaimStatus claimStatus;
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

  public Claim(
      ClaimId claimId,
      ClaimStatus claimStatus,
      SinisterType sinisterType,
      LossDeclarations lossDeclarations) {
    this.claimId = claimId;
    this.claimStatus = claimStatus;
    this.sinisterType = sinisterType;
    this.lossDeclarations = lossDeclarations;
  }

  public ClaimId getClaimId() {
    return claimId;
  }

  public ClaimStatus getClaimStatus() {
    return claimStatus;
  }

  public SinisterType getSinisterType() {
    return sinisterType;
  }

  public LossDeclarations getLossDeclarations() {
    return lossDeclarations;
  }

  public void validate(PolicyInformation policyInformation, CoverageDetails coverageDetails) {
    checkIfLossDeclarationsContainsNotDeclaredBicycle(policyInformation);
    checkIfLossDeclarationsExceedCoverage(coverageDetails);
  }

  private void checkIfLossDeclarationsContainsNotDeclaredBicycle(
      PolicyInformation policyInformation) {
    if (lossDeclarations.includes(BICYCLE) && hasNoDeclaredBicycle(policyInformation)) {
      throw new NotDeclaredBicycleError();
    }
  }

  private boolean hasNoDeclaredBicycle(PolicyInformation policyInformation) {
    return !policyInformation.getPersonalProperty().getBicycle().isFilled();
  }

  private void checkIfLossDeclarationsExceedCoverage(CoverageDetails coverageDetails) {
    checkIfLossDeclarationsExceedPersonalPropertyCoverage(coverageDetails);
    if (lossDeclarations.includes(BICYCLE)) {
      checkIfLossDeclarationsExceedBicycleEndorsementCoverage(coverageDetails);
    }
  }

  private void checkIfLossDeclarationsExceedPersonalPropertyCoverage(
      CoverageDetails coverageDetails) {
    if (isExceedingPersonalPropertyCoverageAmount(coverageDetails)) {
      throw new LossDeclarationsExceedCoverageAmountError();
    }
  }

  private boolean isExceedingPersonalPropertyCoverageAmount(CoverageDetails coverageDetails) {
    Amount personalPropertyLosses = lossDeclarations.computePersonalPropertyLosses();
    return personalPropertyLosses.isGreaterThan(
        coverageDetails.getCoverageAmount(PERSONAL_PROPERTY));
  }

  private void checkIfLossDeclarationsExceedBicycleEndorsementCoverage(
      CoverageDetails coverageDetails) {
    if (isExceedingBicycleEndorsementCoverageAmount(coverageDetails)) {
      throw new LossDeclarationsExceedCoverageAmountError();
    }
  }

  private boolean isExceedingBicycleEndorsementCoverageAmount(CoverageDetails coverageDetails) {
    Amount lossAmount = lossDeclarations.getLossAmount(BICYCLE);
    return lossAmount.isGreaterThan(coverageDetails.getCoverageAmount(BICYCLE_ENDORSEMENT));
  }
}
