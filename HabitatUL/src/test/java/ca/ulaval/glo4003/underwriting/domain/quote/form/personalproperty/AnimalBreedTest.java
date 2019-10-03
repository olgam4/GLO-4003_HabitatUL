package ca.ulaval.glo4003.underwriting.domain.quote.form.personalproperty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalBreedTest {
  @Test
  public void gettingEnum_withKnownAnimal_shouldReturnCorrespondingAnimal() {
    assertEquals(AnimalBreed.CAT, AnimalBreed.getEnum("CAT"));
    assertEquals(AnimalBreed.DOG, AnimalBreed.getEnum("DOG"));
    assertEquals(AnimalBreed.FISH, AnimalBreed.getEnum("FISH"));
    assertEquals(AnimalBreed.SNAKE, AnimalBreed.getEnum("SNAKE"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() {
    assertEquals(AnimalBreed.CAT, AnimalBreed.getEnum("cAt"));
  }

  @Test
  public void gettingEnum_withUnknownAnimal_shouldReturnDefaultValue() {
    assertEquals(AnimalBreed.OTHER, AnimalBreed.getEnum("WAPITI"));
  }
}
