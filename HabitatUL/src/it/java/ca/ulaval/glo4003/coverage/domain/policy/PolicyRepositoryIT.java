package ca.ulaval.glo4003.coverage.domain.policy;

import ca.ulaval.glo4003.coverage.domain.policy.exception.PolicyAlreadyPersistedError;
import ca.ulaval.glo4003.generator.policy.PolicyGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.matcher.PolicyMatcher.matchesPolicy;
import static org.junit.Assert.assertThat;

public abstract class PolicyRepositoryIT {
  private PolicyRepository subject;
  private Policy policy;
  private PolicyId policyId;

  @Before
  public void setUp() {
    subject = createSubject();
    policy = PolicyGenerator.createPolicy();
    policyId = policy.getPolicyId();
  }

  @Test
  public void creatingPolicy_shouldPersistUserAsIs() {
    subject.create(policy);

    assertThat(subject.getById(policyId), matchesPolicy(policy));
  }

  @Test(expected = PolicyAlreadyPersistedError.class)
  public void creatingPolicy_withAlreadyPersistedPolicyId_shouldThrow() {
    subject.create(policy);

    subject.create(policy);
  }

  protected abstract PolicyRepository createSubject();
}
