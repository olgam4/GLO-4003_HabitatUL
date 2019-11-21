package ca.ulaval.glo4003.insuring.domain.policy.aggregate;

import ca.ulaval.glo4003.helper.policy.PolicyBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyHistoricBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyRenewalBuilder;
import ca.ulaval.glo4003.helper.policy.PolicyViewBuilder;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.error.InactivePolicyError;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalNotYetAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyHistoric;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewal;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalId;
import ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalsCoordinator;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static ca.ulaval.glo4003.helper.policy.PolicyGenerator.createPolicyRenewalsCoordinator;
import static ca.ulaval.glo4003.helper.policy.PolicyRenewalGenerator.createPolicyRenewalId;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.*;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.ACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.PolicyStatus.INACTIVE;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.*;
import static org.junit.Assert.assertEquals;

public class CancelRenewalTest {
  private static final ClockProvider CLOCK_PROVIDER = getClockProvider();
  private static final PolicyView CURRENT_POLICY_VIEW =
      PolicyViewBuilder.aPolicyView().withCoveragePeriod(createCurrentPeriod()).build();
  private static final PolicyRenewalId POLICY_RENEWAL_ID = createPolicyRenewalId();

  private Policy subject;
  private PolicyHistoric policyHistoric;
  private PolicyRenewal policyRenewal;
  private PolicyRenewalsCoordinator policyRenewalsCoordinator;

  @Before
  public void setUp() {
    policyHistoric =
        PolicyHistoricBuilder.aPolicyHistoric().withPolicyView(CURRENT_POLICY_VIEW).build();
    policyRenewal =
        PolicyRenewalBuilder.aPolicyRenewal()
            .withPolicyRenewalId(POLICY_RENEWAL_ID)
            .withStatus(ACCEPTED)
            .withCoveragePeriod(createFuturePeriod())
            .build();
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator(Arrays.asList(policyRenewal));
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(policyHistoric)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();
  }

  @Test
  public void cancellingRenewal_shouldSetPolicyActive() {
    subject.cancelRenewal(POLICY_RENEWAL_ID);

    assertEquals(ACTIVE, subject.getStatus());
  }

  @Test
  public void cancellingRenewal_shouldCancelRenewal() {
    subject.cancelRenewal(POLICY_RENEWAL_ID);

    assertEquals(CANCELED, policyRenewal.getStatus());
  }

  @Test(expected = InactivePolicyError.class)
  public void cancellingRenewal_withInactivePolicy_shouldThrow() {
    subject = PolicyBuilder.aPolicy().withStatus(INACTIVE).build();

    subject.cancelRenewal(POLICY_RENEWAL_ID);
  }

  @Test(expected = RenewalNotYetAcceptedError.class)
  public void cancellingRenewal_withNotYetAcceptedRenewal_shouldThrow() {
    policyRenewal =
        PolicyRenewalBuilder.aPolicyRenewal()
            .withPolicyRenewalId(POLICY_RENEWAL_ID)
            .withStatus(PENDING)
            .withCoveragePeriod(createFuturePeriod())
            .build();
    policyRenewalsCoordinator = createPolicyRenewalsCoordinator(Arrays.asList(policyRenewal));
    subject =
        PolicyBuilder.aPolicy()
            .withStatus(ACTIVE)
            .withPolicyHistoric(policyHistoric)
            .withPolicyRenewalsCoordinator(policyRenewalsCoordinator)
            .withClockProvider(CLOCK_PROVIDER)
            .build();

    subject.cancelRenewal(POLICY_RENEWAL_ID);
  }
}
