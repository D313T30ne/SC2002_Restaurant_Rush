package restaurantrush.entity.staff;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.menu.Menu;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;
import restaurantrush.pricing.PricingPolicy;

public class Waiter extends Staff {
    private PricingPolicy pricing;
 
    public Waiter(String name, PricingPolicy pricing) {
        super(name);
        this.pricing = pricing;
    }
 
    @Override
    public String getRole() {
        return "Waiter";
    }
 
    public ActionResult seatCustomer(Customer c, Table t, Restaurant r) {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }

        if (c == null || t == null) {
            return ActionResult.failure("Invalid customer or table selection.");
        }
    
        if (!t.isFree()) {
            return ActionResult.failure("Table " + t.getId() + " is currently occupied.");
        }
    
        if (c.getState() != CustomerState.WAITING) {
            return ActionResult.failure("Customer " + c.getId() + " is not waiting in queue.");
        }
    
        r.getWaitingQueue().remove(c);
        t.seat(c);
        c.setState(CustomerState.SEATED);
        
        actedThisTurn = true;
        return ActionResult.success("Customer " + c.getId() + " seated at Table " + t.getId() + ".");
    }
 
    public ActionResult takeOrder(Customer c, Restaurant r, Menu menu) {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }

        if (c == null) {
            return ActionResult.failure("Invalid customer.");
        }
    
        if (c.getState() != CustomerState.SEATED || c.getOrder() != null) {
            return ActionResult.failure("Customer " + c.getId() + " is not ready to order.");
        }
    
        MenuItem item = c.chooseItem(menu);
        Money bill = pricing.priceFor(item, c);
    
        Order order = new Order(c, item, bill);
        r.registerOrder(order);
        c.setOrder(order);
        c.setState(CustomerState.ORDERED);
        
        actedThisTurn = true;
        return ActionResult.success("Took order for Customer " + c.getId() + ": " + item.getName() + ".");
    }
 
    public ActionResult serveOrder(Order o, int turn) {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }

        if (o == null) {
            return ActionResult.failure("Invalid order.");
        }
    
        if (!o.isReady()) {
            return ActionResult.failure("Order is not ready to be served yet.");
        }
    
        o.markServed();
        o.getCustomer().markServed(turn);
        
        actedThisTurn = true;
        return ActionResult.success("Served order " + o.getId() + " to Customer " + o.getCustomer().getId() + ".");
    }
 
    public ActionResult waitTurn() {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }
        actedThisTurn = true;
        return ActionResult.success("Waiter waits.");
    }
}
