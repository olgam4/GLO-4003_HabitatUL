package ca.ulaval.glo4003.insuring.domain.policy;

import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyAlreadyCreatedException;
import ca.ulaval.glo4003.insuring.domain.policy.exception.PolicyNotFoundException;
import ca.ulaval.glo4003.insuring.helper.policy.PolicyBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static ca.ulaval.glo4003.insuring.helper.policy.PolicyGenerator.createPolicy;
import static ca.ulaval.glo4003.insuring.helper.policy.PolicyGenerator.createPolicyId;
import static ca.ulaval.glo4003.insuring.matcher.PolicyMatcher.matchesPolicy;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertThat;

public abstract class PolicyRepositoryIT {
  private static final PolicyId NOT_EXISTING_POLICY_ID = createPolicyId();

  private PolicyRepository subject;
  private Policy policy;
  private Policy anotherPolicy;
  private PolicyId policyId;

  @Before
  public void setUp() {
    subject = createSubject();
    policy = createPolicy();
    anotherPolicy = createPolicy();
    policyId = policy.getPolicyId();
  }

  @Test
  public void gettingAllPolicies_shouldReturnAllPolicies() throws PolicyAlreadyCreatedException {
    subject.create(policy);
    subject.create(anotherPolicy);

    List<Policy> policies = subject.getAll();

    assertThat(policies, hasItem(policy));
    assertThat(policies, hasItem(anotherPolicy));
  }

  @Test(expected = PolicyNotFoundException.class)
  public void gettingPolicyById_withUnknownPolicyId_shouldThrow() throws PolicyNotFoundException {
    subject.getById(NOT_EXISTING_POLICY_ID);
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
