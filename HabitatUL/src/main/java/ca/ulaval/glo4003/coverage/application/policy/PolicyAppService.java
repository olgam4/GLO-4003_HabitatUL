package ca.ulaval.glo4003.coverage.application.policy;

import ca.ulaval.glo4003.coverage.application.policy.dto.QuotePurchasedDto;

public class PolicyAppService {
  public void createPolicy(QuotePurchasedDto quotePurchasedDto) {
    System.out.println(quotePurchasedDto.toString());
  }
}
