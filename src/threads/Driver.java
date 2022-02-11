package threads;

import java.util.List;
import java.util.Scanner;

public class Driver {
  public DeviceClient server;

  Driver() {
    {
      try {
        server = new DeviceClientImpl(  new IThermostatImpl(), new IDoorLockImpl(), new ILightImpl(), new ICameraImpl("room",10), this);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

  }


  public void notify(String message) {
    if (message.equals("image")) {
      seeImage(message);
    }
    System.out.println(message);
  }

  public void notify(List<Integer> message) throws InterruptedException {
    Thread t1 = new Thread(() -> {
      try {
        watchStream(message);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    });
    t1.start();
  }

  public void seeImage(String image) {
    System.out.printf("This is the image %s", image);
  }

  private void watchStream(List<Integer> stream) throws InterruptedException {
    int i = 0;
    while (i++ < 5) {
      System.out.println(stream);
      Thread.sleep(4000);
    }
  }



  private  void setTemp(String mode) {
    System.out.println("Enter temp in Celsius:");
    Scanner ac = new Scanner(System.in);
    int temp = ac.nextInt();

    if (mode.equals("sh")) {
      server.setHeat(temp);
    } else {
      server.setCool(temp);
    }
  }

  public static void main(String[] args) throws InterruptedException {
    var client = new Driver();
    System.out.print("Welcome to The One A Home Automation System\nHere's what you can do:\nld - lockDoor, ud - unlocksDoor\ntlo - turnsLightOn, tlf - turnLightsOff" +
        "\ngi - generateImage, gs - generateStream, ss - stopStream\nsm - setThermostatMode, sh - setThermostatHeat, sc - setThermostatCool, tf - turnFanOn\n1 - exit\n");
    Scanner sc = new Scanner (System.in);
    String s1 = sc.next();

    while ( !s1.equals("1")) {
      switch (s1) {
         case "ld": {
           System.out.println("running ld");
           client.server.lockDoor();}
         break;
         case "ud": client.server.unlockDoor();
         break;
         case "tlo": client.server.turnLightOn();
         break;
         case "tlf": client.server.turnLightOff();
         break;
         case "gs": client.server.generateStream();
         break;
         case "ss": client.server.stopStream();
         break;
         case "sm": {
           System.out.println("You can use: off, cold, heat,");
           Scanner ac = new Scanner(System.in);
           String mode = ac.next();
           switch (mode) {
             case "off":
               client.server.setMode(Mode.OFF);
               break;
             case "cold":
               client.server.setMode(Mode.COOL);
               break;
             case "hot":
               client.server.setMode(Mode.HEAT);
               break;
           }
         }
         break;
         case "sh": {
           client.setTemp("sh");
         }
         break;
         case "sc": {
           client.setTemp("sc");
         }
         break;
         case "tf": {
           System.out.println("Enter duration in Seconds:");
           Scanner ac = new Scanner(System.in);
           int time = ac.nextInt();
           client.server.turnFan(time);
         }
         break;
         default: System.out.println("command not supported");
       }
      s1 = sc.next();
    }
    System.out.println("Done");

  }


}
