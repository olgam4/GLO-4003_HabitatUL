package ca.ulaval.glo4003.gateway.presentation.quote.request;

public class QuoteRequest {
  private IdentityView identity;
  private LocationView location;

  public QuoteRequest(IdentityView identity, LocationView location) {
    this.identity = identity;
    this.location = location;
  }

  // IMPORTANT - KEEP FOR JACKSON SERIALIZATION
  private QuoteRequest() {}

  public IdentityView getIdentity() {
    return identity;
  }

  public LocationView getLocation() {
    return location;
  }
}
