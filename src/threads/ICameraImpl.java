package threads;

import java.util.ArrayList;
import java.util.List;

public class ICameraImpl implements ICamera {
  private List<DeviceClient> clients;
  private boolean connected;
  private List<Integer> stream;
  private Thread t1;
  private Thread t2;
  private List<Integer> copyStream;

  public ICameraImpl() throws InterruptedException {
    this.clients = new ArrayList<>();
    copyStream = new ArrayList<>();
    connected = true;
    stream = new ArrayList<>();
    t1 = new Thread(() -> {
      try {
        runCamera();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
  }

  private void runCamera() throws InterruptedException {
    int i = 0;
    while (connected) {
      Thread.sleep(3000);
      stream.add(++i);
    }
  }



  @Override
  public String generateImage(String eventId) {
    for (Integer urlInt : stream ) {
      if (urlInt == Integer.parseInt(eventId)) {
        return String.valueOf(urlInt);
      }

    }
    return "url";
  }

  @Override
  public List<Integer> generateStream() {
    copyStream = new ArrayList<>(stream);
    return copyStream;
  }

  @Override
  public void stopStream() {
    //end url
  }

  @Override
  public boolean isConnected() {
    return connected;
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
