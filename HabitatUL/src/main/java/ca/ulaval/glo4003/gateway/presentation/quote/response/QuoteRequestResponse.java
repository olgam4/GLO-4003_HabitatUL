package ca.ulaval.glo4003.gateway.presentation.quote.response;

import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteDto;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class QuoteRequestResponse {
  private BigDecimal premium;
  private UUID quoteId;
  private String expirationDate;

  public QuoteRequestResponse(QuoteDto quoteDto) {
    this.premium = quoteDto.getPremium().getValue().setScale(2, RoundingMode.HALF_UP);
    this.quoteId = quoteDto.getQuoteId().getValue();
    this.expirationDate = quoteDto.getExpirationDate().getValue().toString();
  }

  public BigDecimal getPremium() {
    return premium;
  }

  public UUID getQuoteId() {
    return quoteId;
  }

  public String getExpirationDate() {
    return expirationDate;
  }
}
