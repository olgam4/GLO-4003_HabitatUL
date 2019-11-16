package ca.ulaval.glo4003.coverage.domain.form.validation.part;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProgram;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.UniversityProfileError;
import ca.ulaval.glo4003.coverage.domain.form.validation.quote.QuoteFormValidationPart;

public class UlRegistrationFormValidationPart implements QuoteFormValidationPart {
  private UlRegistrarOffice ulRegistrarOffice;

  public UlRegistrationFormValidationPart(UlRegistrarOffice ulRegistrarOffice) {
    this.ulRegistrarOffice = ulRegistrarOffice;
  }

  @Override
  public void validate(QuoteForm form) {
    validateNamedInsuredUniversityProfile(form);
    validateAdditionalInsuredUniversityProfile(form);
  }

  private void validateNamedInsuredUniversityProfile(QuoteForm form) {
    UniversityProfile namedInsuredUniversityProfile =
        form.getPersonalInformation().getUniversityProfile();
    validateUniversityProfile(namedInsuredUniversityProfile);
  }

  private void validateAdditionalInsuredUniversityProfile(QuoteForm form) {
    UniversityProfile additionalInsuredUniversityProfile =
        form.getAdditionalInsured().getUniversityProfile();
    validateUniversityProfile(additionalInsuredUniversityProfile);
  }

  private void validateUniversityProfile(UniversityProfile universityProfile) {
    if (universityProfile.isFilled()) {
      String idul = universityProfile.getIdul();
      String identificationNumber = universityProfile.getIdentificationNumber();
      UniversityProgram program = universityProfile.getProgram();
      if (!ulRegistrarOffice.isValidRegistration(idul, identificationNumber, program)) {
        throw new UniversityProfileError();
      }
    }
  }
}
