package restaurantrush.entity.staff;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.restaurant.Table;

public class Waiter extends Staff {
  public Waiter(String name) {
        super(name);
    }
 
    @Override
    public String getRole() {
        return "Waiter";
    }
 
    /** Seat a queued customer at a free table. */
    public void seatCustomer(Customer customer, Table table) {
        checkCanAct();
        // TODO guard: table must be free
        // TODO guard: customer must be in the waiting queue
        // TODO: table.assign(customer); customer.setState(SEATED); remove from queue (controller or here?)
        markActed();
    }
 
    /** Take the order of a seated customer. */
    public Order takeOrder(Customer customer) {
        checkCanAct();
        // TODO guard: customer must be SEATED and have no order yet
        // TODO: customer picks food (Member 1's rule), create Order (Member 3), customer.setState(ORDERED)
        // TODO: return the new Order so the CLI can show the customer's choice
        markActed();
        return null;
    }
 
    /** Serve an order that was ALREADY READY when the Waiter acts. */
    public void serveOrder(Order order) {
        checkCanAct();
        // TODO guard: order.getStatus() must be READY (not PREPARING / not finished this turn)
        // TODO: order.setStatus(SERVED); customer starts eating (state EATING)
        markActed();
    }
 
    /** Manager chose WAIT. Uses up the turn without doing anything. */
    public void doNothing() {
        checkCanAct();
        markActed();
    }
 
    private void checkCanAct() {
        if (hasActedThisTurn()) {
            throw new IllegalStateException("Waiter has already acted this turn.");
        }
    }
}
