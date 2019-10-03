package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;

import java.util.List;
import java.util.stream.Collectors;

public class PolicyAssembler {
  public List<PolicyDto> from(List<Policy> policies) {
    return policies.stream().map(this::from).collect(Collectors.toList());
  }

  public PolicyDto from(Policy policy) {
    return new PolicyDto();
  }
}
