package ca.ulaval.glo4003.mediator.newmediator;

import java.util.function.Consumer;
import java.util.function.Function;

public class Mediator {
  private ChannelMap inputChannels;
  private ChannelMap outputChannels;

  public Mediator() {
    inputChannels = new ChannelMap();
    outputChannels = new ChannelMap();
  }

  public <T> EventSender<T> createChannel(Class<T> eventClass) {
    return inputChannels.getChannel(eventClass);
  }

  public <T> void subscribe(Class<T> eventClass, Consumer<T> callback) {
    Channel<T> channel = outputChannels.getChannel(eventClass);
    channel.connect(callback);
  }

  public <T, U> void addEventMapper(
      Class<T> inputClass, Class<U> outputClass, Function<T, U> mapper) {
    Channel<T> inputChannel = inputChannels.getChannel(inputClass);
    Channel<U> outputChannel = outputChannels.getChannel(outputClass);

    inputChannel.connect(event -> outputChannel.send(mapper.apply(event)));
  }
}
