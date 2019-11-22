package ca.ulaval.glo4003.insuring.domain.policy.renewal;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalAlreadyCanceledError;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalAlreadyConfirmedError;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalExpiredError;
import ca.ulaval.glo4003.insuring.domain.policy.error.RenewalNotYetAcceptedError;
import ca.ulaval.glo4003.insuring.domain.policy.historic.PolicyView;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;
import ca.ulaval.glo4003.shared.domain.temporal.Period;

import static ca.ulaval.glo4003.insuring.domain.policy.renewal.PolicyRenewalStatus.*;

public class PolicyRenewal {
  private PolicyRenewalId policyRenewalId;
  private PolicyRenewalStatus status;
  private Period coveragePeriod;
  private CoverageDetails proposedCoverageDetails;
  private PremiumDetails proposedPremiumDetails;
  private ClockProvider clockProvider;

  public PolicyRenewal(
      PolicyRenewalId policyRenewalId,
      PolicyRenewalStatus status,
      Period coveragePeriod,
      CoverageDetails proposedCoverageDetails,
      PremiumDetails proposedPremiumDetails,
      ClockProvider clockProvider) {
    this.policyRenewalId = policyRenewalId;
    this.status = status;
    this.coveragePeriod = coveragePeriod;
    this.proposedCoverageDetails = proposedCoverageDetails;
    this.proposedPremiumDetails = proposedPremiumDetails;
    this.clockProvider = clockProvider;
  }

  public PolicyRenewalId getPolicyRenewalId() {
    return policyRenewalId;
  }

  public PolicyRenewalStatus getStatus() {
    return status;
  }

  public Period getCoveragePeriod() {
    return coveragePeriod;
  }

  public CoverageDetails getProposedCoverageDetails() {
    return proposedCoverageDetails;
  }

  public PremiumDetails getProposedPremiumDetails() {
    return proposedPremiumDetails;
  }

  public DateTime getEffectiveDateTime() {
    return coveragePeriod.getStartDate().atStartOfDay();
  }

  public void updateStatus() {
    if (isOutdated()) expire();
  }

  private boolean isOutdated() {
    return DateTime.now(clockProvider.getClock())
        .isAfter(coveragePeriod.getStartDate().atStartOfDay());
  }

  public void expire() {
    if (status.equals(PENDING)) status = EXPIRED;
  }

  public void accept() {
    checkIfRenewalIsExpired();
    checkIfRenewalIsCanceled();
    checkIfRenewalIsConfirmed();
    status = ACCEPTED;
  }

  private void checkIfRenewalIsExpired() {
    if (status.equals(EXPIRED)) {
      throw new RenewalExpiredError(policyRenewalId);
    }
  }

  private void checkIfRenewalIsCanceled() {
    if (status.equals(CANCELED)) {
      throw new RenewalAlreadyCanceledError(policyRenewalId);
    }
  }

  private void checkIfRenewalIsConfirmed() {
    if (status.equals(CONFIRMED)) {
      throw new RenewalAlreadyConfirmedError(policyRenewalId);
    }
  }

  public void cancel() {
    checkIfRenewalIsNotAccepted();
    status = CANCELED;
  }

  public void confirm() {
    checkIfRenewalIsNotAccepted();
    status = CONFIRMED;
  }

  private void checkIfRenewalIsNotAccepted() {
    if (!status.equals(ACCEPTED)) {
      throw new RenewalNotYetAcceptedError(policyRenewalId);
    }
  }

  public PolicyView updatePolicyView(PolicyView currentView) {
    return new PolicyView(
        coveragePeriod,
        currentView.getPolicyInformation(),
        proposedCoverageDetails,
        proposedPremiumDetails);
  }
}
