package restaurantrush.entity.staff;

import restaurantrush.entity.order.Order;

public class Chef extends Staff {
    public Chef(String name) {
        super(name);
    }
 
    @Override
    public String getRole() {
        return "Chef";
    }
 
    /** Advance the chosen order by one unit. When the last unit is done, order becomes READY. */
    public void prepare(Order order) {
        if (hasActedThisTurn()) {
            throw new IllegalStateException("Chef has already acted this turn.");
        }
        // TODO guard: order must have been taken and not yet READY / SERVED / cancelled
        // TODO: order.advancePreparation();  -> Order sets READY when remaining units hit 0
        markActed();
    }
 
    /** Manager chose WAIT. */
    public void doNothing() {
        if (hasActedThisTurn()) {
            throw new IllegalStateException("Chef has already acted this turn.");
        }
        markActed();
    }
}
