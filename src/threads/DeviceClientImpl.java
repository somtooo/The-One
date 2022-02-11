package threads;

import java.util.List;

public class DeviceClientImpl implements DeviceClient {
  private IThermostat thermostat;
  private IDoorLock doorLock;
  private  ILight light;
  private ICamera camera;
  private Driver client;
  private String personEventId;

  public DeviceClientImpl(IThermostat thermostat, IDoorLock doorLock, ILight light, ICamera camera, Driver client) {
    this.thermostat = thermostat;
    thermostat.subscribe(this);
    this.doorLock = doorLock;
    doorLock.subscribe(this);
    this.light = light;
    light.subscribe(this);
    this.camera = camera;
    camera.subscribe(this);
    this.client = client;
    this.personEventId = "";
  }




  @Override
  public void receiveEvents(Message message) {
    client.notify(message.message);
    if (message.event == Events.PERSON) {
      personEventId = message.id;
      if (!message.message.contains("Door")) {
        turnLightOn();
      }
    }

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
  public void generateImage() {
    client.notify(camera.generateImage(personEventId));

  }

  @Override
  public void generateStream() throws InterruptedException {
    client.notify(camera.generateStream());
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
