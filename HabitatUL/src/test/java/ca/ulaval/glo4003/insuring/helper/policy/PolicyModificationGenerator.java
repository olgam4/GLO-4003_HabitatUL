package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;
import ca.ulaval.glo4003.shared.helper.EnumSampler;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.insuring.helper.policy.PolicyInformationModifierGenerator.createPolicyInformationModifier;
import static ca.ulaval.glo4003.shared.helper.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createFutureDateTime;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.getClockProvider;

public class PolicyModificationGenerator {
  private PolicyModificationGenerator() {}

  public static PolicyModificationDto createPolicyModificationDto() {
    return new PolicyModificationDto(
        createPolicyModificationId(),
        createPolicyModificationStatus(),
        createFutureDateTime(),
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
        createPolicyModificationStatus(),
        createFutureDateTime(),
        createMoney(),
        createPolicyInformationModifier(),
        createCoverageDetails(),
        createPremiumDetails(),
        getClockProvider());
  }

  public static PolicyModificationId createPolicyModificationId() {
    return new PolicyModificationId();
  }

  public static PolicyModificationStatus createPolicyModificationStatus() {
    return EnumSampler.sample(PolicyModificationStatus.class);
  }
}
