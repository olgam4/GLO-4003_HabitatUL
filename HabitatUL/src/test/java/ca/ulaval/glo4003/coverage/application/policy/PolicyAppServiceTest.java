package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyFactory;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import ca.ulaval.glo4003.generator.policy.PolicyGenerator;
import ca.ulaval.glo4003.shared.infrastructure.FixedClockProvider;
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
  private static final FixedClockProvider FIXED_CLOCK_PROVIDER = new FixedClockProvider();

  @Mock private Policy policy;
  @Mock private PolicyRepository policyRepository;
  @Mock private PolicyFactory policyFactory;

  private PolicyAppService subject;
  private QuotePurchasedDto quotePurchasedDto;

  @Before
  public void setUp() {
    quotePurchasedDto = PolicyGenerator.createQuotePurchasedDto();
    when(policyFactory.create(any())).thenReturn(policy);
    subject = new PolicyAppService(policyRepository, policyFactory);
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
