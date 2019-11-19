package ca.ulaval.glo4003.matcher;

import ca.ulaval.glo4003.coverage.domain.form.BicycleEndorsementForm;
import ca.ulaval.glo4003.coverage.domain.form.CoverageModificationForm;
import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.gateway.presentation.coverage.request.BicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.InsureBicycleRequest;
import ca.ulaval.glo4003.gateway.presentation.insuring.policy.request.ModifyCoverageRequest;
import ca.ulaval.glo4003.insuring.application.policy.dto.InsureBicycleDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.ModifyCoverageDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyDto;
import ca.ulaval.glo4003.insuring.application.policy.dto.PolicyModificationDto;
import ca.ulaval.glo4003.insuring.application.policy.event.PolicyPurchasedEvent;
import ca.ulaval.glo4003.insuring.domain.policy.Policy;
import ca.ulaval.glo4003.insuring.domain.policy.modification.PolicyModification;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class PolicyMatcher {
  private PolicyMatcher() {}

  public static Matcher<PolicyDto> matchesPolicyDto(final Policy policy) {
    return allOf(
        hasProperty("policyId", equalTo(policy.getPolicyId())),
        hasProperty("coveragePeriod", equalTo(policy.getCoveragePeriod())),
        hasProperty("coverageDetails", equalTo(policy.getCoverageDetails())),
        hasProperty("premiumDetails", equalTo(policy.getPremiumDetails())));
  }

  public static Matcher<Policy> matchesPolicy(final Policy policy) {
    return allOf(
        hasProperty("policyId", equalTo(policy.getPolicyId())),
        hasProperty("quoteKey", equalTo(policy.getQuoteKey())));
  }

  public static Matcher<Policy> matchesPolicy(final PolicyPurchasedEvent event) {
    return hasProperty("quoteKey", equalTo(event.getQuoteKey()));
  }

  public static Matcher<InsureBicycleDto> matchesInsureBicycleDto(
      final InsureBicycleRequest insureBicycleRequest) {
    return allOf(hasProperty("bicycle", matchesBicycle(insureBicycleRequest.getBicycle())));
  }

  public static Matcher<ModifyCoverageDto> matchesModifyCoverageDto(
      final ModifyCoverageRequest modifyCoverageRequest) {
    return allOf(
        hasProperty(
            "personalPropertyCoverageAmount", equalTo(modifyCoverageRequest.getPersonalProperty())),
        hasProperty("civilLiabilityLimit", equalTo(modifyCoverageRequest.getCivilLiability())));
  }

  private static Matcher<Bicycle> matchesBicycle(final BicycleRequest bicycleRequest) {
    return allOf(
        hasProperty("price", equalTo(bicycleRequest.getPrice())),
        hasProperty("brand", equalTo(bicycleRequest.getBrand())),
        hasProperty("model", equalTo(bicycleRequest.getModel())),
        hasProperty("year", equalTo(bicycleRequest.getYear())));
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

  public static Matcher<BicycleEndorsementForm> matchesBicycleEndorsementForm(
      final Policy policy, final InsureBicycleDto insureBicycleDto) {
    return allOf(
        hasProperty("bicycle", equalTo(insureBicycleDto.getBicycle())),
        hasProperty("currentCoverageDetails", equalTo(policy.getCoverageDetails())),
        hasProperty("currentPremiumDetails", equalTo(policy.getPremiumDetails())));
  }

  public static Matcher<CoverageModificationForm> matchesCoverageModificationForm(
      final Policy policy, final ModifyCoverageDto modifyCoverageDto) {
    return allOf(
        hasProperty(
            "personalPropertyCoverageAmount",
            equalTo(modifyCoverageDto.getPersonalPropertyCoverageAmount())),
        hasProperty("civilLiabilityLimit", equalTo(modifyCoverageDto.getCivilLiabilityLimit())),
        hasProperty("currentCoverageDetails", equalTo(policy.getCoverageDetails())),
        hasProperty("currentPremiumDetails", equalTo(policy.getPremiumDetails())));
  }
}
