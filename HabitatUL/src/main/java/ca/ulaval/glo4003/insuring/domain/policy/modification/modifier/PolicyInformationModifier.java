package ca.ulaval.glo4003.insuring.domain.policy.modification.modifier;

import ca.ulaval.glo4003.insuring.domain.policy.PolicyInformation;

public interface PolicyInformationModifier {
  PolicyInformation modify(PolicyInformation policyInformation);
}
