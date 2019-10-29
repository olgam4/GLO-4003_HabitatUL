package ca.ulaval.glo4003.epic.purchaseInsurance;

import ca.ulaval.glo4003.AcceptanceTestHelper;
import ca.ulaval.glo4003.helper.quote.form.QuoteFormGenerator;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class InsureAnApartmentStory {
  private QuoteAppService quoteAppService;
  private QuoteFormDto quoteFormDto;

  @BeforeClass
  public static void setUpClass() {
    AcceptanceTestHelper.initialize();
  }

  @Before
  public void setUp() {
    quoteAppService = new QuoteAppService();
    quoteFormDto = QuoteFormGenerator.createQuoteFormDto();
  }

  @Test
  public void anonymousStudentWantsToReceiveQuotePrice() {
    QuoteDto quoteDto = quoteAppService.requestQuote(quoteFormDto);

    assertNotNull(quoteDto.getPrice());
  }
}
