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
  private String name;
  private boolean personTracking;
  private final int TRACKING_FACTOR;
  ReentrantLock lock = new ReentrantLock();


  public ICameraImpl(String name, int tracking_factor) throws InterruptedException {
    this.name = name;
    TRACKING_FACTOR = tracking_factor;
    this.personTracking = false;
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
      Thread.sleep(6000);
      lock.lock();
      try {
        // Critical section here
        if(personTracking && i%TRACKING_FACTOR==0) {
          notifyClient(new Message(Events.PERSON, name + " Camera person detected", String.valueOf(i)));

        }
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
        return urlInt + " this is the image!!";
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
  public void setPersonTracking(boolean shouldTrack) {
    personTracking = shouldTrack;
  }

  @Override
  public boolean isConnected() {
    return connected;
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
