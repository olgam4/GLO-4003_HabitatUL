package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.IdentityGenerator;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteDifferentAdditionalInsuredError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import org.junit.Before;
import org.junit.Test;

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
            .withoutAdditionalInsured()
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

  @Test(expected = QuoteDifferentAdditionalInsuredError.class)
  public void validatingQuoteForm_withEquivalentAdditionalInsured_shouldThrow() {
    QuoteForm quoteForm =
        QuoteFormBuilder.aQuoteForm()
            .withPersonalInformation(NAMED_INSURED_IDENTITY)
            .withAdditionalInsured(EQUIVALENT_IDENTITY)
            .build();

    subject.validate(quoteForm);
  }
}
