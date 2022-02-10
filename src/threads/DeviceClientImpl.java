package threads;

import java.util.List;

public class DeviceClientImpl implements DeviceClient {
  private IThermostat thermostat;
  private IDoorLock doorLock;
  private  ILight light;
  private ICamera camera;

  public DeviceClientImpl() throws InterruptedException {
    this.thermostat = new IThermostatImpl();
    thermostat.subscribe(this);
    this.doorLock = new IDoorLockImpl();
    doorLock.subscribe(this);
    this.light = new ILightImpl();
    light.subscribe(this);
    this.camera = new ICameraImpl();
    camera.subscribe(this);
  }


  @Override
  public void receiveEvents(Events event) {
    System.out.printf("Received event of type %s \n", event);
  }

  @Override
  public void lockDoor() {
    doorLock.lock();
  }

  @Override
  public void unlockDoor() {
    doorLock.unlock();
  }

  @Override
  public void turnLightOn() {
    light.turnLightOn();

  }

  @Override
  public void turnLightOff() {
    light.turnLightOff();

  }

  @Override
  public String generateImage(String eventID) {
    return camera.generateImage(eventID);
  }

  @Override
  public List<Integer> generateStream() {
    return camera.generateStream();
  }

  @Override
  public void stopStream() {
    camera.stopStream();

  }

  @Override
  public void setMode(Mode mode) throws InterruptedException {
    thermostat.setMode(mode);

  }

  @Override
  public void setHeat(double degrees) {
    thermostat.setHeat(degrees);

  }

  @Override
  public void setCool(double degrees) {
    thermostat.setCool(degrees);

  }

  @Override
  public void turnFan(int duration) {
    thermostat.turnFan(duration);
  }

}
