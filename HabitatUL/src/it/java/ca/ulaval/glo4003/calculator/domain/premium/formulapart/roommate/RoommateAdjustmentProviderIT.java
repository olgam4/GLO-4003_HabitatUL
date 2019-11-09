package ca.ulaval.glo4003.calculator.domain.premium.formulapart.roommate;

import ca.ulaval.glo4003.calculator.domain.premium.adjustment.PremiumAdjustment;
import ca.ulaval.glo4003.calculator.domain.premium.formula.quote.input.GenderInput;
import org.junit.Before;
import org.junit.Test;

import static ca.ulaval.glo4003.helper.premium.QuotePremiumInputGenerator.createGenderInput;
import static org.junit.Assert.assertNotNull;

public abstract class RoommateAdjustmentProviderIT {
  private static final GenderInput NAMED_INSURED_GENDER = createGenderInput();
  private static final GenderInput ROOMMATE_GENDER = createGenderInput();

  private RoommateAdjustmentProvider subject;

  @Before
  public void setUp() {
    subject = createSubject();
  }

  @Test
  public void gettingAdjustment_shouldProvideAdjustment() {
    PremiumAdjustment adjustment = subject.getAdjustment(NAMED_INSURED_GENDER, ROOMMATE_GENDER);

    assertNotNull(adjustment);
  }

  public abstract RoommateAdjustmentProvider createSubject();
}
