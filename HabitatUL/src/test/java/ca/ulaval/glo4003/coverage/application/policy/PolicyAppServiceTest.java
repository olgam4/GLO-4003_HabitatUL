package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.coverage.domain.policy.Policy;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyHolderId;
import ca.ulaval.glo4003.coverage.domain.policy.PolicyRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static ca.ulaval.glo4003.matcher.policy.PolicyDtoMatcher.matchesPolicyDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceTest {
  private static final PolicyHolderId POLICY_HOLDER_ID = new PolicyHolderId();

  @Mock private Policy policy;
  @Mock private PolicyRepository policyRepository;

  private PolicyAppService subject;
  private PolicyAssembler policyAssembler;

  @Before
  public void setUp() {
    policyAssembler = new PolicyAssembler();
    subject = new PolicyAppService(policyRepository, policyAssembler);
    when(policyRepository.getByPolicyHolderId(any())).thenReturn(Arrays.asList(policy));
  }

  @Test
  public void gettingPoliciesByPolicyHolderId_shouldGetPoliciesByPolicyHolderId() {
    subject.getPoliciesByPolicyHolderId(POLICY_HOLDER_ID);

    verify(policyRepository).getByPolicyHolderId(POLICY_HOLDER_ID);
  }

  @Test
  public void gettingPoliciesByPolicyHolderId_shouldProduceCorrespondingPolicyDtos() {
    List<PolicyDto> policyDtos = subject.getPoliciesByPolicyHolderId(POLICY_HOLDER_ID);

    assertEquals(1, policyDtos.size());
    assertThat(policyDtos.get(0), matchesPolicyDto(policy));
  }
}
