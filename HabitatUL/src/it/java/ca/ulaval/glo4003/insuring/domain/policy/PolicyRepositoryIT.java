package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
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
  public void creatingPolicy_shouldPersistPolicyAsIs()
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

  @Test
  public void updatingPolicy_shouldChangeAssociatedPolicy()
      throws PolicyNotFoundException, PolicyAlreadyCreatedException {
    subject.create(policy);

    Policy updatedPolicy = PolicyBuilder.aPolicy().withId(policyId).build();
    subject.update(updatedPolicy);

    assertThat(subject.getById(policyId), matchesPolicy(updatedPolicy));
  }

  @Test(expected = PolicyNotFoundException.class)
  public void updatingPolicy_withNotYetPersistedPolicy_shouldThrow()
      throws PolicyNotFoundException {
    subject.update(policy);
  }

  protected abstract PolicyRepository createSubject();
}
