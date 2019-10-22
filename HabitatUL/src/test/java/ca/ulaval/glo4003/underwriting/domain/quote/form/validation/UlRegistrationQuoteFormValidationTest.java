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
  private static final Identity IDENTITY_WITHOUT_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withoutUniversityProfile().build();

  @Mock private UlRegistrarOffice ulRegistrarOffice;

  private UlRegistrationQuoteFormValidation subject;

  @Before
  public void setUp() {
    when(ulRegistrarOffice.isValidRegistration(
            VALID_UNIVERSITY_PROFILE.getIdul(),
            VALID_UNIVERSITY_PROFILE.getIdentificationNumber(),
            VALID_UNIVERSITY_PROFILE.getProgram()))
        .thenReturn(true);
    when(ulRegistrarOffice.isValidRegistration(
            INVALID_UNIVERSITY_PROFILE.getIdul(),
            INVALID_UNIVERSITY_PROFILE.getIdentificationNumber(),
            INVALID_UNIVERSITY_PROFILE.getProgram()))
        .thenReturn(false);

    subject = new UlRegistrationQuoteFormValidation(ulRegistrarOffice);
  }

  @Test
  public void
      validatingUlRegistration_withValidUniversityProfileAndWithoutAdditionalInsured_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_VALID_UNIVERSITY_PROFILE)
            .withoutAdditionalInsured()
            .build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuoteUniversityProfileError.class)
  public void
      validatingUlRegistration_withInvalidUniversityProfileAndWithoutAdditionalInsured_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE)
            .withoutAdditionalInsured()
            .build();

    subject.validate(quoteForm);
  }

  @Test
  public void
      validatingUlRegistration_withValidUniversityProfileAndWithAdditionalInsuredWithoutUniversityProfile_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_VALID_UNIVERSITY_PROFILE)
            .withAdditionalInsured(IDENTITY_WITHOUT_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }

  @Test
  public void
      validatingUlRegistration_withValidUniversityProfileAndWithAdditionalInsuredWithValidUniversityProfile_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_VALID_UNIVERSITY_PROFILE)
            .withAdditionalInsured(IDENTITY_WITH_VALID_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuoteUniversityProfileError.class)
  public void
      validatingUlRegistration_withValidUniversityProfileAndWithAdditionalInsuredWithInvalidUniversityProfile_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_VALID_UNIVERSITY_PROFILE)
            .withAdditionalInsured(IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }
}
