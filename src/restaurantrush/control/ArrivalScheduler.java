package restaurantrush.control;

import java.util.ArrayList;
import java.util.List;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.RegularCustomer;

/**
 * Decides who arrives on which turn (brief section 2.1).
 * Stage 1: one RegularCustomer on each arrival turn in the config.
 * Stage 2 only needs to change createCustomer() to mix in VIP / Critic,
 * so the controller never has to know which customer types exist.
 */
public class ArrivalScheduler {

    private int nextCustomerId;
    private final GameConfig config;

    public ArrivalScheduler(GameConfig config) {
        this.config = config;
        this.nextCustomerId = 1;
    }

    /** Returns the customers who arrive at the start of this turn (empty list if none). */
    public List<Customer> customersArrivingOn(int turn) {
        List<Customer> arrivals = new ArrayList<>();
        int arrivalIndex = config.getArrivalTurns().indexOf(turn);
        if (arrivalIndex >= 0) {
            arrivals.add(createCustomer());
        }
        return arrivals;
    }

    private Customer createCustomer() {
        Customer customer = new RegularCustomer(nextCustomerId);
        nextCustomerId++;
        return customer;
    }
}
