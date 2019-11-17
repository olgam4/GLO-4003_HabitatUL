package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;
import ca.ulaval.glo4003.shared.domain.ValueComparableObject;

public abstract class PolicyInformationModifier extends ValueComparableObject {
  public abstract PolicyInformation modify(PolicyInformation policyInformation);
}
