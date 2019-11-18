package ca.ulaval.glo4003.gateway.presentation.insuring.policy;

import ca.ulaval.glo4003.administration.application.user.UserAppService;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ClaimRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.insuring.application.policy.PolicyAppService;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.domain.claim.ClaimId;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.ws.rs.core.SecurityContext;

import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimId;
import static ca.ulaval.glo4003.helper.claim.ClaimGenerator.createClaimRequest;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationDto;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.helper.shared.SecurityContextGenerator.createSecurityContext;
import static ca.ulaval.glo4003.matcher.ClaimMatcher.matchesOpenClaimDto;
import static ca.ulaval.glo4003.matcher.PolicyMatcher.matchesInsureBicycleDto;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.hamcrest.MockitoHamcrest.argThat;

@RunWith(MockitoJUnitRunner.class)
public class PolicyResourceTest {
  private static final SecurityContext SECURITY_CONTEXT = createSecurityContext();
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final InsureBicycleRequest INSURE_BICYCLE_REQUEST = createInsureBicycleRequest();
  private static final PolicyModificationDto POLICY_MODIFICATION_DTO =
      createPolicyModificationDto();
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();
  private static final PolicyDto POLICY_DTO = createPolicyDto();
  private static final ClaimId CLAIM_ID = createClaimId();
  private static final ClaimRequest CLAIM_REQUEST = createClaimRequest();

  @Mock private PolicyAppService policyAppService;
  @Mock private UserAppService userAppService;

  private PolicyResource subject;
  private PolicyViewAssembler policyViewAssembler;

  @Before
  public void setUp() {
    policyViewAssembler = new PolicyViewAssembler();
    when(policyAppService.insureBicycle(any(PolicyId.class), any(InsureBicycleDto.class)))
        .thenReturn(POLICY_MODIFICATION_DTO);
    when(policyAppService.confirmModification(any(PolicyId.class), any(PolicyModificationId.class)))
        .thenReturn(POLICY_DTO);
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
  public void insuringBicycle_shouldDelegateToPolicyAppService() {
    subject.insureBicycle(SECURITY_CONTEXT, POLICY_ID, INSURE_BICYCLE_REQUEST);

    verify(policyAppService)
        .insureBicycle(eq(POLICY_ID), argThat(matchesInsureBicycleDto(INSURE_BICYCLE_REQUEST)));
  }

  @Test
  public void confirmingModification_shouldDelegateToPolicyAppService() {
    subject.confirmModification(SECURITY_CONTEXT, POLICY_ID, POLICY_MODIFICATION_ID);

    verify(policyAppService).confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);
  }

  @Test
  public void openingClaim_shouldDelegateToPolicyAppService() {
    subject.openClaim(SECURITY_CONTEXT, POLICY_ID, CLAIM_REQUEST);

    verify(policyAppService).openClaim(eq(POLICY_ID), argThat(matchesOpenClaimDto(CLAIM_REQUEST)));
  }
}
