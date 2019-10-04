package ca.ulaval.glo4003.coverage.domain.policy;

public interface PolicyRepository {
  void create(Policy policy);

  Policy getById(PolicyId policyId);
}
