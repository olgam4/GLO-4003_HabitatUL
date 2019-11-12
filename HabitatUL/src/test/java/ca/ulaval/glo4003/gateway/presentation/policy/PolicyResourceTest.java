package ca.ulaval.glo4003.gateway.presentation.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.policy.request.ClaimRequest;
import ca.ulaval.glo4003.helper.claim.ClaimGenerator;
import ca.ulaval.glo4003.helper.policy.PolicyGenerator;
import ca.ulaval.glo4003.helper.shared.SecurityContextGenerator;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;

import static ca.ulaval.glo4003.matcher.ClaimMatcher.matchesOpenClaimDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PolicyResourceTest {
  private static final SecurityContext SECURITY_CONTEXT =
      SecurityContextGenerator.createSecurityContext();
  private static final PolicyId POLICY_ID = PolicyGenerator.createPolicyId();
  private static final ClaimId CLAIM_ID = ClaimGenerator.createClaimId();

  @Mock private PolicyAppService policyAppService;
  @Mock private UserAppService userAppService;

  private PolicyResource subject;
  private ClaimRequest claimRequest;
  private PolicyViewAssembler policyViewAssembler;

  @Before
  public void setUp() {
    claimRequest = ClaimGenerator.createClaimRequest();
    policyViewAssembler = new PolicyViewAssembler();
    when(policyAppService.openClaim(any(PolicyId.class), any(OpenClaimDto.class)))
        .thenReturn(CLAIM_ID);
    subject = new PolicyResource(policyAppService, userAppService, policyViewAssembler);
  }

  @Test
  public void gettingPolicies_shouldDelegateToUserAppService() {
    String userKey = SECURITY_CONTEXT.getUserPrincipal().getName();

    subject.getPolicies(SECURITY_CONTEXT);

    verify(userAppService).getPolicies(userKey);
  }

  @Test
  public void openingClaim_shouldDelegateToPolicyAppService() {
    subject.openClaim(SECURITY_CONTEXT, POLICY_ID, claimRequest);

    verify(policyAppService).openClaim(eq(POLICY_ID), argThat(matchesOpenClaimDto(claimRequest)));
  }
}
