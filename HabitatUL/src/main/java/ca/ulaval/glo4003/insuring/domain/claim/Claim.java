package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.insuring.domain.claim.error.CannotAcceptAuthorityNumberError;
import ca.ulaval.glo4003.insuring.domain.claim.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.claim.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.money.Amount;
import ca.ulaval.glo4003.shared.domain.temporal.Date;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.BICYCLE_ENDORSEMENT;
import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.RECEIVED;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.UNDER_ANALYSIS;
import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;
import static ca.ulaval.glo4003.insuring.domain.claim.SinisterType.THEFT;
import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;

public class Claim {
  private ClaimId claimId;
  private Date declarationDate;
  private ClaimStatus claimStatus;
  private AuthorityNumber authorityNumber;
  private SinisterType sinisterType;
  private LossDeclarations lossDeclarations;

  public Claim(
      ClaimId claimId,
      Date declarationDate,
      ClaimStatus claimStatus,
      AuthorityNumber authorityNumber,
      SinisterType sinisterType,
      LossDeclarations lossDeclarations) {
    this.claimId = claimId;
    this.declarationDate = declarationDate;
    this.claimStatus = claimStatus;
    this.authorityNumber = authorityNumber;
    this.sinisterType = sinisterType;
    this.lossDeclarations = lossDeclarations;
  }

  public ClaimId getClaimId() {
    return claimId;
  }

  public Date getDeclarationDate() {
    return declarationDate;
  }

  public ClaimStatus getStatus() {
    return claimStatus;
  }

  public AuthorityNumber getAuthorityNumber() {
    return authorityNumber;
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
    return !policyInformation.getBicycle().isFilled();
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

  public void provideAuthorityNumber(AuthorityNumber value) {
    checkIfCannotAcceptAuthorityNumber(value);
    claimStatus = UNDER_ANALYSIS;
    authorityNumber = value;
  }

  private void checkIfCannotAcceptAuthorityNumber(AuthorityNumber authorityNumber) {
    if (cannotAcceptAuthorityNumber(authorityNumber)) {
      throw new CannotAcceptAuthorityNumberError(claimId);
    }
  }

  private boolean cannotAcceptAuthorityNumber(AuthorityNumber value) {
    return !authorityNumber.equals(UNFILLED_AUTHORITY_NUMBER)
        || !sinisterType.equals(THEFT)
        || !claimStatus.equals(RECEIVED)
        || !value.declaredOn(declarationDate);
  }
}
