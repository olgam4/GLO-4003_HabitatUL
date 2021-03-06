package ca.ulaval.glo4003.insuring.helper.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyRenewalDto;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus;
import ca.ulaval.glo4003.shared.helper.EnumSampler;
import com.github.javafaker.Faker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static ca.ulaval.glo4003.coverage.helper.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.coverage.helper.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.createPeriod;
import static ca.ulaval.glo4003.shared.helper.TemporalGenerator.getClockProvider;

public class PolicyRenewalGenerator {
  private PolicyRenewalGenerator() {}

  public static PolicyRenewalDto createPolicyRenewalDto() {
    return new PolicyRenewalDto(
        createPolicyRenewalId(),
        createPolicyRenewalStatus(),
        createPeriod(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static List<PolicyRenewal> createPolicyRenewals() {
    return IntStream.range(0, Faker.instance().number().randomDigitNotZero())
        .mapToObj(i -> createPolicyRenewal())
        .collect(Collectors.toList());
  }

  public static PolicyRenewal createPolicyRenewal() {
    return new PolicyRenewal(
        createPolicyRenewalId(),
        createPolicyRenewalStatus(),
        createPeriod(),
        createCoverageDetails(),
        createPremiumDetails(),
        getClockProvider());
  }

  public static PolicyRenewalId createPolicyRenewalId() {
    return new PolicyRenewalId();
  }

  public static PolicyRenewalStatus createPolicyRenewalStatus() {
    return EnumSampler.sample(PolicyRenewalStatus.class);
  }
}
