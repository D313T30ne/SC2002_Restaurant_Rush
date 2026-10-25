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
 
    /*
    * Returns the customer who paid this turn, or null if nobody could pay.
    * Only customers who became READY_TO_PAY on an EARLIER turn can pay
    * (c.getReadyToPayTurn() < turn).
    */
    public Customer collectPayment(Restaurant r, int turn) {
        if (actedThisTurn) {
            return null;
        }

        Customer oldestCustomer = null;

        // Find the customer who has been waiting to pay the longest
        for (Customer c : r.getCustomers()) {
            if (c.getState() == CustomerState.READY_TO_PAY && c.getReadyToPayTurn() < turn) {
                if (oldestCustomer == null || c.getReadyToPayTurn() < oldestCustomer.getReadyToPayTurn()) {
                    oldestCustomer = c;
                }
            }
        }
    
        // If no eligible customer was found, return null
        if (oldestCustomer == null) {
            return null;
        }
    
        // Process payment, mark staff as acted, and return customer
        r.recordPayment(oldestCustomer);
        actedThisTurn = true;
        return oldestCustomer;
    }
}
