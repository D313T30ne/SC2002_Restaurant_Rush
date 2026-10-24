package restaurantrush.entity.restaurant;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.staff.Cashier;
import restaurantrush.entity.staff.Chef;
import restaurantrush.entity.staff.Waiter;
import restaurantrush.util.Money;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {

    private final List<Table> tableList = new ArrayList<>();
    private final WaitingQueue waitingQueue = new WaitingQueue();
    private final List<Customer> customerList = new ArrayList<>();
    private final List<Order> orderList = new ArrayList<>();

    private final Waiter waiter;
    private final Chef chef;
    private final Cashier cashier;

    private Money revenue = Money.ofCents(0); //Set to $0.00
    private int paidCount;
    private int paidSatisfactionTotal;
    private int servedCount;
    private int unhappyDepartures;

    public Restaurant(int tableCount, Waiter waiter, Chef chef, Cashier cashier) {
        for (int i = 1; i <= tableCount; i++) {
            tableList.add(new Table(i));
        }
        this.waiter = waiter;
        this.chef = chef;
        this.cashier = cashier;
    }

    //First free table, or null if every table is occupied.
    public Table findFreeTable() {
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).isFree()) {
                return tableList.get(i);
            }
        }
        return null;
    }

    //New customer arrives, added to customerList and waitingQueue
    public void addArrival(Customer c) {
        customerList.add(c);
        waitingQueue.enqueue(c);
    }

    public void registerOrder(Order o) {
        orderList.add(o);
    }

    //The Waiter served an order
    public void recordServed() {
        servedCount++;
    }

    //Cashier collected payment. Payment adds the bill to revenue, marks the customer and order PAID, and frees the table.
    public void recordPayment(Customer c) {
        Order o = c.getOrder();
        o.markPaid();
        revenue = revenue.add(o.getBill());
        paidCount++;
        paidSatisfactionTotal += c.getSatisfaction();
        c.setState(CustomerState.PAID); //TODO: We can create a markPaid() method in customer to encapsulate the state transition
        freeTableOf(c);
    }

    //Customer left unhappy. Cancel the order and frees the table.
    public void recordDeparture(Customer c) {
        waitingQueue.remove(c); //If not queued, nothing happens here
        Order o = c.getOrder();
        if (o != null) {
            o.cancel();
        }
        c.setState(CustomerState.LEFT); //TODO: We can create a markLeft() method in customer to encapsulate the state transition
        freeTableOf(c);
        unhappyDepartures++;
    }

    // Average satisfaction of paid customers, or null if nobody has paid.
    public Double getAverageSatisfaction() {
        if (paidCount == 0) {
            return null; //So we don't divide by 0
        }
        return (double) paidSatisfactionTotal / paidCount;
    }

    //Iterates through tableList to find matching seated customer and frees that table
    private void freeTableOf(Customer c) {
        for (int i = 0; i < tableList.size(); i++) {
            if (tableList.get(i).getOccupant() == c) {
                tableList.get(i).free();
                break;
            }
        }
    }

    public Money getRevenue() {
        return revenue;
    }

    public int getPaidCount() {
        return paidCount;
    }

    public int getServedCount() {
        return servedCount;
    }

    public int getUnhappyDepartures() {
        return unhappyDepartures;
    }

    public List<Table> getTables() {
        return List.copyOf(tableList);
    }

    public WaitingQueue getWaitingQueue() {
        return waitingQueue; //Returning the real queue here so that the waiter can actually dequeue
    }

    public List<Order> getOrders() {
        return List.copyOf(orderList);
    }

    public List<Customer> getCustomers() {
        return List.copyOf(customerList);
    }

    public Waiter getWaiter() {
        return waiter;
    }

    public Chef getChef() {
        return chef;
    }

    public Cashier getCashier() {
        return cashier;
    }
}