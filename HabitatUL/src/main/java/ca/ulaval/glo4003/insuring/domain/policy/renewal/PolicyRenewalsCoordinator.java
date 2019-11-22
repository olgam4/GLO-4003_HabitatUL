package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.error.AnotherRenewalAlreadyAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.PolicyRenewalNotFoundError;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.shared.domain.temporal.Period;
import org.glassfish.jersey.internal.util.Producer;

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
    return updateRenewalsStatus(this::getRenewalsWithoutUpdate);
  }

  private List<PolicyRenewal> getRenewalsWithoutUpdate() {
    return new ArrayList<>(renewals.values());
  }

  public PolicyRenewal getRenewal(PolicyRenewalId policyRenewalId) {
    return updateRenewalsStatus(() -> getRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal getRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    return Optional.ofNullable(renewals.get(policyRenewalId))
        .orElseThrow(() -> new PolicyRenewalNotFoundError(policyRenewalId));
  }

  public PolicyRenewal registerPolicyRenewal(
      Date currentCoveragePeriodEndDate,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodProvider policyCoveragePeriodProvider,
      ClockProvider clockProvider) {
    return updateRenewalsStatus(
        () ->
            registerPolicyRenewalWithoutUpdate(
                currentCoveragePeriodEndDate,
                proposedCoverageDetails,
                proposedPremiumDetails,
                policyCoveragePeriodProvider,
                clockProvider));
  }

  private PolicyRenewal registerPolicyRenewalWithoutUpdate(
      Date currentCoveragePeriodEndDate,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      PolicyCoveragePeriodProvider policyCoveragePeriodProvider,
      ClockProvider clockProvider) {
    checkIfAcceptedRenewalAlreadyExist();
    PolicyRenewalId policyRenewalId = new PolicyRenewalId();
    Period renewalCoveragePeriod =
        computeRenewalCoveragePeriod(currentCoveragePeriodEndDate, policyCoveragePeriodProvider);
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
      PolicyCoveragePeriodProvider policyCoveragePeriodProvider) {
    Date renewalCoveragePeriodStartDate =
        currentCoveragePeriodEndDate.plus(java.time.Period.ofDays(1));
    Date renewalCoveragePeriodEndDate =
        currentCoveragePeriodEndDate.plus(policyCoveragePeriodProvider.getPolicyCoveragePeriod());
    return new Period(renewalCoveragePeriodStartDate, renewalCoveragePeriodEndDate);
  }

  public PolicyRenewal retrieveAcceptedRenewal(PolicyRenewalId policyRenewalId) {
    return updateRenewalsStatus(() -> retrieveAcceptedRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal retrieveAcceptedRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    checkIfAcceptedRenewalAlreadyExist();
    PolicyRenewal policyRenewal = getRenewalWithoutUpdate(policyRenewalId);
    acceptRenewal(policyRenewal);
    expirePendingRenewals();
    return policyRenewal;
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

  public PolicyRenewal cancelRenewal(PolicyRenewalId policyRenewalId) {
    return updateRenewalsStatus(() -> cancelRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal cancelRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    PolicyRenewal policyRenewal = getRenewalWithoutUpdate(policyRenewalId);
    cancelRenewal(policyRenewal);
    return policyRenewal;
  }

  private void cancelRenewal(PolicyRenewal policyRenewal) {
    policyRenewal.cancel();
    renewals.put(policyRenewal.getPolicyRenewalId(), policyRenewal);
  }

  public PolicyRenewal confirmRenewal(PolicyRenewalId policyRenewalId) {
    return updateRenewalsStatus(() -> confirmRenewalWithoutUpdate(policyRenewalId));
  }

  private PolicyRenewal confirmRenewalWithoutUpdate(PolicyRenewalId policyRenewalId) {
    PolicyRenewal policyRenewal = getRenewalWithoutUpdate(policyRenewalId);
    confirmRenewal(policyRenewal);
    return policyRenewal;
  }

  private void confirmRenewal(PolicyRenewal policyRenewal) {
    policyRenewal.confirm();
    renewals.put(policyRenewal.getPolicyRenewalId(), policyRenewal);
  }

  private <T> T updateRenewalsStatus(Producer<T> call) {
    updateRenewalsStatus();
    return call.call();
  }

  private void updateRenewalsStatus() {
    renewals.values().stream().forEach(PolicyRenewal::updateStatus);
  }
}
