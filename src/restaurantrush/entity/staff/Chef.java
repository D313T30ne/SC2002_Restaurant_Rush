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

        if (o == null) {
            return ActionResult.failure("Invalid order.");
        }
    
        if (o.getStatus() != OrderStatus.PLACED && o.getStatus() != OrderStatus.PREPARING) {
            return ActionResult.failure("Order cannot be prepared (status: " + o.getStatus() + ").");
        }

        o.advancePreparation();
        
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
