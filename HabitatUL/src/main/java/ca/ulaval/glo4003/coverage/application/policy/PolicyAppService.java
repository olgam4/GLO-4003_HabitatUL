package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.context.ServiceLocator;
import ca.ulaval.glo4003.coverage.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyHolderId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;

import java.util.List;

public class PolicyAppService {
  private PolicyRepository policyRepository;
  private PolicyAssembler policyAssembler;

  public PolicyAppService() {
    this(ServiceLocator.resolve(PolicyRepository.class), new PolicyAssembler());
  }

  public PolicyAppService(PolicyRepository policyRepository, PolicyAssembler policyAssembler) {
    this.policyRepository = policyRepository;
    this.policyAssembler = policyAssembler;
  }

  public void createPolicy(QuotePurchasedDto quotePurchasedDto) {
    System.out.println(quotePurchasedDto.toString());
  }

  public List<PolicyDto> getPoliciesByPolicyHolderId(PolicyHolderId policyHolderId) {
    List<Policy> policies = policyRepository.getByPolicyHolderId(policyHolderId);
    return policyAssembler.from(policies);
  }
}
