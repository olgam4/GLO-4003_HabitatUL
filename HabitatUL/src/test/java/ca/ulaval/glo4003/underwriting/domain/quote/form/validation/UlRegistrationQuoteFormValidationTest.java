package ca.ulaval.glo4003.underwriting.domain.quote.form.validation;

import ca.ulaval.glo4003.helper.quote.form.IdentityBuilder;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormBuilder;
import ca.ulaval.glo4003.helper.quote.form.UniversityProfileGenerator;
import ca.ulaval.glo4003.underwriting.domain.quote.error.QuoteUniversityProfileError;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.Identity;
import ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Arrays;
import java.util.Collection;

import static ca.ulaval.glo4003.helper.ParameterizedTestHelper.PARAMETERIZED_TEST_TITLE;
import static ca.ulaval.glo4003.underwriting.domain.quote.form.identity.UniversityProfile.UNFILLED_UNIVERSITY_PROFILE;
import static org.mockito.Mockito.when;

@RunWith(Enclosed.class)
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

  @RunWith(Parameterized.class)
  public static class ValidTestCase extends TestCase {
    public ValidTestCase(
        String title, Identity namedInsuredIdentity, Identity additionalInsuredIdentity) {
      super(namedInsuredIdentity, additionalInsuredIdentity);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with named and additional insureds unfilled university profile should not throw",
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE
            },
            {
              "with named insured valid university profile should not throw",
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE
            },
            {
              "with additional insured valid university profile should not throw",
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE
            },
            {
              "with named and additional insureds valid university profile should not throw",
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE
            },
          });
    }

    @Test
    public void validatingQuoteForm() {
      super.validatingQuoteForm();
    }
  }

  @RunWith(Parameterized.class)
  public static class InvalidTestCase extends TestCase {
    public InvalidTestCase(
        String title, Identity namedInsuredIdentity, Identity additionalInsuredIdentity) {
      super(namedInsuredIdentity, additionalInsuredIdentity);
    }

    @Parameterized.Parameters(name = PARAMETERIZED_TEST_TITLE)
    public static Collection parameters() {
      return Arrays.asList(
          new Object[][] {
            {
              "with named insured invalid university profile should throw",
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE
            },
            {
              "with additional insured invalid university profile should throw",
              IDENTITY_WITH_UNFILLED_UNIVERSITY_PROFILE,
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE
            },
            {
              "with named insured invalid and additional insured valid university profile should throw",
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE
            },
            {
              "with named insured valid and additional insured invalid university profile should throw",
              IDENTITY_WITH_VALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE
            },
            {
              "with named and additional insureds invalid university profile should throw",
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE,
              IDENTITY_WITH_INVALID_UNIVERSITY_PROFILE
            },
          });
    }

    @Test(expected = QuoteUniversityProfileError.class)
    public void validatingQuoteForm() {
      super.validatingQuoteForm();
    }
  }

  public abstract static class TestCase {
    private final Identity namedInsuredIdentity;
    private final Identity additionalInsuredIdentity;
    @Rule public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock private UlRegistrarOffice ulRegistrarOffice;

    private UlRegistrationQuoteFormValidation subject;

    public TestCase(Identity namedInsuredIdentity, Identity additionalInsuredIdentity) {
      this.namedInsuredIdentity = namedInsuredIdentity;
      this.additionalInsuredIdentity = additionalInsuredIdentity;
    }

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

    public void validatingQuoteForm() {
      QuoteForm quoteForm =
          QuoteFormBuilder.aQuoteForm()
              .withPersonalInformation(namedInsuredIdentity)
              .withAdditionalInsured(additionalInsuredIdentity)
              .build();

      subject.validate(quoteForm);
    }
  }
}
