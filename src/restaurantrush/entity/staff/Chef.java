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
 
    public ActionResult advanceOrder(Order o) {
        if (actedThisTurn) {
            return ActionResult.failure("Chef has already acted this turn.");
        }
        // TODO guard: o is not cancelled, not already READY / SERVED / PAID
        // TODO: o.advancePreparation();  (Order becomes READY when units hit 0)
        actedThisTurn = true;
        return ActionResult.success("Order advanced.");
    }
 
    public ActionResult waitTurn() {
        if (actedThisTurn) {
            return ActionResult.failure("Chef has already acted this turn.");
        }
        actedThisTurn = true;
        return ActionResult.success("Chef waits.");
    }
}
