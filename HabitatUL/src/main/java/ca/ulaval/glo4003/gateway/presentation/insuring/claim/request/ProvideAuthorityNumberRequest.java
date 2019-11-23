package ca.ulaval.glo4003.gateway.presentation.insuring.claim.request;

import ca.ulaval.glo4003.shared.domain.authority.AuthorityNumber;

import javax.validation.constraints.NotNull;

public class ProvideAuthorityNumberRequest {
  @NotNull private AuthorityNumber authorityNumber;

  private ProvideAuthorityNumberRequest() {}

  public ProvideAuthorityNumberRequest(AuthorityNumber authorityNumber) {
    this.authorityNumber = authorityNumber;
  }

  public AuthorityNumber getAuthorityNumber() {
    return authorityNumber;
  }
}
