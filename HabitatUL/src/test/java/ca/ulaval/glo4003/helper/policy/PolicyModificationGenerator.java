package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationModifierGenerator.createPolicyInformationModifier;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDateTime;

public class PolicyModificationGenerator {
  private PolicyModificationGenerator() {}

  public static PolicyModificationDto createPolicyModificationDto() {
    return new PolicyModificationDto(
        createPolicyModificationId(),
        createFutureDateTime(),
        createPolicyModificationStatus(),
        createMoney(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static List<PolicyModification> createPolicyModifications() {
    return IntStream.range(0, Faker.instance().number().randomDigitNotZero())
        .mapToObj(i -> createPolicyModification())
        .collect(Collectors.toList());
  }

  public static PolicyModification createPolicyModification() {
    return new PolicyModification(
        createPolicyModificationId(),
        createFutureDateTime(),
        createPolicyModificationStatus(),
        createMoney(),
        createPolicyInformationModifier(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static PolicyModificationId createPolicyModificationId() {
    return new PolicyModificationId();
  }

  private static PolicyModificationStatus createPolicyModificationStatus() {
    return EnumSampler.sample(PolicyModificationStatus.class);
  }
}
