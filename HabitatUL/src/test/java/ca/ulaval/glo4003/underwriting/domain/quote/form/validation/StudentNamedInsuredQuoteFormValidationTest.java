package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.IdentityBuilder;
import ca.ulaval.glo4003.helper.quote.form.IdentityGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteStudentNamedInsuredError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;

public class StudentNamedInsuredQuoteFormValidationTest {
  private static final Identity IDENTITY_WITH_FILLED_UNIVERSITY_PROFILE =
      IdentityGenerator.createIdentity();
  private static final Identity IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE =
      IdentityBuilder.anIdentity().withUniversityProfile(UNFILLED_UNIVERSITY_PROFILE).build();

  private StudentNamedInsuredQuoteFormValidation subject;

  @Before
  public void setUp() {
    subject = new StudentNamedInsuredQuoteFormValidation();
  }

  @Test
  public void validatingQuoteForm_withFilledNamedInsuredUniversityProfile_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_FILLED_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }

  @Test(expected = QuoteStudentNamedInsuredError.class)
  public void validatingQuoteForm_withUnfilledNamedInsuredUniversityProfile_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE)
            .build();

    subject.validate(quoteForm);
  }
}
