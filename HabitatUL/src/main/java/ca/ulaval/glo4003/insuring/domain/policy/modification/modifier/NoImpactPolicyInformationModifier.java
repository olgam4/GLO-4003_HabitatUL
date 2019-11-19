package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

public class NoImpactPolicyInformationModifier extends PolicyInformationModifier {
  @Override
  public PolicyInformation modify(PolicyInformation policyInformation) {
    return new PolicyInformation(
        policyInformation.getNamedInsuredIdentity(),
        policyInformation.getAdditionalInsuredIdentity(),
        policyInformation.getLocation(),
        policyInformation.getBuilding(),
        policyInformation.getAnimals(),
        policyInformation.getBicycle());
  }
}
