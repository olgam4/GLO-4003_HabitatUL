package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class PolicyMatcher {
  private PolicyMatcher() {}

  public static Matcher<Policy> matchesPolicy(final Policy policy) {
    return allOf(
        hasProperty("policyId", equalTo(policy.getPolicyId())),
        hasProperty("quoteKey", equalTo(policy.getQuoteKey())));
  }

  public static Matcher<Policy> matchesPolicy(final PolicyPurchasedEvent event) {
    return hasProperty("quoteKey", equalTo(event.getQuoteKey()));
  }

  public static Matcher<BicycleEndorsementForm> matchesBicycleEndorsementForm(
      final Policy policy, final InsureBicycleDto insureBicycleDto) {
    return allOf(
        hasProperty("bicycle", equalTo(insureBicycleDto.getBicycle())),
        hasProperty("currentCoverageDetails", equalTo(policy.getCurrentCoverageDetails())),
        hasProperty("currentPremiumDetails", equalTo(policy.getCurrentPremiumDetails())));
  }

  public static Matcher<PolicyModificationDto> matchesPolicyModificationDto(
      final PolicyModification policyModification) {
    return allOf(
        hasProperty("policyModificationId", equalTo(policyModification.getPolicyModificationId())),
        hasProperty("expirationDate", equalTo(policyModification.getExpirationDate())),
        hasProperty("status", equalTo(policyModification.getStatus())),
        hasProperty("premiumAdjustment", equalTo(policyModification.getPremiumAdjustment())),
        hasProperty(
            "proposedCoverageDetails", equalTo(policyModification.getProposedCoverageDetails())),
        hasProperty(
            "proposedPremiumDetails", equalTo(policyModification.getProposedPremiumDetails())));
  }
}
