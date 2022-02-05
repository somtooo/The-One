package threads;

public class DeviceClientImpl implements DeviceClient {

 public IThermostat thermostat;

  public DeviceClientImpl() {
    this.thermostat = new IThermostatImpl();
    thermostat.subscribe(this);
  }

  public void start(Mode mode) throws InterruptedException {
    this.thermostat.setMode(mode);
  }


  public void turnHeat(double deg) throws InterruptedException {
    thermostat.setHeat(deg);
  }

  public void isConnected() {
    System.out.println(thermostat.isConnected());
  }

  @Override
  public void receiveEvents(Events event) {
    System.out.printf("Received event of type %s \n", event);
  }
}
