package restaurantrush.entity.staff;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.restaurant.Restaurant;

public class Cashier extends Staff {
    public Cashier(String name) {
        super(name);
    }
 
    @Override
    public String getRole() {
        return "Cashier";
    }
 
    public Customer collectPayment(Restaurant r, int turn) {
        if (actedThisTurn) {
            return null;
        }

        Customer oldestCustomer = null;

        for (Customer c : r.getCustomers()) {
            if (c.getState() == CustomerState.READY_TO_PAY && c.getReadyToPayTurn() < turn) {
                if (oldestCustomer == null || c.getReadyToPayTurn() < oldestCustomer.getReadyToPayTurn()) {
                    oldestCustomer = c;
                }
            }
        }
    
        if (oldestCustomer == null) {
            return null;
        }
    
        r.recordPayment(oldestCustomer);
        actedThisTurn = true;
        return oldestCustomer;
    }
}
