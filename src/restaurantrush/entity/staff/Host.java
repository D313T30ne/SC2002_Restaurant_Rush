package restaurantrush.entity.staff;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;

/**
 * Stage 3. The Host seats at most one queued customer per turn, in addition to the Waiter's
 * task. The Waiter can still seat customers.
 *
 * The Host acts before the Waiter, so a customer the Host seats can have their order taken
 * by the Waiter in the same turn.
 */
public class Host extends Staff {

    public Host(String name) {
        super(name);
    }

    @Override
    public String getRole() {
        return "Host";
    }

    public ActionResult seatCustomer(Customer c, Table t, Restaurant r) {
        if (actedThisTurn) {
            return ActionResult.failure("Host has already acted this turn.");
        }

        if (c == null || t == null) {
            return ActionResult.failure("Invalid customer or table selection.");
        }

        if (!t.isFree()) {
            return ActionResult.failure("Table " + t.getNumber() + " is currently occupied.");
        }

        if (c.getState() != CustomerState.WAITING) {
            return ActionResult.failure("Customer " + c.getId() + " is not waiting in queue.");
        }

        r.getWaitingQueue().remove(c);
        t.seat(c);
        c.setState(CustomerState.SEATED);

        actedThisTurn = true;
        return ActionResult.success("Host: Customer " + c.getId() + " seated at Table " + t.getNumber() + ".");
    }

    public ActionResult waitTurn() {
        if (actedThisTurn) {
            return ActionResult.failure("Host has already acted this turn.");
        }
        actedThisTurn = true;
        return ActionResult.success("Host waits.");
    }
}