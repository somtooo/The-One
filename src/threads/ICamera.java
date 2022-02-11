package threads;

import java.util.List;

/**
 * Represents an iot camera
 */
public interface ICamera {

  /**
   * Generates an image from an event. See events to see supported camera events. if id not presents generates image
   * showing current state of camera surroundings.
   * @param eventId event id to get image from.
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
   * Checks device connectivity in case of errors.
   * @return true if connected and false if not.
   */
  boolean isConnected();

  /*
    Activates the tracking functionality of the app.
   */
  public void setPersonTracking(boolean shouldTrack);


  /**
   * This device is able to detect persons. Client must be subscribed to receive Person events.
   */
  void subscribe(DeviceClient client );

}
