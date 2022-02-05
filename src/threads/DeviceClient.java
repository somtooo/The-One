package threads;

import threads.Events;

/**
 * Client that handles all interactions and automation between iot devices and the user.
 */
public interface DeviceClient {
  public void receiveEvents(Events event);
}
