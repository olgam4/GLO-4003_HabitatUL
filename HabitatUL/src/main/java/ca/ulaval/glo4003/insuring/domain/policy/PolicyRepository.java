package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;

public interface PolicyRepository {
  Policy getById(PolicyId policyId) throws PolicyNotFoundException;

  void create(Policy policy) throws PolicyAlreadyCreatedException;
}
