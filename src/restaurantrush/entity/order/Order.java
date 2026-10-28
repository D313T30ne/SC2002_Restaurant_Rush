package restaurantrush.entity.order;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.util.Money;

public class Order {

    private final int id;
    private int unitsRemaining; //No. of prep units still required
    private OrderStatus status;

    private final Customer customer;
    private final MenuItem item;
    private final Money bill;

    public Order(int id, Customer customer, MenuItem item, Money bill) {
        this.id = id;
        this.customer = customer;
        this.item = item;
        this.bill = bill;
        this.unitsRemaining = item.getPrepUnits();
        this.status = OrderStatus.PLACED;
    }
    //Note: Illegal actions should already be prevented before it is checked here. 
    //We only check illegal states here in case there is a bug that allows illegal actions to pass through our first layer of checking.

    //Chef does one preparation unit. READY when the last unit is done.
    public void advancePreparation() {
        if (status != OrderStatus.PLACED && status != OrderStatus.PREPARING) {
            throw new IllegalStateException("Cannot prepare an order that is " + status);
        }

        //Decrement the number of units still needed
        unitsRemaining--;

        //When unitsRemaining == 0, the order is now READY to be SERVED
        if (unitsRemaining <= 0){
            status = OrderStatus.READY;
        }
        else{
            status = OrderStatus.PREPARING;
        }
    }

    //Waiter delivers the dish. Only allowed from READY.
    public void markServed() {
        if (status != OrderStatus.READY) {
            throw new IllegalStateException("Cannot serve an order that is " + status);
        }
        status = OrderStatus.SERVED;
    }

    //Cashier collects payment. Only allowed from SERVED.
    public void markPaid() {
        if (status != OrderStatus.SERVED) {
            throw new IllegalStateException("Cannot pay an order that is " + status);
        }
        status = OrderStatus.PAID;
    }

    //Customer left before eating. Allowed before the dish is served.
    public void cancel() {
        //A customer can only cancel an order if they leave before eating. 
        //Currently the customer cannot leave when their order is already served to them.
        if (status == OrderStatus.SERVED || status == OrderStatus.PAID || status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cannot cancel an order that is " + status);
        }
        status = OrderStatus.CANCELLED;
    }

    //TODO: Since we have isReady(), should we also have isCookable()? We can force all status checks into each entity class to encapsulate them.
    public boolean isReady() {
        return status == OrderStatus.READY;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public MenuItem getItem() {
        return item;
    }

    public Money getBill() {
        return bill;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public int getUnitsRemaining() {
        return unitsRemaining;
    }
}