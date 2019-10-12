package ca.ulaval.glo4003.generator;

import ca.ulaval.glo4003.mediator.Event;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EventGenerator {
  public static List<Event> createList(int length) {
    return IntStream.range(0, length).mapToObj(i -> createEvent()).collect(Collectors.toList());
  }

  public static Event createEvent() {
    return new DummyEvent();
  }

  private static class DummyEvent extends Event {}
}
