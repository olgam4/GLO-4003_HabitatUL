package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

public class InsureBicyclePolicyInformationModifier extends PolicyInformationModifier {
  private Bicycle bicycle;

  public InsureBicyclePolicyInformationModifier(Bicycle bicycle) {
    this.bicycle = bicycle;
  }

  @Override
  public PolicyInformation modify(PolicyInformation policyInformation) {
    return new PolicyInformation(
        policyInformation.getNamedInsuredIdentity(),
        policyInformation.getAdditionalInsuredIdentity(),
        policyInformation.getLocation(),
        policyInformation.getBuilding(),
        policyInformation.getAnimals(),
        bicycle);
  }
}
