package ca.ulaval.glo4003.insuring.application.policy.dto;

import ca.ulaval.glo4003.coverage.domain.form.personalproperty.Bicycle;
import ca.ulaval.glo4003.shared.application.DataTransferObject;

public class ModifyPolicyDto extends DataTransferObject {
  private final Bicycle bicycle;

  public ModifyPolicyDto(Bicycle bicycle) {
    this.bicycle = bicycle;
  }

  public Bicycle getBicycle() {
    return bicycle;
  }
}
