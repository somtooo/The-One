package threads;

import java.util.ArrayList;
import java.util.List;

public class IDoorLockImpl  extends ICameraImpl implements IDoorLock {
  private boolean isLocked;
  private List<DeviceClient> clients;

  public IDoorLockImpl() throws InterruptedException {
    super("Door", 20);
    this.isLocked = false;
    clients = new ArrayList<>();
    Thread t1 = new Thread(() -> {
      try {
        pressBell();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
  }

  @Override
  public void lock() {
  isLocked = true;
  notifyClient(new Message(Events.RESOURCE, "Door Locked"));

  }

  private void pressBell() throws InterruptedException {
    while (true) {
      Thread.sleep(60000);
      notifyClient(new Message(Events.CHIME, "Someone at the Door"));
    }
  }

  @Override
  public void unlock() {
    isLocked = false;
    notifyClient(new Message(Events.RESOURCE, "Door Unlocked"));
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
