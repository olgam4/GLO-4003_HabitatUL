package ca.ulaval.glo4003.underwriting.domain.quote;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class QuoteIdTest {
  private static final UUID UUID_VALUE = UUID.randomUUID();

  private QuoteId subject;

  @Before
  public void setUp() {
    subject = new QuoteId(UUID_VALUE.toString());
  }

  @Test
  public void quoteIdRepresentation_shouldReturnStringifiedValue() {
    String representation = subject.toRepresentation();

    assertEquals(UUID_VALUE.toString(), representation);
  }
}
