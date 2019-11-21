package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.error.AnotherRenewalAlreadyAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyRenewalNotFoundError;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import java.util.*;

import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.ACCEPTED;
import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.PENDING;

public class PolicyRenewalsCoordinator {
  private Map<PolicyRenewalId, PolicyRenewal> renewals;

  public PolicyRenewalsCoordinator() {
    this(new HashMap<>());
  }

  public PolicyRenewalsCoordinator(Map<PolicyRenewalId, PolicyRenewal> renewals) {
    this.renewals = renewals;
  }

  public List<PolicyRenewal> getRenewals() {
    updateRenewalsStatus();
    return new ArrayList<>(renewals.values());
  }

  public PolicyRenewal getRenewal(PolicyRenewalId policyRenewalId) {
    updateRenewalsStatus();
    return Optional.ofNullable(renewals.get(policyRenewalId))
        .orElseThrow(() -> new PolicyRenewalNotFoundError(policyRenewalId));
  }

  public PolicyRenewal registerPolicyRenewal(
      Date currentCoveragePeriodEndDate,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodLengthProvider policyCoveragePeriodLengthProvider,
      ClockProvider clockProvider) {
    updateRenewalsStatus();
    checkIfAcceptedRenewalAlreadyExist();
    PolicyRenewalId policyRenewalId = new PolicyRenewalId();
    Period renewalCoveragePeriod =
        computeRenewalCoveragePeriod(
            currentCoveragePeriodEndDate, policyCoveragePeriodLengthProvider);
    PolicyRenewal policyRenewal =
        new PolicyRenewal(
            policyRenewalId,
            PENDING,
            renewalCoveragePeriod,
            proposedCoverageDetails,
            proposedPremiumDetails,
            clockProvider);
    renewals.put(policyRenewalId, policyRenewal);
    return policyRenewal;
  }

  private Period computeRenewalCoveragePeriod(
      Date currentCoveragePeriodEndDate,
      PolicyCoveragePeriodLengthProvider policyCoveragePeriodLengthProvider) {
    Date renewalCoveragePeriodStartDate =
        currentCoveragePeriodEndDate.plus(java.time.Period.ofDays(1));
    Date renewalCoveragePeriodEndDate =
        renewalCoveragePeriodStartDate.plus(
            policyCoveragePeriodLengthProvider.getPolicyCoveragePeriod());
    return new Period(renewalCoveragePeriodStartDate, renewalCoveragePeriodEndDate);
  }

  public PolicyRenewal retrieveAcceptedRenewal(PolicyRenewalId policyRenewalId) {
    updateRenewalsStatus();
    checkIfAcceptedRenewalAlreadyExist();
    PolicyRenewal policyRenewal = getRenewal(policyRenewalId);
    acceptRenewal(policyRenewal);
    expirePendingRenewals();
    return policyRenewal;
  }

  private void updateRenewalsStatus() {
    renewals.values().stream().forEach(PolicyRenewal::updateStatus);
  }

  private void checkIfAcceptedRenewalAlreadyExist() {
    if (hasAlreadyExistingAcceptedRenewal()) {
      throw new AnotherRenewalAlreadyAcceptedError();
    }
  }

  private boolean hasAlreadyExistingAcceptedRenewal() {
    return renewals.values().stream().anyMatch(x -> x.getStatus().equals(ACCEPTED));
  }

  private void acceptRenewal(PolicyRenewal policyRenewal) {
    policyRenewal.accept();
    renewals.put(policyRenewal.getPolicyRenewalId(), policyRenewal);
  }

  private void expirePendingRenewals() {
    renewals.values().stream().forEach(PolicyRenewal::expire);
  }
}
