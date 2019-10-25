package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.event.PolicyCreationRequestedEvent;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimFactory;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceTest {
  @Mock private Policy policy;
  @Mock private PolicyFactory policyFactory;
  @Mock private PolicyRepository policyRepository;
  @Mock private ClaimFactory claimFactory;
  @Mock private ClaimRepository claimRepository;

  private PolicyAppService subject;
  private PolicyCreationRequestedEvent policyCreationRequestedEvent;

  @Before
  public void setUp() {
    policyCreationRequestedEvent = PolicyGenerator.createPolicyCreationRequestedEvent();
    when(policyFactory.create(any(), any(), any())).thenReturn(policy);
    subject = new PolicyAppService(policyFactory, policyRepository, claimFactory, claimRepository);
  }

  @Test
  public void issuingPolicy_shouldIssuePolicy() {
    subject.issuePolicy(policyCreationRequestedEvent);

    verify(policy).issue();
  }

  @Test
  public void issuingPolicy_shouldCreatePolicy() {
    subject.issuePolicy(policyCreationRequestedEvent);

    verify(policyRepository).create(policy);
  }
}
