package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.shared.domain.ClockProvider;

public class PolicyAppService {
  private PolicyRepository policyRepository;
  private PolicyFactory policyFactory;

  public PolicyAppService() {
    this(
        ServiceLocator.resolve(PolicyRepository.class),
        new PolicyFactory(ServiceLocator.resolve(ClockProvider.class)));
  }

  public PolicyAppService(PolicyRepository policyRepository, PolicyFactory policyFactory) {
    this.policyRepository = policyRepository;
    this.policyFactory = policyFactory;
  }

  public void issuePolicy(QuotePurchasedDto quotePurchasedDto) {
    Policy policy = policyFactory.create(quotePurchasedDto.getQuoteId());
    policy.issue();
    policyRepository.create(policy);
  }
}
