package ca.ulaval.glo4003.generator.quote.form;

import ca.ulaval.glo4003.gateway.presentation.quote.request.QuoteRequest;
import ca.ulaval.glo4003.shared.domain.temporal.Date;
import ca.ulaval.glo4003.underwriting.application.quote.dto.QuoteFormDto;
import ca.ulaval.glo4003.underwriting.domain.quote.form.QuoteForm;
import ca.ulaval.glo4003.underwriting.domain.quote.form.civilliability.CivilLiabilityAmount;

import static ca.ulaval.glo4003.generator.quote.form.BuildingGenerator.createBuilding;
import static ca.ulaval.glo4003.generator.quote.form.BuildingGenerator.createBuildingRequest;
import static ca.ulaval.glo4003.generator.quote.form.CivilLiabilityGenerator.*;
import static ca.ulaval.glo4003.generator.quote.form.EffectiveDateGenerator.createEffectiveDate;
import static ca.ulaval.glo4003.generator.quote.form.IdentityGenerator.createIdentity;
import static ca.ulaval.glo4003.generator.quote.form.IdentityGenerator.createIdentityRequest;
import static ca.ulaval.glo4003.generator.quote.form.LocationGenerator.createLocation;
import static ca.ulaval.glo4003.generator.quote.form.LocationGenerator.createLocationRequest;
import static ca.ulaval.glo4003.generator.quote.form.PersonalPropertyGenerator.createPersonalProperty;
import static ca.ulaval.glo4003.generator.quote.form.PersonalPropertyGenerator.createPersonalPropertyRequest;

public class QuoteFormGenerator {
  private QuoteFormGenerator() {}

  public static QuoteRequest createQuoteRequest() {
    return new QuoteRequest(
        createIdentityRequest(),
        createIdentityRequest(),
        createLocationRequest(),
        createEffectiveDate(),
        createBuildingRequest(),
        createPersonalPropertyRequest(),
        createCivilLiabilityRequest());
  }

  public static QuoteFormDto createQuoteFormDto() {
    return new QuoteFormDto(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }

  public static QuoteForm createQuoteForm() {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }

  public static QuoteForm createQuoteFormWithEffectiveDate(Date effectiveDate) {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        effectiveDate,
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }

  public static QuoteForm createQuoteFormWithCivilLiabilityAmount(
      CivilLiabilityAmount civilLiabilityAmount) {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiabilityWithAmount(civilLiabilityAmount));
  }

  public static QuoteForm createQuoteFormWithCivilLiabilityAmountAndNumberOfUnits(
      CivilLiabilityAmount civilLiabilityAmount, int numberOfUnits) {
    return new QuoteForm(
        createIdentity(),
        createIdentity(),
        createLocation(),
        createEffectiveDate(),
        BuildingGenerator.createBuildingWithNumberOfUnits(numberOfUnits),
        createPersonalProperty(),
        CivilLiabilityGenerator.createCivilLiabilityWithAmount(civilLiabilityAmount));
  }

  public static QuoteForm createQuoteFormWithoutAdditionalInsured() {
    return new QuoteForm(
        createIdentity(),
        null,
        createLocation(),
        createEffectiveDate(),
        createBuilding(),
        createPersonalProperty(),
        createCivilLiability());
  }
}
