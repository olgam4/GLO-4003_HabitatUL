package ca.ulaval.glo4003.epic.purchaseInsurance;

import ca.ulaval.glo4003.AcceptanceTestHelper;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppService;
import ca.ulaval.glo4003.underwriting.application.quote.QuoteAppServiceImpl;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;
import ca.ulaval.glo4003.underwriting.application.quote.dto.RequestQuoteDto;
import ca.ulaval.glo4003.underwriting.helper.quote.QuoteGenerator;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class InsureAnApartmentStory {
  private QuoteAppService quoteAppService;
  private RequestQuoteDto requestQuoteDto;

  @BeforeClass
  public static void setUpClass() {
    AcceptanceTestHelper.initialize();
  }

  @Before
  public void setUp() {
    quoteAppService = new QuoteAppServiceImpl();
    requestQuoteDto = QuoteGenerator.createRequestQuoteDto();
  }

  @Test
  public void anonymousStudentWantsToReceiveQuotePremium() {
    QuoteDto quoteDto = quoteAppService.requestQuote(requestQuoteDto);

    assertNotNull(quoteDto.getPremiumDetails());
    assertNotNull(quoteDto.getPremiumDetails().computeTotalPremium());
  }
}
