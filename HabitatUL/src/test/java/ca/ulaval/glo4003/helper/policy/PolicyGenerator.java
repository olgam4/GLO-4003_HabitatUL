package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyId;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createAmountGreaterThanZero;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createPeriod;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyGenerator {
  private PolicyGenerator() {}

  public static OpenClaimDto createOpenClaimDto() {
    return new OpenClaimDto(
        ClaimGenerator.createSinisterType(), ClaimGenerator.createLossDeclarations());
  }

  public static Policy createPolicy() {
    return new Policy(
        createPolicyId(),
        Faker.instance().internet().uuid(),
        createPeriod(),
        createAmountGreaterThanZero(),
        getClockProvider());
  }

  public static PolicyId createPolicyId() {
    return new PolicyId();
  }
}
