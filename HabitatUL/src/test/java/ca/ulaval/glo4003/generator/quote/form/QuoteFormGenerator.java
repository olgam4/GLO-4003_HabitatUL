package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static ca.ulaval.glo4003.generator.quote.form.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.generator.quote.form.BuildingGenerator.createBuildingView;
import static ca.ulaval.glo4003.generator.quote.form.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.generator.quote.form.IdentityGenerator.createIdentityView;
import static ca.ulaval.glo4003.generator.quote.form.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.generator.quote.form.LocationGenerator.createLocationView;
import static ca.ulaval.glo4003.generator.quote.form.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.generator.quote.form.PersonalPropertyGenerator.createPersonalPropertyView;
import static ca.ulaval.glo4003.generator.quote.form.StudentInformationGenerator.createStudentInformation;
import static ca.ulaval.glo4003.generator.quote.form.StudentInformationGenerator.createStudentInformationView;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest(
        createIdentityView(),
        createLocationView(),
        createEffectiveDate(),
        createBuildingView(),
        createPersonalPropertyView(),
        createStudentInformationView());
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto(
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createStudentInformation());
  }

  public static QuoteForm createQuoteFormWithEffectiveDate(Date effectiveDate) {
    return new QuoteForm(
        createIdentity(),
        createLocation(),
        effectiveDate,
        createBuilding(),
        createPersonalProperty(),
        createStudentInformation());
  }

  public static QuoteForm createQuoteForm() {
    return new QuoteForm(
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createStudentInformation());
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
