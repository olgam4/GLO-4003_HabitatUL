package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimFactory;
import ca.ulaval.glo4003.coverage.domain.claim.ClaimRepository;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.generator.policy.PolicyGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.matcher.policy.PolicyMatcher.mockitoPolicyMatcher;
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
  private QuotePurchasedDto quotePurchasedDto;

  @Before
  public void setUp() {
    quotePurchasedDto = PolicyGenerator.createQuotePurchasedDto();
    when(policyFactory.create(any())).thenReturn(policy);
    subject = new PolicyAppService(policyFactory, policyRepository, claimFactory, claimRepository);
  }

  @Test
  public void issuingPolicy_shouldIssuePolicy() {
    subject.issuePolicy(quotePurchasedDto);

    verify(policy).issue();
  }

  @Test
  public void issuingPolicy_shouldCreatePolicy() {
    subject.issuePolicy(quotePurchasedDto);

    verify(policyRepository).create(mockitoPolicyMatcher(quotePurchasedDto));
  }
}
