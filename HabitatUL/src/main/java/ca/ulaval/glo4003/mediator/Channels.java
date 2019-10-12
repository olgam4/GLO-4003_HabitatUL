package ca.ulaval.glo4003.mediator;

import ca.ulaval.glo4003.mediator.exception.ChannelAlreadyDefinedException;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

class Channels {
  private Map<Class, Map<Class, Function>> channelsMap = new HashMap<>();

  <T extends Event, U extends Event> void addChannel(
      Class<T> inputClass, Class<U> outputClass, Function<T, U> mapper) {
    Map<Class, Function> outputChannels = channelsMap.getOrDefault(inputClass, new HashMap<>());
    if (outputChannels.containsKey(outputClass)) {
      throw new ChannelAlreadyDefinedException(inputClass, outputClass);
    }

    outputChannels.put(outputClass, mapper);
    channelsMap.put(inputClass, outputChannels);
  }

  <T> Map<Class, Function> getChannels(Class<T> inputEventClass) {
    return channelsMap.getOrDefault(inputEventClass, new HashMap<>());
  }
}
