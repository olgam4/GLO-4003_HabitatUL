package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyPolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createLossDeclarations;
import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;

public class PolicyGenerator {
  private PolicyGenerator() {}

  public static PolicyPurchasedEvent createPolicyPurchasedEvent() {
    return new PolicyPurchasedEvent(
        createQuoteKey(),
        createPeriod(),
        createDate(),
        createCoverageDetails(),
        createPremiumDetails());
  }

  public static Policy createPolicy() {
    return new Policy(
        createPolicyId(),
        createQuoteKey(),
        createPeriod(),
        createCoverageDetails(),
        createPremiumDetails(),
        getClockProvider());
  }

  private static String createQuoteKey() {
    return Faker.instance().internet().uuid();
  }

  public static PolicyId createPolicyId() {
    return new PolicyId();
  }

  public static ModifyPolicyDto createModifyPolicyDto() {
    return new ModifyPolicyDto(createBicycle());
  }

  public static OpenClaimDto createOpenClaimDto() {
    return new OpenClaimDto(createSinisterType(), createLossDeclarations());
  }
}
