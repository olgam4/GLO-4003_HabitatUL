package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;

import java.util.List;

public interface PolicyRepository {
  List<Policy> getAll();

  Policy getById(PolicyId policyId) throws PolicyNotFoundException;

  void create(Policy policy) throws PolicyAlreadyCreatedException;

  void update(Policy policy) throws PolicyNotFoundException;
}
