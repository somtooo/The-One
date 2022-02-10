package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class ICameraImpl implements ICamera {
  private List<DeviceClient> clients;
  private boolean connected;
  private List<Integer> imageStream;
  private Thread t1;
  private List<Integer> copyStream;
  private int i = 0;
  private  int streamTime = 15;
  private boolean stop;
  ReentrantLock lock = new ReentrantLock();


  public ICameraImpl() throws InterruptedException {
    this.clients = new ArrayList<>();
    copyStream = new ArrayList<>();
    connected = true;
    imageStream = new ArrayList<>();
    stop = false;
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
    while (connected) {
      Thread.sleep(3000);
      if (i%10 ==0) {
        notifyClient();
      }
      lock.lock();
      try {
        // Critical section here
        if (!stop) {
          copyStream.add(i);
        }
        if (streamTime <=0) {
          stopStream();
        }
        ++i;
        streamTime = ( streamTime <= 0) ? streamTime -1: 0;
        imageStream.add(i);  //10,11

      } finally {
        lock.unlock();
      }


    }
  }


  @Override
  public String generateImage(String eventId) {
    for (Integer urlInt : imageStream) {
      if (urlInt == Integer.parseInt(eventId)) {
        return String.valueOf(urlInt);
      }

    }
    return "";
  }


  @Override
  public List<Integer> generateStream() {
    lock.lock();
    try {
      // Critical section here
      stop = false;
      streamTime = 15;
      copyStream = new ArrayList<>();
    } finally {
      lock.unlock();
    }
    return copyStream;
  }

  @Override
  public void stopStream() {
    lock.lock();
    try {
      // Critical section here
      stop = true;
    } finally {
      lock.unlock();
    }

  }

  @Override
  public boolean isConnected() {
    return connected;
  }

  @Override
  public void subscribe(DeviceClient client) {
    clients.add(client);
  }

  private void notifyClient() {
    for (DeviceClient client : clients ) {
      client.receiveEvents(Events.PERSON);
    }
  }
}
