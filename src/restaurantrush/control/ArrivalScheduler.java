package restaurantrush.control;

import java.util.ArrayList;
import java.util.List;

import restaurantrush.entity.customer.Customer;

/**
 * Decides who arrives on which turn (brief section 2.1).
 * Stage 2+: the customer type for each arrival comes from GameConfig's arrival plan
 * (base setting: Regular, VIP, Regular, Critic, Regular, Regular), so the controller
 * never has to know which customer types exist.
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
            arrivals.add(createCustomer(arrivalIndex));
        }
        return arrivals;
    }

    /** Builds the customer type the config's arrival plan lists for this arrival slot. */
    private Customer createCustomer(int arrivalIndex) {
        Customer customer = config.getArrivalPlan().get(arrivalIndex).apply(nextCustomerId);
        nextCustomerId++;
        return customer;
    }
}
