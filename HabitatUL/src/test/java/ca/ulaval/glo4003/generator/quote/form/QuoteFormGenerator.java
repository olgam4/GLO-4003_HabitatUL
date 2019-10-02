package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.shared.domain.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static ca.ulaval.glo4003.generator.quote.form.identity.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.generator.quote.form.identity.IdentityGenerator.createIdentityView;
import static ca.ulaval.glo4003.generator.quote.form.location.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.generator.quote.form.location.LocationGenerator.createLocationView;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest(createIdentityView(), createLocationView(), createEffectiveDate());
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto(createIdentity(), createLocation(), createEffectiveDate());
  }

  public static QuoteFormDto createQuoteFormDtoWithEffectiveDate(Date effectiveDate) {
    return new QuoteFormDto(createIdentity(), createLocation(), effectiveDate);
  }

  public static QuoteForm createQuoteForm() {
    return new QuoteForm(createIdentity(), createLocation(), createEffectiveDate());
  }

  private static Date createEffectiveDate() {
    LocalDate localDate =
        Faker.instance()
            .date()
            .future(100, TimeUnit.DAYS)
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    return Date.from(localDate);
  }
}
