package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.TriggerRenewalRequest;
import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationsCoordinator;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycleRequest;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.PersonalPropertyGenerator.createCoverageAmount;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.QuotePremiumInputGenerator.createCivilLiabilityLimit;
import static ca.ulaval.glo4003.helper.policy.PolicyHistoricGenerator.createPolicyHistoric;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModifications;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;

public class PolicyGenerator {
  private PolicyGenerator() {}

  public static PolicyPurchasedEvent createPolicyPurchasedEvent() {
    return new PolicyPurchasedEvent(
        createQuoteKey(),
        createPeriod(),
        createDate(),
        createPolicyInformation(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static PolicyDto createPolicyDto() {
    return new PolicyDto(
        createPolicyId(), createPeriod(), createCoverageDetails(), createPremiumDetails());
  }

  public static Policy createPolicy() {
    return new Policy(
        createPolicyId(),
        createQuoteKey(),
        createPolicyStatus(),
        createPolicyHistoric(),
        createPolicyModificationsCoordinator(),
        getClockProvider());
  }

  public static PolicyId createPolicyId() {
    return new PolicyId();
  }

  private static String createQuoteKey() {
    return Faker.instance().internet().uuid();
  }

  public static PolicyStatus createPolicyStatus() {
    return EnumSampler.sample(PolicyStatus.class);
  }

  public static PolicyModificationsCoordinator createPolicyModificationsCoordinator() {
    return createPolicyModificationsCoordinator(createPolicyModifications());
  }

  public static PolicyModificationsCoordinator createPolicyModificationsCoordinator(
      List<PolicyModification> policyModifications) {
    return new PolicyModificationsCoordinator(
        policyModifications.stream()
            .collect(
                Collectors.toMap(
                    PolicyModification::getPolicyModificationId,
                    policyModification -> policyModification,
                    (a, b) -> b)));
  }

  public static InsureBicycleRequest createInsureBicycleRequest() {
    return new InsureBicycleRequest(createBicycleRequest());
  }

  public static InsureBicycleDto createInsureBicycleDto() {
    return new InsureBicycleDto(createBicycle());
  }

  public static ModifyCoverageRequest createModifyCoverageRequest() {
    return new ModifyCoverageRequest(createCoverageAmount(), createCivilLiabilityLimit());
  }

  public static ModifyCoverageDto createEmptyModifyCoverageDto() {
    return new ModifyCoverageDto(null, null);
  }

  public static ModifyCoverageDto createModifyCoverageDto() {
    return new ModifyCoverageDto(createCoverageAmount(), createCivilLiabilityLimit());
  }

  public static TriggerRenewalRequest createTriggerRenewalRequest() {
    return new TriggerRenewalRequest(createCoverageAmount());
  }

  public static OpenClaimDto createOpenClaimDto() {
    return new OpenClaimDto(createSinisterType(), createLossDeclarations());
  }
}
