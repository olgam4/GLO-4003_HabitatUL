package ca.ulaval.glo4003.insuring.domain.claim;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.helper.claim.ClaimBuilder;
import ca.ulaval.glo4003.helper.claim.LossDeclarationsBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyInformationBuilder;
import ca.ulaval.glo4003.helper.shared.AuthorityGenerator;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.helper.shared.TemporalGenerator;
import ca.ulaval.glo4003.insuring.domain.claim.error.CannotAcceptAuthorityNumberError;
import ca.ulaval.glo4003.insuring.domain.claim.error.LossDeclarationsExceedCoverageAmountError;
import ca.ulaval.glo4003.insuring.domain.claim.error.NotDeclaredBicycleError;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import org.junit.Test;

import java.util.Arrays;

import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.BICYCLE_ENDORSEMENT;
import static ca.ulaval.glo4003.coverage.domain.coverage.CoverageCategory.PERSONAL_PROPERTY;
import static ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle.UNFILLED_BICYCLE;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createPersonalPropertyLossCategory;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.shared.AuthorityGenerator.createAuthorityNumber;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThan;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountSmallerThan;
import static ca.ulaval.glo4003.insuring.domain.claim.ClaimStatus.RECEIVED;
import static ca.ulaval.glo4003.insuring.domain.claim.LossCategory.BICYCLE;
import static ca.ulaval.glo4003.insuring.domain.claim.SinisterType.THEFT;
import static ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber.UNFILLED_AUTHORITY_NUMBER;
import static org.junit.Assert.assertEquals;

public class ClaimTest {
  private static final PolicyInformation POLICY_INFORMATION = createPolicyInformation();
  private static final CoverageDetails COVERAGE_DETAILS = createCoverageDetails();
  private static final AuthorityNumber AUTHORITY_NUMBER = createAuthorityNumber();

  private Claim subject;

  @Test
  public void validatingClaim_withValidClaimAndNoBicycleLoss_shouldNotThrow() {
    LossDeclarations validLossDeclarations =
        LossDeclarationsBuilder.aLossDeclaration()
            .withLoss(
                createPersonalPropertyLossCategory(),
                createAmountSmallerThan(COVERAGE_DETAILS.getCoverageAmount(PERSONAL_PROPERTY)))
            .build();
    subject = ClaimBuilder.aClaim().withLossDeclarations(validLossDeclarations).build();

    subject.validate(POLICY_INFORMATION, COVERAGE_DETAILS);
  }

  @Test
  public void validatingClaim_withValidClaimAndBicycleLoss_shouldNotThrow() {
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
    PolicyInformation policyInformationWithoutDeclaredBicycle =
        PolicyInformationBuilder.aPolicyInformation().withBicycle(UNFILLED_BICYCLE).build();
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

  @Test
  public void providingAuthorityNumber_shouldSetTheAuthorityNumber() {
    Date date = TemporalGenerator.createDate();
    AuthorityNumber authorityNumber = AuthorityGenerator.createAuthorityNumber(date);
    subject =
        ClaimBuilder.aClaim()
            .withStatus(RECEIVED)
            .withDeclarationDate(date)
            .withAuthorityNumber(UNFILLED_AUTHORITY_NUMBER)
            .withSinisterType(THEFT)
            .build();

    subject.provideAuthorityNumber(authorityNumber);

    assertEquals(authorityNumber, subject.getAuthorityNumber());
  }

  @Test(expected = CannotAcceptAuthorityNumberError.class)
  public void providingAuthorityNumber_withClaimOfTypeOtherThanTheft_shouldThrowAnException() {
    subject = ClaimBuilder.aClaim().withStatus(RECEIVED).withSinisterType(THEFT).build();

    subject.provideAuthorityNumber(AUTHORITY_NUMBER);
  }

  @Test(expected = CannotAcceptAuthorityNumberError.class)
  public void providingAuthorityNumber_withSinisterTypeOtherThanTheft_shouldThrow() {
    SinisterType sinisterTypeOtherThanTheft =
        EnumSampler.sample(SinisterType.class, Arrays.asList(THEFT));
    subject =
        ClaimBuilder.aClaim()
            .withStatus(RECEIVED)
            .withAuthorityNumber(UNFILLED_AUTHORITY_NUMBER)
            .withSinisterType(sinisterTypeOtherThanTheft)
            .build();

    subject.provideAuthorityNumber(AUTHORITY_NUMBER);
  }

  @Test(expected = CannotAcceptAuthorityNumberError.class)
  public void providingAuthorityNumber_onClaimOfStatusOtherThanReceived_shouldThrow() {
    ClaimStatus claimStatusOtherThanReceived =
        EnumSampler.sample(ClaimStatus.class, Arrays.asList(RECEIVED));
    subject =
        ClaimBuilder.aClaim()
            .withStatus(claimStatusOtherThanReceived)
            .withAuthorityNumber(UNFILLED_AUTHORITY_NUMBER)
            .withSinisterType(THEFT)
            .build();

    subject.provideAuthorityNumber(AUTHORITY_NUMBER);
  }

  @Test(expected = CannotAcceptAuthorityNumberError.class)
  public void providingAuthorityNumber_onClaimWithDifferentDate_shouldThrow() {
    Date someDate = TemporalGenerator.createDate();
    Date otherDate = TemporalGenerator.createDate();
    AuthorityNumber someAuthorityNumber = AuthorityGenerator.createAuthorityNumber(someDate);
    subject = ClaimBuilder.aClaim().withDeclarationDate(otherDate).build();

    subject.provideAuthorityNumber(someAuthorityNumber);
  }
}
