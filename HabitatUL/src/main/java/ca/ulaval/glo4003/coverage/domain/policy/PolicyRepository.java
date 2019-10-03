package ca.ulaval.glo4003.coverage.domain.policy;

import java.util.List;

public interface PolicyRepository {
  List<Policy> getByPolicyHolderId(PolicyHolderId policyHolderId);
}
