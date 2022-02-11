package threads;

/**
 * Data structure that stores event type and message that accompanies the event.
 */
public class Message {
  public Events event;
  public String message;
  public String id;

  public Message(Events event, String message) {
    this.event = event;
    this.message = message;
  }

  public Message(Events event, String message, String id) {
    this.event = event;
    this.message = message;
    this.id = id;
  }

}
