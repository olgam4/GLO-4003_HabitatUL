package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.PolicyMatcher.matchesPolicy;
import static org.junit.Assert.assertThat;

public abstract class PolicyRepositoryIT {
  private static final PolicyId POLICY_ID = PolicyGenerator.createPolicyId();

  private PolicyRepository subject;
  private Policy policy;
  private PolicyId policyId;

  @Before
  public void setUp() {
    subject = createSubject();
    policy = PolicyGenerator.createPolicy();
    policyId = policy.getPolicyId();
  }

  @Test(expected = PolicyNotFoundException.class)
  public void gettingPolicyById_withUnknownPolicyId_shouldThrow() throws PolicyNotFoundException {
    subject.getById(POLICY_ID);
  }

  @Test
  public void creatingPolicy_shouldPersistUserAsIs()
      throws PolicyAlreadyCreatedException, PolicyNotFoundException {
    subject.create(policy);

    assertThat(subject.getById(policyId), matchesPolicy(policy));
  }

  @Test(expected = PolicyAlreadyCreatedException.class)
  public void creatingPolicy_withAlreadyPersistedPolicyId_shouldThrow()
      throws PolicyAlreadyCreatedException {
    subject.create(policy);

    subject.create(policy);
  }

  protected abstract PolicyRepository createSubject();
}
