package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.coverage.domain.coverage.detail.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.PersonalProperty;
import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyInformationBuilder;
import ca.ulaval.glo4003.insuring.domain.claim.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.claim.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.domain.CoverageCategory.BICYCLE_ENDORSEMENT;
import static ca.ulaval.glo4003.coverage.domain.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createPersonalPropertyLossCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThan;
import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;

public class ClaimTest {
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();

  private Claim subject;

  @Test
  public void validatingClaim_withValidClaim_shouldNotThrow() {
    LossDeclarations validLossDeclarations =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(
                createPersonalPropertyLossCategory(),
                createAmountSmallerThan(COVERAGE_DETAILS.getCoverageAmount(PERSONAL_PROPERTY)))
            .withLoss(
                BICYCLE,
                createAmountSmallerThan(COVERAGE_DETAILS.getCoverageAmount(BICYCLE_ENDORSEMENT)))
            .build();
    subject = ClaimBuilder.aClaim().withLossDeclarations(validLossDeclarations).build();

    subject.validate(POLICY_INFORMATION, COVERAGE_DETAILS);
  }

  @Test(expected = NotDeclaredBicycleError.class)
  public void validatingClaim_withNotDeclaredBicycle_shouldThrow() {
    PersonalProperty personalPropertyWithoutBicycle =
        PersonalPropertyBuilder.aPersonalProperty().withoutBicycle().build();
    PolicyInformation policyInformationWithoutDeclaredBicycle =
        PolicyInformationBuilder.aPolicyInformation()
            .withPersonalProperty(personalPropertyWithoutBicycle)
            .build();
    LossDeclarations lossDeclarationsWithBicycleLoss =
        LossDeclarationsBuilder.aLossDeclaration().withBicycleLoss().build();

    subject = ClaimBuilder.aClaim().withLossDeclarations(lossDeclarationsWithBicycleLoss).build();

    subject.validate(policyInformationWithoutDeclaredBicycle, COVERAGE_DETAILS);
  }

  @Test(expected = LossDeclarationsExceedCoverageAmountError.class)
  public void validatingClaim_withLossesExceedingPersonalPropertyCoverageAmount_shouldThrow() {
    LossDeclarations lossDeclarationsWithExceedingPersonalPropertyLosses =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(
                createPersonalPropertyLossCategory(),
                createAmountGreaterThan(COVERAGE_DETAILS.getCoverageAmount(PERSONAL_PROPERTY)))
            .build();
    subject =
        ClaimBuilder.aClaim()
            .withLossDeclarations(lossDeclarationsWithExceedingPersonalPropertyLosses)
            .build();

    subject.validate(POLICY_INFORMATION, COVERAGE_DETAILS);
  }

  @Test(expected = LossDeclarationsExceedCoverageAmountError.class)
  public void validatingClaim_withLossesExceedingBicycleEndorsementCoverageAmount_shouldThrow() {
    LossDeclarations lossDeclarationsWithExceedingBikeEndorsementLosses =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(
                BICYCLE,
                createAmountGreaterThan(COVERAGE_DETAILS.getCoverageAmount(BICYCLE_ENDORSEMENT)))
            .build();
    subject =
        ClaimBuilder.aClaim()
            .withLossDeclarations(lossDeclarationsWithExceedingBikeEndorsementLosses)
            .build();

    subject.validate(POLICY_INFORMATION, COVERAGE_DETAILS);
  }
}
