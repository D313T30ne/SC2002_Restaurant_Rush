package restaurantrush.control;

import java.util.ArrayList;
import java.util.List;

import restaurantrush.boundary.GameCLI;
import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.customer.CustomerState;
import restaurantrush.entity.menu.Menu;
import restaurantrush.entity.order.Order;
import restaurantrush.entity.restaurant.Restaurant;
import restaurantrush.entity.restaurant.Table;
import restaurantrush.entity.staff.ActionResult;
import restaurantrush.entity.staff.Cashier;
import restaurantrush.entity.staff.Chef;
import restaurantrush.entity.staff.Waiter;
import restaurantrush.entity.staff.WaiterTask;
import restaurantrush.pricing.StandardPricing;

/**
 * Runs the game: always all turns from the config, each turn in the six
 * steps of brief section 3.2, then the Victory/Defeat check.
 *
 * The controller only decides the ORDER things happen in. The rules
 * themselves live in the entity classes (Waiter, Chef, Customer, ...),
 * and the CLI only asks questions and prints. That keeps rules out of the UI.
 */
public class GameController {

    private int currentTurn;
    private final Restaurant restaurant;
    private final Menu menu;
    private final GameConfig config;
    private final ArrivalScheduler scheduler;
    private final ObjectiveChecker checker;
    private final GameCLI ui;

    public GameController(GameConfig config, GameCLI ui) {
        this.config = config;
        this.ui = ui;
        this.restaurant = new Restaurant(
                config.getTableCount(),
                new Waiter("Waiter", new StandardPricing()),
                new Chef("Chef"),
                new Cashier("Cashier"));
        this.menu = new Menu();
        this.scheduler = new ArrivalScheduler(config);
        this.checker = new ObjectiveChecker();
        this.currentTurn = 0;
    }

    /** Plays the whole game. There is no early ending (brief 3.2 step 6). */
    public void run() {
        for (currentTurn = 1; currentTurn <= config.getTotalTurns(); currentTurn++) {
            runTurn();
        }
        GameResult result = checker.evaluate(restaurant);
        ui.showFinalResult(result, restaurant);
    }

    private void runTurn() {
        restaurant.getWaiter().startTurn();
        restaurant.getChef().startTurn();
        restaurant.getCashier().startTurn();

        processArrivals();                          // step 1
        ui.showStartOfTurn(currentTurn, restaurant); // shown after arrivals so the new customer is visible
        runWaiterPhase();                           // steps 2 + 3: Waiter acts before Chef
        runChefPhase();
        processEatingAndPayment();                  // step 4
        applyWaitingLoss();                         // step 5
        ui.showEndOfTurn(currentTurn, restaurant);
    }

    // ---------- Step 1: arrivals ----------

    private void processArrivals() {
        for (Customer c : scheduler.customersArrivingOn(currentTurn)) {
            restaurant.addArrival(c); // joins the waiting queue with satisfaction 100
        }
    }

    // ---------- Steps 2 and 3: Waiter ----------

    /**
     * Keeps asking until the Waiter either does one valid task or waits.
     * A failed action does not use up the task: we just ask again.
     */
    private void runWaiterPhase() {
        Waiter waiter = restaurant.getWaiter();
        while (!waiter.hasActed()) {
            WaiterTask task = ui.chooseWaiterTask(restaurant);
            ActionResult result = doWaiterTask(waiter, task);
            ui.showActionResult(result);
        }
    }

    private ActionResult doWaiterTask(Waiter waiter, WaiterTask task) {
        switch (task) {
            case SEAT_CUSTOMER: {
                if (restaurant.findFreeTable() == null) {                   // added
                    return ActionResult.failure("No free table available.");
                }
                Customer customer = ui.chooseCustomerToSeat(restaurant);
                Table table = ui.chooseFreeTable(restaurant);
                return waiter.seatCustomer(customer, table, restaurant);
            }
            case TAKE_ORDER: {
                Customer customer = ui.chooseSeatedCustomer(restaurant);
                ActionResult result = waiter.takeOrder(customer, restaurant, menu);
                if (result.isSuccess()) {
                    ui.showOrderPlaced(customer, customer.getOrder()); // show the customer's own choice
                }
                return result;
            }
            case SERVE_ORDER: {
                Order order = ui.chooseReadyOrder(restaurant);
                ActionResult result = waiter.serveOrder(order, currentTurn);
                if (result.isSuccess()) {
                    restaurant.recordServed();          // added
                }
                return result;
            }
            case WAIT:
            default:
                return waiter.waitTurn();
        }
    }

    // ---------- Steps 2 and 3: Chef ----------

    /** chooseChefOrder returns null when the manager picks Wait. */
    private void runChefPhase() {
        Chef chef = restaurant.getChef();
        while (!chef.hasActed()) {
            Order order = ui.chooseChefOrder(restaurant);
            ActionResult result = (order == null) ? chef.waitTurn() : chef.advanceOrder(order);
            ui.showActionResult(result);
        }
    }

    // ---------- Step 4: eating and payment ----------

    /**
     * The Cashier pays first, THEN customers who finished eating become READY_TO_PAY.
     * Doing it in this order guarantees that someone who becomes READY_TO_PAY
     * this turn can only pay next turn (brief 3.2 step 4).
     */
    private void processEatingAndPayment() {
        // Returns the customer who paid, or null. The Cashier records the payment
        // in Restaurant (revenue, PAID, table freed); the end-of-turn display shows it.
        restaurant.getCashier().collectPayment(restaurant, currentTurn);

        for (Customer c : restaurant.getCustomers()) {
            if (c.getState() == CustomerState.EATING) {
                c.updateEating(currentTurn);
            }
        }
    }

    // ---------- Step 5: waiting and abandonment ----------

    /** Only customers not yet served (WAITING, SEATED, ORDERED) lose satisfaction. */
    private void applyWaitingLoss() {
        // Copy the list: recordDeparture may remove customers while we loop.
        List<Customer> customers = new ArrayList<>(restaurant.getCustomers());
        for (Customer c : customers) {
            if (c.isAwaitingService()) {
                c.applyWaitingLoss();
                if (c.hasAbandoned()) {
                    restaurant.recordDeparture(c); // cancels unfinished order, frees table
                }
            }
        }
    }

    public int getCurrentTurn() {
        return currentTurn;
    }
}
