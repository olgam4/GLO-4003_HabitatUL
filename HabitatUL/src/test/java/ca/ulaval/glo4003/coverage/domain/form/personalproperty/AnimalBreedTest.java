package ca.ulaval.glo4003.coverage.domain.form.personalproperty;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalBreedTest {
  @Test
  public void gettingEnum_withKnownAnimalBreed_shouldReturnCorrespondingAnimalBreed() {
    assertEquals(AnimalBreed.CAT, AnimalBreed.getEnum("CAT"));
    assertEquals(AnimalBreed.DOG, AnimalBreed.getEnum("DOG"));
    assertEquals(AnimalBreed.GOLD_FISH, AnimalBreed.getEnum("GOLD_FISH"));
    assertEquals(AnimalBreed.SNAKE, AnimalBreed.getEnum("SNAKE"));
  }

  @Test
  public void gettingEnum_shouldBeCaseInsensitive() {
    assertEquals(AnimalBreed.CAT, AnimalBreed.getEnum("cAt"));
  }

  @Test
  public void gettingEnum_withUnknownAnimalBreed_shouldReturnDefaultValue() {
    assertEquals(AnimalBreed.OTHER, AnimalBreed.getEnum("UNKNOWN_ANIMAL_BREED"));
  }
}
