package threads;

/**
 * Represents an iot door lock that has a camera and a doorbell.
 */
public interface IDoorLock extends ICamera{

  /**
   * Locks the door.
   */
  void lock();


  /**
   * Unlocks the door.
   */
  void unlock();


  /**
   * Any change to device generates an event. To be able to receive updates
   * you must subscribe to the device. Note that notification for events are not
   * guaranteed to be delivered in a timely manner. It also emits the Chime event.
   */
  void subscribe(DeviceClient client );
}
