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
    notifyClient(Events.RESOURCE);
  }

  @Override
  public void turnLightOff() {
    isLightOn = false;
    notifyClient(Events.RESOURCE);
  }

  @Override
  public boolean isConnected() {
    return true;
  }

  @Override
  public void subscribe(DeviceClient client) {
    clients.add(client);
  }

  private void notifyClient(Events resource) {
    for (DeviceClient client : clients ) {
      client.receiveEvents(resource);
    }
  }
}
