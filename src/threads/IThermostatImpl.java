package threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class IThermostatImpl implements IThermostat {

  private Mode mode;
  private double preferredTemp;
  private boolean fanOn;
  private List<DeviceClient> clients;
  private double roomTemp;
  ReentrantLock lock = new ReentrantLock();




  public IThermostatImpl() {
    this.preferredTemp = 0.0;
    this.mode = Mode.OFF;
    this.fanOn = false;
    this.clients = new ArrayList<>();
    this.roomTemp = 0.0;
    setRoomTemp();


    Thread t1 = new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          runHvacSystem();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    t1.start();

  }

  @Override
  synchronized public void setMode(Mode mode) {
    lock.lock();
    try{
      this.mode = mode;
      if (mode != Mode.OFF) {
        this.preferredTemp = (this.mode == Mode.COOL ? 20.00 :  29.60  );
        notifyClient(new Message(Events.RESOURCE, "Thermostat on and setTo: " + mode));

      } else {
        notifyClient(new Message(Events.RESOURCE, "Thermostat turned off"));
      }
    } finally{
      lock.unlock();
    }



  }

  private void setRoomTemp () {
    this.roomTemp =  Math.floor(Math.random()*(40-15+1)+15);
  }


  private void runHvacSystem() throws InterruptedException {

    while (this.mode != Mode.OFF) {
      Thread.sleep(3000);
      lock.lock();
      if (roomTemp > preferredTemp) {
        roomTemp -= 0.01;
      } else if (roomTemp == preferredTemp) {
        turnFan(500);
      } else {
        roomTemp += 0.01;
      }
      lock.unlock();
    }
  }

  @Override
  public void setHeat(double degrees) {
    if (this.mode == Mode.HEAT) {
      lock.lock();
      this.preferredTemp = degrees;
      lock.unlock();
    }

    notifyClient(new Message(Events.RESOURCE, "Heat temp changed"));

  }

  @Override
  public void setCool(double degrees) {
    if (this.mode == Mode.COOL) {
      lock.lock();
      this.preferredTemp = degrees;
      lock.unlock();
    }
    notifyClient(new Message(Events.RESOURCE, "Cool temp changed"));
  }


  @Override
  synchronized public void turnFan(int duration) {
    fanOn = true;
    notifyClient(new Message(Events.RESOURCE, "Fan turned On"));
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
  synchronized public boolean isConnected() {
    return mode != Mode.OFF;
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
