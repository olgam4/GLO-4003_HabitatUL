package ca.ulaval.glo4003.coverage.domain.form.validationpart;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.validationpart.error.StudentNamedInsuredError;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.identity.IdentityBuilder;
import ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;

public class StudentNamedInsuredFormValidationPartTest {
  private static final Identity IDENTITY_WITH_FILLED_UNIVERSITY_PROFILE =
      IdentityGenerator.createIdentity();
  private static final Identity IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(UNFILLED_UNIVERSITY_PROFILE).build();

  private StudentNamedInsuredFormValidationPart subject;

  @Before
  public void setUp() {
    subject = new StudentNamedInsuredFormValidationPart();
  }

  @Test
  public void validatingQuoteForm_withFilledNamedInsuredUniversityProfile_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_FILLED_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }

  @Test(expected = StudentNamedInsuredError.class)
  public void validatingQuoteForm_withUnfilledNamedInsuredUniversityProfile_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }
}
