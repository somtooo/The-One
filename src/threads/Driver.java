package threads;

public class Driver {
  static DeviceClient server;

  static {
    try {
      server = new DeviceClientImpl();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {

  }
}
