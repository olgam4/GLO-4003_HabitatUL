package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.IdentityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteUniversityProfileError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UlRegistrationQuoteFormValidationTest {
  private static final UniversityProfile VALID_UNIVERSITY_PROFILE =
      UniversityProfileGenerator.createUniversityProfile();
  private static final Identity IDENTITY_WITH_VALID_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(VALID_UNIVERSITY_PROFILE).build();
  private static final UniversityProfile INVALID_UNIVERSITY_PROFILE =
      UniversityProfileGenerator.createUniversityProfile();
  private static final Identity IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(INVALID_UNIVERSITY_PROFILE).build();
  private static final Identity IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(UNFILLED_UNIVERSITY_PROFILE).build();

  @Mock private UlRegistrarOffice ulRegistrarOffice;

  private UlRegistrationQuoteFormValidation subject;

  @Before
  public void setUp() {
    when(ulRegistrarOffice.isValidRegistration(
            VALID_UNIVERSITY_PROFILE.getIdul(),
            VALID_UNIVERSITY_PROFILE.getIdentificationNumber(),
            VALID_UNIVERSITY_PROFILE.getCycle(),
            VALID_UNIVERSITY_PROFILE.getDegree(),
            VALID_UNIVERSITY_PROFILE.getProgram()))
        .thenReturn(true);
    when(ulRegistrarOffice.isValidRegistration(
            INVALID_UNIVERSITY_PROFILE.getIdul(),
            INVALID_UNIVERSITY_PROFILE.getIdentificationNumber(),
            INVALID_UNIVERSITY_PROFILE.getCycle(),
            INVALID_UNIVERSITY_PROFILE.getDegree(),
            INVALID_UNIVERSITY_PROFILE.getProgram()))
        .thenReturn(false);

    subject = new UlRegistrationQuoteFormValidation(ulRegistrarOffice);
  }

  @Test
  public void validatingQuoteForm_withValidUniversityProfiles_shouldNotThrow() {
    validateValidScenario(
        IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE, IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE);
    validateValidScenario(
        IDENTITY_WITH_VALID_UNIVERSITY_PROFILE, IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE);
    validateValidScenario(
        IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE, IDENTITY_WITH_VALID_UNIVERSITY_PROFILE);
    validateValidScenario(
        IDENTITY_WITH_VALID_UNIVERSITY_PROFILE, IDENTITY_WITH_VALID_UNIVERSITY_PROFILE);
  }

  @Test(expected = QuoteUniversityProfileError.class)
  public void validatingQuoteForm_withNamedInsuredInvalidUniversityProfile_shouldThrow() {
    validateInvalidScenario(
        IDENTITY_WITH_VALID_UNIVERSITY_PROFILE, IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE);
  }

  @Test(expected = QuoteUniversityProfileError.class)
  public void validatingQuoteForm_withAdditionalInsuredInvalidUniversityProfile_shouldThrow() {
    validateInvalidScenario(
        IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE, IDENTITY_WITH_VALID_UNIVERSITY_PROFILE);
  }

  @Test(expected = QuoteUniversityProfileError.class)
  public void validatingQuoteForm_withBothInvalidUniversityProfiles_shouldThrow() {
    validateInvalidScenario(
        IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE, IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE);
  }

  private void validateValidScenario(
      Identity namedInsuredIdentity, Identity additionalInsuredIdentity) {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(namedInsuredIdentity)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    subject.validate(quoteForm);
  }

  private void validateInvalidScenario(
      Identity namedInsuredIdentity, Identity additionalInsuredIdentity) {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(namedInsuredIdentity)
            .withAdditionalInsured(additionalInsuredIdentity)
            .build();

    subject.validate(quoteForm);
  }
}
