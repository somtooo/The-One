package threads;

/**
 * Represents thermostat that can heat, cool, reports on the Celsius scale
 * and has a fan.
 */
public interface IThermostat {

  /**
   * Changes the behaviour of the thermostat.
   * @param mode the specified behaviour. It ranges from Heat,Cool and OFF
   //@return true if behaviour was changed or false if there was an error.
   */
  void setMode(Mode mode);

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
   * Sets the target temperature defaults to 25deg on device setup
   * @param degrees the degree in Celsius to set the cool mode temp to.
   */
  void targetTemperature(double degrees);

  /**
   * Runs the thermostat fan for the specified duration. Note that command
   * may not produce desired result because the fan can be engaged either
   * manually or by the HVAC cycle.
   * @param duration time in seconds to run fan.
   */
  void turnFan(int duration);

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
