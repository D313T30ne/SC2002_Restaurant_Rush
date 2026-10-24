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
        // TODO guard: t.isFree()
        // TODO guard: c is WAITING (in the queue)
        // TODO: r.getWaitingQueue().remove(c); t.seat(c); c.setState(CustomerState.SEATED);
        actedThisTurn = true;
        return ActionResult.success("Customer seated.");
    }
 
    public ActionResult takeOrder(Customer c, Restaurant r, Menu menu) {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }
        // TODO guard: c is SEATED and has no order yet
        // TODO: MenuItem item = c.chooseItem(menu);
        // TODO: Money bill = pricing.priceFor(item, c);   (price locked here)
        // TODO: create Order, r.registerOrder(order); c.setOrder(order); c.setState(CustomerState.ORDERED);
        actedThisTurn = true;
        return ActionResult.success("Order taken.");   // TODO: include the food choice in the message
    }
 
    public ActionResult serveOrder(Order o, int turn) {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }
        // TODO guard: o.isReady()  (Waiter acts before Chef, so READY means finished on an earlier turn)
        // TODO: o.markServed(); o.getCustomer().markServed(turn);
        actedThisTurn = true;
        return ActionResult.success("Order served.");
    }
 
    public ActionResult waitTurn() {
        if (actedThisTurn) {
            return ActionResult.failure("Waiter has already acted this turn.");
        }
        actedThisTurn = true;
        return ActionResult.success("Waiter waits.");
    }
}
