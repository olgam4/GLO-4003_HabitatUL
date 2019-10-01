package ca.ulaval.glo4003.gateway.presentation.quote;

import ca.ulaval.glo4003.gateway.presentation.quote.request.IdentityView;
import ca.ulaval.glo4003.gateway.presentation.quote.request.LocationView;
import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.location.Location;

public class QuoteViewAssembler {
  public QuoteFormDto from(QuoteRequest quoteRequest) {
    return new QuoteFormDto(from(quoteRequest.getIdentity()), from(quoteRequest.getLocation()));
  }

  private Identity from(IdentityView identityView) {
    return new Identity(
        identityView.getFirstName(), identityView.getLastName(), identityView.getGender());
  }

  private Location from(LocationView locationView) {
    return new Location(
        locationView.getPostalCode(),
        locationView.getStreetNumber(),
        locationView.getApartmentNumber(),
        locationView.getFloor());
  }
}
