package threads;

import threads.Events;

import java.util.List;

/**
 * Client that handles all interactions and automation between iot devices and the user.
 */
public interface DeviceClient {
  public void receiveEvents(Events event);

  /**
   * Locks the door.
   */
  void lockDoor();


  /**
   * Unlocks the door.
   */
  void unlockDoor();

  /**
   * Turns light on.
   */
  void turnLightOn();

  /**
   * Turns lights off;
   */
  void turnLightOff();

  /**
   * Generates an image from an event. See events to see supported camera events. if id not presents generates image
   * showing current state of camera surroundings.
   * @return url to download image from
   */
  String generateImage(String eventId);

  /**
   * Generates a live stream from the camera. Stream is only active for 5 min.
   * @return the url to watch the camera's live stream.
   */
  List<Integer> generateStream();

  /**
   * Stops stream.
   */
  void stopStream();

  /**
   * Changes the behaviour of the thermostat.
   * @param mode the specified behaviour. It ranges from Heat,Cool and OFF
  //@return true if behaviour was changed or false if there was an error.
   */
  void setMode(Mode mode) throws InterruptedException;

  /**
   * Changes the temperature setPoint must be in HEAT mode to work. Note this is
   * not the temperature of the device but the temperature of the room the device
   * is in charge of.
   * @param degrees the degree in Celsius to set the heat to.
   */
  void setHeat(double degrees);

  /**
   * Changes the temperature setPoint must be in COOL mode to work. Note this is * not the
   * temperature of the device but the temperature of the room the device * is in charge of.
   * @param degrees the degree in Celsius to set the cool mode temp to.
   */
  void setCool(double degrees);


  /**
   * Runs the thermostat fan for the specified duration. Note that command
   * may not produce desired result because the fan can be engaged either
   * manually or by the HVAC cycle.
   * @param duration time in seconds to run fan.
   */
  void turnFan(int duration);
}
