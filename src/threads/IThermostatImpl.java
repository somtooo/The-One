package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IThermostatImpl implements IThermostat {

  private Mode mode;
  private double preferredTemp;
  private boolean fanOn;
  private List<DeviceClient> clients;
  private double roomTemp;


  public IThermostatImpl() {
    this.preferredTemp = 0.0;
    this.mode = Mode.OFF;
    this.fanOn = false;
    this.clients = new ArrayList<>();
    this.roomTemp = 0.0;
    setRoomTemp();

  }

  @Override
  public void setMode(Mode mode) throws InterruptedException {
    this.mode = mode;
    if (mode != Mode.OFF) {
      System.out.printf("Thermostat on and setTo mode %s \n", mode);
      this.preferredTemp = (this.mode == Mode.COOL ? 20.00 :  29.60  );

      runHvacSystem();
      notifyClient(Events.RESOURCE);

    } else {
      System.out.println("Thermostat off");
    }

  }

  private void setRoomTemp () {
    this.roomTemp =  Math.floor(Math.random()*(40-15+1)+15);
  }


  private void runHvacSystem() throws InterruptedException {
    while (this.mode != Mode.OFF) {
      if (roomTemp > preferredTemp) {
        roomTemp -= 0.08;
      } else if (roomTemp == preferredTemp) {
        turnFan(500);
      } else {
        roomTemp += 0.08;
      }
    }
  }

  @Override
  public void setHeat(double degrees) {
    if (this.mode == Mode.HEAT) {
      this.preferredTemp = degrees;
    }

    notifyClient(Events.RESOURCE);

  }

  @Override
  public void setCool(double degrees) {
    if (this.mode == Mode.COOL) {
      this.preferredTemp = degrees;
    }
    notifyClient(Events.RESOURCE);
  }


  @Override
  public void turnFan(int duration) {
    fanOn = true;
    notifyClient(Events.RESOURCE);
    while (duration-- > 0) {
      if (mode == Mode.COOL) {
        roomTemp+=0.5;
      } else if(mode == Mode.HEAT) {
        roomTemp-=0.6;
      }
    }
    fanOn = false;

  }

  @Override
  public boolean isConnected() {
    return mode != Mode.OFF;
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
