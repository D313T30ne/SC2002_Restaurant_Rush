package restaurantrush.entity.restaurant;

import restaurantrush.entity.customer.Customer;
import java.util.ArrayList;
import java.util.List;

public class WaitingQueue {

    private final List<Customer> customerList = new ArrayList<>();

    //A new customer joins the queue
    public void enqueue(Customer c) {
        if (customerList.contains(c)) {
            throw new IllegalStateException("Customer " + c.getId() + " is already in the queue");
        }
        customerList.add(c);
    }

    //Remove customer from queue
    //Note: We should allow random access removal. 
    //Eg. ImpatiantCustomer loses satisfaction quicker than Customer, leaves queue earlier than normal Customer despite being a newer entry.
    public void remove(Customer c) {
        customerList.remove(c);
    }

    //Returns an immutable snapshot of the queue (shallow copy)
    //Eg. waitingQueue.getCustomers().get(0).applyWaitingLoss() would actually apply the waiting loss to that real customer.
    public List<Customer> getCustomers() {
        return List.copyOf(customerList);
    }

    public boolean isEmpty() {
        return customerList.isEmpty();
    }
}