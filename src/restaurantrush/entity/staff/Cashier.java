package restaurantrush.entity.staff;

import java.util.List;
 
import restaurantrush.entity.order.Order;

public class Cashier extends Staff {
    public Cashier(String name) {
        super(name);
    }
 
    @Override
    public String getRole() {
        return "Cashier";
    }
 
    /**
     * Pick the oldest order that is READY_TO_PAY (and became so on an EARLIER turn),
     * process the payment, and return it. Return null if nobody can pay.
     *
     * @param orders      all active orders, in the order they became READY_TO_PAY
     * @param currentTurn used to skip anyone who became READY_TO_PAY this turn
     */
    public Order processPayment(List<Order> orders, int currentTurn) {
        if (hasActedThisTurn()) {
            return null;
        }
        // TODO: find oldest order with status READY_TO_PAY and readyToPayTurn < currentTurn
        // TODO: customer.recordSatisfaction(); mark order + customer PAID; free the table
        // TODO: restaurant revenue += bill (controller or Restaurant does this with the returned order)
        // TODO: markActed() once a payment is made
        return null;
    }
}
