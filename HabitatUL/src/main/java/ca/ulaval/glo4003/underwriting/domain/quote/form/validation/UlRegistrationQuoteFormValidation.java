package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteUniversityProfileError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;

import java.util.Optional;

public class UlRegistrationQuoteFormValidation implements QuoteFormValidation {
  private UlRegistrarOffice ulRegistrarOffice;

  public UlRegistrationQuoteFormValidation(UlRegistrarOffice ulRegistrarOffice) {
    this.ulRegistrarOffice = ulRegistrarOffice;
  }

  @Override
  public void validate(QuoteForm quoteForm) {
    validateNamedInsuredUniversityProfile(quoteForm);
    validateAdditionalInsuredUniversityProfile(quoteForm);
  }

  private void validateNamedInsuredUniversityProfile(QuoteForm quoteForm) {
    UniversityProfile namedInsuredUniversityProfile =
        quoteForm.getPersonalInformation().getUniversityProfile();
    validateUniversityProfile(namedInsuredUniversityProfile);
  }

  private void validateAdditionalInsuredUniversityProfile(QuoteForm quoteForm) {
    Optional<Identity> additionalInsured = Optional.ofNullable(quoteForm.getAdditionalInsured());
    Optional<UniversityProfile> additionalInsuredUniversityProfile =
        additionalInsured.map(Identity::getUniversityProfile);
    additionalInsuredUniversityProfile.ifPresent(this::validateUniversityProfile);
  }

  private void validateUniversityProfile(UniversityProfile universityProfile) {
    String idul = universityProfile.getIdul();
    String identificationNumber = universityProfile.getIdentificationNumber();
    String program = universityProfile.getProgram();
    if (!ulRegistrarOffice.isValidRegistration(idul, identificationNumber, program)) {
      throw new QuoteUniversityProfileError();
    }
  }
}
