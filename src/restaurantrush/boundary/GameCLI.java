package restaurantrush.boundary;

import java.util.Scanner;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;
import restaurantrush.entity.restaurant.WaitingQueue;
import restaurantrush.entity.staff.ActionResult;
import restaurantrush.entity.staff.WaiterTask;

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
        // Option 1: Seat Customer
        if (!r.getWaitingQueue().isEmpty()) {
            System.out.println("1. Seat a queued customer");
        }
        // Option 2: Take Order
        System.out.println("2. Take one seated customer's order");

        // Option 3: Serve READY order
        System.out.println("3. Serve one READY order");

        // Option 4: Wait
        System.out.println("4. Wait");

        int choice = this.input.readInt("Select a waiter task (1-4): ", 1, 4);

        // Convert the integer choice into the Enum to return to the Controller
        switch (choice) {
            case 1:
                return WaiterTask.SEAT_CUSTOMER;
            case 2:
                return WaiterTask.TAKE_ORDER;
            case 3:
                return WaiterTask.SERVE_ORDER;
            case 4:
                return WaiterTask.WAIT;
        }
        return WaiterTask.WAIT;
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
        }

        int choice = this.input.readInt("Choice (1-" + size + ")", 1, size);
        return wq.getCustomers().get(choice - 1);
    }

    public Table chooseFreeTable(Restaurant r) {
        System.out.println("\n--- Choose Free Table ---");
        // Show free tables only
        System.out.println("Choosing first free table: ");
        return r.findFreeTable();
    }

    public Customer chooseSeatedCustomer(Restaurant r) {

    }

    public Order chooseReadyOrder(Restaurant r) {

    }

    public Order chooseChefOrder(Restaurant r) {

    }

    public void showOrderPlaced(Customer c, Order o) {

    }

    public void showActionResult(ActionResult res) {
        System.out.println(res.getMessage());
    }


}
