package restaurantrush.entity.staff;

public enum WaiterTask {
  SEAT_CUSTOMER("Seat one queued customer at a free table"),
  TAKE_ORDER("Take one seated customer's order"),
  SERVE_ORDER("Serve one READY order"),
  WAIT("Do nothing this turn");
 
  private final String description;
 
  WaiterTask(String description) {
    this.description = description;
  }
 
  public String getDescription() {
    return description;
  }
}
