package restaurantrush.boundary;

import restaurantrush.control.GameResult;
import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.order.OrderStatus;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;
import restaurantrush.entity.restaurant.WaitingQueue;
import restaurantrush.entity.staff.ActionResult;
import restaurantrush.entity.staff.WaiterTask;

import java.util.ArrayList;
import java.util.List;

// Various UI methods that GameController can call.
// Separation of concerns. GameCLI the boundary. GameController the controller
// This class is a view layer. It prints whatever GameController tells it to print.
public class GameCLI {
    private InputReader input;
    private StateDisplay display;

    public GameCLI() {
        System.out.println("Welcome to Restaurant Rush");
        this.input = new InputReader();
        this.display = new StateDisplay();
    }

    public void showStartOfTurn(int turn, Restaurant r) {
        display.renderStartOfTurn(turn, r);
    }

    public void showEndOfTurn(int turn, Restaurant r) {
        display.renderEndOfTurn(turn, r);
    }

    public void showFinalResult(GameResult res, Restaurant r) {
        display.renderFinal(res, r);
    }

    public WaiterTask chooseWaiterTask(Restaurant r) {
        System.out.println("\n--- Assign Waiter Task ---");
        List<WaiterTask> options = new ArrayList<>();
        // Option 1: Seat Customer
        if (!r.getWaitingQueue().isEmpty()) {
            options.add(WaiterTask.SEAT_CUSTOMER);
            System.out.println("1. Seat a queued customer");
        }
        // Option 2: Take Order
        boolean hasSeated = false;                                      // added
        for (Customer c : r.getCustomers()) {
            if (c.getState() == CustomerState.SEATED) {
                hasSeated = true;
                break;
            }
        }
        if (hasSeated) {                                                // added
            options.add(WaiterTask.TAKE_ORDER);
            System.out.println(options.size() + ". Take one seated customer's order");
        }

        // Option 3: Serve READY order
        boolean hasReady = false;                                       // added
        for (Order o : r.getOrders()) {
            if (o.isReady()) {
                hasReady = true;
                break;
            }
        }
        if (hasReady) {                                                 // added
            options.add(WaiterTask.SERVE_ORDER);
            System.out.println(options.size() + ". Serve one READY order");
        }

        // Option 4: Wait
        options.add(WaiterTask.WAIT);
        System.out.println(options.size() + ". Wait");

        int choice = this.input.readInt("Select a waiter task (1-" + options.size() + "): ", 1, options.size());
        return options.get(choice - 1);
    }

    public Customer chooseCustomerToSeat(Restaurant r) {
        System.out.println("\n--- Choose Customer to Seat ---");
        // Show the customers in the waiting queue
        System.out.println("Enter the number to choose the customer: ");
        int counter = 1;
        WaitingQueue wq = r.getWaitingQueue();
        int size = wq.getCustomers().size();
        for (Customer c : wq.getCustomers()) {
            System.out.println(counter + ". " + "id: " + c.getId() + " Satisfaction: " + c.getSatisfaction());
            counter++;
        }

        int choice = this.input.readInt("Choice (1-" + size + ")", 1, size);
        return wq.getCustomers().get(choice - 1);
    }

    public Table chooseFreeTable(Restaurant r) {
        System.out.println("\n--- Choosing first Free Table ---");
        Table table = r.findFreeTable();
        System.out.println("Table Number " + table.getNumber() + " chosen");
        return table;
    }

    public Customer chooseSeatedCustomer(Restaurant r) {
        System.out.println("--- Take order from ---");
        List<Customer> seated = new ArrayList<>();                  // added
        for (Customer c : r.getCustomers()) {
            if (c.getState() == CustomerState.SEATED) {             // added filter
                seated.add(c);
            }
        }
        if (seated.isEmpty()) {                                     // added
            System.out.println("No seated customers to take an order from.");
            return null;
        }

        int counter = 1;
        for (Customer c : seated) {
            System.out.println(counter + ". " + "id: " + c.getId() + " Satisfaction: " + c.getSatisfaction());
            counter++;                                              // added, was missing
        }

        int choice = this.input.readInt("Choice (1-" + seated.size() + ")", 1, seated.size());
        return seated.get(choice - 1);
    }

    public Order chooseReadyOrder(Restaurant r) {
        System.out.println("--- Choose Order to Serve ---");
        List<Order> ready = new ArrayList<>();                      // added
        for (Order order : r.getOrders()) {
            if (order.isReady()) {                                  // added filter
                ready.add(order);
            }
        }
        if (ready.isEmpty()) {                                      // added
            System.out.println("No READY orders to serve.");
            return null;
        }

        int counter = 1;
        for (Order order : ready) {
            System.out.println(counter + ". Order ID: " + order.getId() + ", Status: " + order.getStatus());
            counter++;                                              // added, was missing
        }

        int choice = this.input.readInt("Choice (1-" + ready.size() + ")", 1, ready.size());
        return ready.get(choice - 1);
    }

    public Order chooseChefOrder(Restaurant r) {
        System.out.println("\n--- Assign Chef Task ---");
        List<Order> placed = new ArrayList<>();                     // added
        for (Order order : r.getOrders()) {
            if (order.getStatus() == OrderStatus.PLACED
                    || order.getStatus() == OrderStatus.PREPARING) {         // added filter
                placed.add(order);
            }
        }
        int counter = 1;
        for (Order order : placed) {
            System.out.println(counter + ". Order ID: " + order.getId() + ", Status: " + order.getStatus());
            counter++;
        }
        System.out.println(counter + ". Wait");

        int choice = this.input.readInt("Choice (1-" + counter + ")", 1, counter);
        return (choice == counter) ? null : placed.get(choice - 1);
    }

    public void showOrderPlaced(Customer c, Order o) {
        System.out.println("\n--- Order Placed ---");
        System.out.println("Customer ID: " + c.getId() + ", Order: " + c.getOrder().getItem().getName());
    }

    public void showActionResult(ActionResult res) {
        System.out.println(res.getMessage());
    }
}
