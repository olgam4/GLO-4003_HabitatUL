package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import com.github.javafaker.Faker;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createSinisterType;
import static ca.ulaval.glo4003.helper.claim.LossDeclarationsGenerator.createLossDeclarations;
import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycle;
import static ca.ulaval.glo4003.helper.coverage.form.personalproperty.BicycleGenerator.createBicycleRequest;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;
import static ca.ulaval.glo4003.helper.policy.PolicyViewGenerator.createPolicyView;
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

  public static Policy createPolicy() {
    return new Policy(createPolicyId(), createQuoteKey(), createPolicyView(), getClockProvider());
  }

  public static PolicyId createPolicyId() {
    return new PolicyId();
  }

  private static String createQuoteKey() {
    return Faker.instance().internet().uuid();
  }

  public static InsureBicycleRequest createInsureBicycleRequest() {
    return new InsureBicycleRequest(createBicycleRequest());
  }

  public static InsureBicycleDto createInsureBicycleDto() {
    return new InsureBicycleDto(createBicycle());
  }

  public static ModifyCoverageDto createModifyPolicyDto() {
    return new ModifyCoverageDto();
  }

  public static OpenClaimDto createOpenClaimDto() {
    return new OpenClaimDto(createSinisterType(), createLossDeclarations());
  }
}
