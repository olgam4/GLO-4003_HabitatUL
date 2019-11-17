package ca.ulaval.glo4003.helper.policy;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.insuring.domain.policy.modification.modifier.PolicyInformationModifier;

import static ca.ulaval.glo4003.helper.policy.PolicyInformationGenerator.createPolicyInformation;

public class PolicyInformationModifierGenerator {
  private PolicyInformationModifierGenerator() {}

  public static PolicyInformationModifier createPolicyInformationModifier() {
    return createPolicyInformationModifier(createPolicyInformation());
  }

  public static PolicyInformationModifier createPolicyInformationModifier(
      PolicyInformation policyInformation) {
    return new DummyPolicyInformationModifier(policyInformation);
  }

  private static class DummyPolicyInformationModifier extends PolicyInformationModifier {
    private PolicyInformation modifiedPolicyInformation;

    DummyPolicyInformationModifier(PolicyInformation modifiedPolicyInformation) {
      this.modifiedPolicyInformation = modifiedPolicyInformation;
    }

    @Override
    public PolicyInformation modify(PolicyInformation policyInformation) {
      return modifiedPolicyInformation;
    }
  }
}
