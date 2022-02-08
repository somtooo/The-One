package threads;

import java.util.ArrayList;
import java.util.List;

public class IDoorLockImpl  extends ICameraImpl implements IDoorLock {
  private boolean isLocked;
  private List<DeviceClient> clients;


  public IDoorLockImpl() throws InterruptedException {
    super();
    this.isLocked = false;
    clients = new ArrayList<>();
  }

  @Override
  public void lock() {
  isLocked = true;
  notifyClient(Events.RESOURCE);

  }

  @Override
  public void unlock() {
    isLocked = false;
    notifyClient(Events.RESOURCE);
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
