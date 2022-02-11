package threads;

import java.util.ArrayList;
import java.util.List;

public class ILightImpl implements ILight {
  private List<DeviceClient> clients;
  private boolean isLightOn;
  private boolean isConnected;

  ILightImpl() {
    this.clients = new ArrayList<>();
    this.isLightOn = false;
    this.isConnected = true;
  }

  @Override
  public void turnLightOn() {
    isLightOn = true;
    notifyClient(new Message(Events.RESOURCE, "Light is On"));
  }

  @Override
  public void turnLightOff() {
    isLightOn = false;
    notifyClient(new Message(Events.RESOURCE, "Light is On"));
  }

  @Override
  public boolean isConnected() {
    return true;
  }

  @Override
  public void subscribe(DeviceClient client) {
    clients.add(client);
  }

  private void notifyClient(Message message) {
    for (DeviceClient client : clients ) {
      client.receiveEvents(message);
    }
  }
}
