package ca.ulaval.glo4003.insuring.application.policy;

import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.OpenClaimDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.TriggerRenewalDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyId;
import ca.ulaval.glo4003.insuring.domain.policy.lossratio.LossRatio;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.shared.application.logging.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.helper.policy.LossRatioGenerator.createLossRatio;
import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.*;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PolicyAppServiceLoggingDecoratorTest {
  private static final PolicyId POLICY_ID = createPolicyId();
  private static final PolicyPurchasedEvent POLICY_PURCHASED_EVENT = createPolicyPurchasedEvent();
  private static final InsureBicycleDto INSURING_BICYCLE_DTO = createInsureBicycleDto();
  private static final ModifyCoverageDto MODIFY_COVERAGE_DTO = createModifyCoverageDto();
  private static final PolicyModificationId POLICY_MODIFICATION_ID = createPolicyModificationId();
  private static final TriggerRenewalDto TRIGGER_RENEWAL_DTO = createTriggerRenewalDto();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();
  private static final OpenClaimDto OPEN_CLAIM_DTO = createOpenClaimDto();
  private static final LossRatio LOSS_RATIO = createLossRatio();

  @Mock private PolicyAppService policyAppService;
  @Mock private Logger logger;

  private PolicyAppService subject;

  @Before
  public void setUp() {
    subject = new PolicyAppServiceLoggingDecorator(policyAppService, logger);
  }

  @Test
  public void issuingPolicy_shouldLogParamsAsInfo() {
    subject.issuePolicy(POLICY_PURCHASED_EVENT);

    verify(logger).info(anyString());
  }

  @Test
  public void issuingPolicy_shouldDelegateToPolicyAppService() {
    subject.issuePolicy(POLICY_PURCHASED_EVENT);

    verify(policyAppService).issuePolicy(POLICY_PURCHASED_EVENT);
  }

  @Test
  public void insuringBicycle_shouldLogParamsAndReturnAsInfo() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void insuringBicycle_shouldDelegateToPolicyAppService() {
    subject.insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);

    verify(policyAppService).insureBicycle(POLICY_ID, INSURING_BICYCLE_DTO);
  }

  @Test
  public void modifyingCoverage_shouldLogParamsAndReturnAsInfo() {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void modifyingCoverage_shouldDelegateToPolicyAppService() {
    subject.modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);

    verify(policyAppService).modifyCoverage(POLICY_ID, MODIFY_COVERAGE_DTO);
  }

  @Test
  public void confirmingModification_shouldLogParamsAndReturnAsInfo() {
    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void confirmingModification_shouldDelegateToPolicyAppService() {
    subject.confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);

    verify(policyAppService).confirmModification(POLICY_ID, POLICY_MODIFICATION_ID);
  }

  @Test
  public void triggeringRenewal_shouldLogParamsAndReturnAsInfo() {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void triggeringRenewal_shouldDelegateToPolicyAppService() {
    subject.triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);

    verify(policyAppService).triggerRenewal(POLICY_ID, TRIGGER_RENEWAL_DTO);
  }

  @Test
  public void acceptingRenewal_shouldLogParamsAsInfo() {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(logger).info(anyString());
  }

  @Test
  public void acceptingRenewal_shouldDelegateToPolicyAppService() {
    subject.acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyAppService).acceptRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void cancelingRenewal_shouldLogParamsAsInfo() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(logger).info(anyString());
  }

  @Test
  public void cancelingRenewal_shouldDelegateToPolicyAppService() {
    subject.cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);

    verify(policyAppService).cancelRenewal(POLICY_ID, POLICY_RENEWAL_ID);
  }

  @Test
  public void openingClaim_shouldLogParamsAndReturnAsInfo() {
    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    verify(logger, times(2)).info(anyString());
  }

  @Test
  public void openingClaim_shouldDelegateToPolicyAppService() {
    subject.openClaim(POLICY_ID, OPEN_CLAIM_DTO);

    verify(policyAppService).openClaim(POLICY_ID, OPEN_CLAIM_DTO);
  }

  @Test
  public void configuringMaximumLossRatio_shouldLogParamsAsInfo() {
    subject.configureMaximumLossRatio(LOSS_RATIO);

    verify(logger).info(anyString());
  }
}
