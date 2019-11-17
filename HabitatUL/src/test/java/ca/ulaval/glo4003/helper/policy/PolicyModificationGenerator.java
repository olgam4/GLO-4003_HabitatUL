package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.helper.shared.EnumSampler;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
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

  public static PolicyModificationId createPolicyModificationId() {
    return new PolicyModificationId();
  }

  private static PolicyModificationStatus createPolicyModificationStatus() {
    return EnumSampler.sample(PolicyModificationStatus.class);
  }
}
