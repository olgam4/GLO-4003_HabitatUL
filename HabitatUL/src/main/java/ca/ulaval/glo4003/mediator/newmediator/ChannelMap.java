package ca.ulaval.glo4003.mediator.newmediator;

import java.util.HashMap;
import java.util.Map;

public class ChannelMap {
  private Map<Class<?>, Channel<?>> channels;

  public ChannelMap() {
    channels = new HashMap<>();
  }

  public <T> Channel<T> getChannel(Class<T> channelClass) {
    Channel<T> channel = (Channel<T>) channels.get(channelClass);

    if (channel == null) {
      channel = new Channel<>();
      channels.put(channelClass, channel);
    }

    return channel;
  }
}
