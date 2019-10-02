package ca.ulaval.glo4003.underwriting.domain.quote.form;

import ca.ulaval.glo4003.shared.domain.ValueObject;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

public class QuoteForm extends ValueObject {
  private Identity identity;
  private Location location;

  public QuoteForm(Identity identity, Location location) {
    this.identity = identity;
    this.location = location;
  }

  public Identity getIdentity() {
    return identity;
  }

  public Location getLocation() {
    return location;
  }
}
