package threads;

/*
Represents an iot light.
 */
public interface ILight {
  /**
   * Turns light on.
   */
  void turnLightOn();

  /**
   * Turns lights off;
   */
  void turnLightOff();

  /**
   * Checks device connectivity in case of errors.
   * @return true if connected and false if not.
   */
  boolean isConnected();



  /**
   * Any change to device generates an event. To be able to receive updates
   * you must subscribe to the device. Note that notification for events are not
   * guaranteed to be delivered in a timely manner.
   */
  void subscribe(DeviceClient client );

}
