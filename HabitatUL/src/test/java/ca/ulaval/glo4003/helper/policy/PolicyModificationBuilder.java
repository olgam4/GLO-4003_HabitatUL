package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.coverage.domain.coverage.CoverageDetails;
import ca.ulaval.glo4003.coverage.domain.premium.PremiumDetails;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationId;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModificationStatus;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;
import ca.ulaval.glo4003.shared.domain.money.Money;
import ca.ulaval.glo4003.shared.domain.temporal.ClockProvider;
import ca.ulaval.glo4003.shared.domain.temporal.DateTime;

import static ca.ulaval.glo4003.helper.coverage.coverage.CoverageDetailsGenerator.createCoverageDetails;
import static ca.ulaval.glo4003.helper.coverage.premium.PremiumDetailsGenerator.createPremiumDetails;
import static ca.ulaval.glo4003.helper.policy.PolicyInformationModifierGenerator.createPolicyInformationModifier;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationId;
import static ca.ulaval.glo4003.helper.policy.PolicyModificationGenerator.createPolicyModificationStatus;
import static ca.ulaval.glo4003.helper.shared.MoneyGenerator.createMoney;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.createFutureDateTime;
import static ca.ulaval.glo4003.helper.shared.TemporalGenerator.getClockProvider;

public class PolicyModificationBuilder {
  private final PolicyModificationId DEFAULT_POLICY_MODIFICATION_ID = createPolicyModificationId();
  private final DateTime DEFAULT_EXPIRATION_DATE = createFutureDateTime();
  private final PolicyModificationStatus DEFAULT_STATUS = createPolicyModificationStatus();
  private final Money DEFAULT_PREMIUM_ADJUSTMENT = createMoney();
  private final PolicyInformationModifier DEFAULT_POLICY_INFORMATION_MODIFIER =
      createPolicyInformationModifier();
  private final CoverageDetails DEFAULT_COVERAGE_DETAILS = createCoverageDetails();
  private final PremiumDetails DEFAULT_PREMIUM_DETAILS = createPremiumDetails();
  private final ClockProvider DEFAULT_CLOCK_PROVIDER = getClockProvider();

  private PolicyModificationId policyModificationId = DEFAULT_POLICY_MODIFICATION_ID;
  private DateTime expirationDate = DEFAULT_EXPIRATION_DATE;
  private PolicyModificationStatus status = DEFAULT_STATUS;
  private Money premiumAdjustment = DEFAULT_PREMIUM_ADJUSTMENT;
  private PolicyInformationModifier policyInformationModifier = DEFAULT_POLICY_INFORMATION_MODIFIER;
  private CoverageDetails coverageDetails = DEFAULT_COVERAGE_DETAILS;
  private PremiumDetails premiumDetails = DEFAULT_PREMIUM_DETAILS;
  private ClockProvider clockProvider = DEFAULT_CLOCK_PROVIDER;

  private PolicyModificationBuilder() {}

  public static PolicyModificationBuilder aPolicyModification() {
    return new PolicyModificationBuilder();
  }

  public PolicyModificationBuilder withPolicyModificationId(
      PolicyModificationId policyModificationId) {
    this.policyModificationId = policyModificationId;
    return this;
  }

  public PolicyModificationBuilder withStatus(PolicyModificationStatus status) {
    this.status = status;
    return this;
  }

  public PolicyModificationBuilder withExpirationDate(DateTime expirationDate) {
    this.expirationDate = expirationDate;
    return this;
  }

  public PolicyModificationBuilder withPolicyInformationModifier(
      PolicyInformationModifier policyInformationModifier) {
    this.policyInformationModifier = policyInformationModifier;
    return this;
  }

  public PolicyModification build() {
    return new PolicyModification(
        policyModificationId,
        expirationDate,
        status,
        premiumAdjustment,
        policyInformationModifier,
        coverageDetails,
        premiumDetails,
        clockProvider);
  }
}
