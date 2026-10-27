package restaurantrush.control;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.util.Money;

/**
 * Checks the three Stage 1 targets after the last turn (brief section 2.1):
 * at least 4 paid customers, revenue at least $45.00,
 * average satisfaction of paid customers at least 60.
 */
public class ObjectiveChecker {

    private final int minPaidCustomers;
    private final Money minRevenue;
    private final int minAvgSatisfaction;

    public ObjectiveChecker() {
        this(4, Money.ofCents(4500), 60);
    }

    public ObjectiveChecker(int minPaidCustomers, Money minRevenue, int minAvgSatisfaction) {
        this.minPaidCustomers = minPaidCustomers;
        this.minRevenue = minRevenue;
        this.minAvgSatisfaction = minAvgSatisfaction;
    }

    public GameResult evaluate(Restaurant restaurant) {
        int paid = countPaidCustomers(restaurant);
        boolean enoughPaid = paid >= minPaidCustomers;

        boolean enoughRevenue = restaurant.getRevenue().compareTo(minRevenue) >= 0;

        // getAverageSatisfaction() returns null when nobody has paid ("N/A").
        Double average = restaurant.getAverageSatisfaction();
        boolean happyEnough = average != null && average >= minAvgSatisfaction;

        if (enoughPaid && enoughRevenue && happyEnough) {
            return GameResult.VICTORY;
        }
        return GameResult.DEFEAT;
    }

    private int countPaidCustomers(Restaurant restaurant) {
        int count = 0;
        for (Customer c : restaurant.getCustomers()) {
            if (c.getState() == CustomerState.PAID) {
                count++;
            }
        }
        return count;
    }
}
