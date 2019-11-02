package ca.ulaval.glo4003.underwriting.domain.quote.form.building;

import ca.ulaval.glo4003.shared.domain.ValueObject;

import java.util.HashSet;
import java.util.Set;

public class PreventionSystems extends ValueObject {
  public static final PreventionSystems UNFILLED_PREVENTION_SYSTEMS =
      new PreventionSystems(new HashSet<>());

  private final Set<PreventionSystem> collection;

  public PreventionSystems(Set<PreventionSystem> collection) {
    this.collection = collection;
  }

  public Set<PreventionSystem> getCollection() {
    return collection;
  }
}
