package ca.ulaval.glo4003.coverage.domain.form.validation;

import ca.ulaval.glo4003.coverage.domain.form.QuoteForm;
import ca.ulaval.glo4003.coverage.domain.form.identity.Identity;
import ca.ulaval.glo4003.coverage.domain.form.identity.UniversityProfile;
import ca.ulaval.glo4003.coverage.domain.form.validation.error.DifferentAdditionalInsuredError;
import ca.ulaval.glo4003.helper.coverage.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.coverage.form.identity.IdentityGenerator;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.coverage.domain.form.identity.Identity.UNFILLED_IDENTITY;

public class DifferentAdditionalInsuredQuoteFormValidationTest {
  private static final Identity NAMED_INSURED_IDENTITY = IdentityGenerator.createIdentity();
  private static final Identity DIFFERENT_IDENTITY = IdentityGenerator.createIdentity();
  private static final Identity EQUIVALENT_IDENTITY =
      createEquivalentIdentity(NAMED_INSURED_IDENTITY);

  private DifferentAdditionalInsuredQuoteFormValidation subject;

  private static Identity createEquivalentIdentity(Identity identity) {
    return new Identity(
        identity.getFirstName(),
        identity.getLastName(),
        identity.getBirthDate(),
        identity.getGender(),
        new UniversityProfile(
            identity.getUniversityProfile().getIdul(),
            identity.getUniversityProfile().getIdentificationNumber(),
            identity.getUniversityProfile().getProgram()));
  }

  @Before
  public void setUp() {
    subject = new DifferentAdditionalInsuredQuoteFormValidation();
  }

  @Test
  public void validatingQuoteForm_withoutAdditionalInsured_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(NAMED_INSURED_IDENTITY)
            .withAdditionalInsured(UNFILLED_IDENTITY)
            .build();

    subject.validate(quoteForm);
  }

  @Test
  public void validatingQuoteForm_withDifferentAdditionalInsured_shouldNotThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(NAMED_INSURED_IDENTITY)
            .withAdditionalInsured(DIFFERENT_IDENTITY)
            .build();

    subject.validate(quoteForm);
  }

  @Test(expected = DifferentAdditionalInsuredError.class)
  public void validatingQuoteForm_withEquivalentAdditionalInsured_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(NAMED_INSURED_IDENTITY)
            .withAdditionalInsured(EQUIVALENT_IDENTITY)
            .build();

    subject.validate(quoteForm);
  }
}
