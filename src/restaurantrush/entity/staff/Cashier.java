package restaurantrush.entity.staff;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.restaurant.Restaurant;

public class Cashier extends Staff {
    public Cashier(String name) {
        super(name);
    }
 
    @Override
    public String getRole() {
        return "Cashier";
    }
 
    /**
     * Returns the customer who paid this turn, or null if nobody could pay.
     * Only customers who became READY_TO_PAY on an EARLIER turn can pay
     * (c.getReadyToPayTurn() < turn).
     */
    public Customer collectPayment(Restaurant r, int turn) {
        if (actedThisTurn) {
            return null;
        }
        // TODO: loop r.getCustomers(), find state READY_TO_PAY with readyToPayTurn < turn,
        //       pick the one with the smallest readyToPayTurn (oldest)
        // TODO: r.recordPayment(c);  (adds bill to revenue, records satisfaction, marks PAID, frees table)
        // TODO: actedThisTurn = true; return c;
        return null;
    }
}
