package restaurantrush.entity.customer;

import java.util.List;

import restaurantrush.entity.menu.Menu;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.order.Order;
import restaurantrush.util.Money;

/**
 * Abstract base class for every customer in the restaurant.
 *
 * Encapsulation: satisfaction and state are private. They only change through
 * the behaviour methods below (applyWaitingLoss, markServed, updateEating, ...).
 *
 * Polymorphism: subclasses decide how much satisfaction is lost per waiting turn
 * (getWaitingLoss) and may override calculateBill. Stage 1 only has
 * RegularCustomer, but later customer types plug in without changing this class's callers.
 */
public abstract class Customer {

    public static final int INITIAL_SATISFACTION = 100;

    private final int id;
    private int satisfaction;
    private CustomerState state;
    private Order order;

    /** Turn on which the customer was served; -1 until then. */
    private int servedTurn;
    /** Turn on which the customer became READY_TO_PAY; -1 until then. */
    private int readyToPayTurn;

    protected Customer(int id) {
        this.id = id;
        this.satisfaction = INITIAL_SATISFACTION;
        this.state = CustomerState.WAITING;
        this.order = null;
        this.servedTurn = -1;
        this.readyToPayTurn = -1;
    }

    // ------------------------------------------------------------------
    // Behaviour
    // ------------------------------------------------------------------

    /**
     * Picks the dish this customer orders. The manager never chooses food.
     *
     * Rule (deterministic, so runs are reproducible): customer N orders the
     * ((N - 1) mod menuSize)-th item on the menu. With the base menu
     * (Salad, Burger, Pasta) customers 1..6 order Salad, Burger, Pasta, Salad, Burger, Pasta.
     */
    public MenuItem chooseItem(Menu menu) {
        List<MenuItem> items = menu.getItems();
        if (items.isEmpty()) {
            throw new IllegalStateException("Menu has no items to choose from.");
        }
        return items.get((id - 1) % items.size());
    }

    /**
     * Applies one turn of waiting loss. Only customers who are still waiting for
     * their food (WAITING, SEATED or ORDERED) lose satisfaction. Never drops below 0.
     */
    public void applyWaitingLoss() {
        if (isAwaitingService()) {
            satisfaction = Math.max(0, satisfaction - getWaitingLoss());
        }
    }

    /** True while the customer has not yet been served. */
    public boolean isAwaitingService() {
        return state == CustomerState.WAITING
                || state == CustomerState.SEATED
                || state == CustomerState.ORDERED;
    }

    /** A customer abandons the restaurant when satisfaction hits 0 before being served. */
    public boolean hasAbandoned() {
        return isAwaitingService() && satisfaction <= 0;
    }

    /** Called when the Waiter serves this customer's order. Eating starts now. */
    public void markServed(int turn) {
        if (state != CustomerState.ORDERED) {
            throw new IllegalStateException(
                    "Customer " + id + " cannot be served from state " + state);
        }
        this.state = CustomerState.EATING;
        this.servedTurn = turn;
    }

    /**
     * Called once per turn in the eating/payment step. A served customer finishes
     * eating one full turn later and becomes READY_TO_PAY, so they can pay at the
     * earliest on the following turn.
     */
    public void updateEating(int currentTurn) {
        if (state == CustomerState.EATING && currentTurn > servedTurn) {
            this.state = CustomerState.READY_TO_PAY;
            this.readyToPayTurn = currentTurn;
        }
    }

    /**
     * Amount this customer is charged for a dish with the given base price.
     * Stage 1: always the full price. Subclasses may override.
     */
    public Money calculateBill(Money basePrice) {
        return basePrice;
    }

    /** Satisfaction lost per waiting turn. Decided by each customer type. */
    protected abstract int getWaitingLoss();

    // ------------------------------------------------------------------
    // Accessors
    // ------------------------------------------------------------------

    public int getId() {
        return id;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public CustomerState getState() {
        return state;
    }

    public void setState(CustomerState state) {
        this.state = state;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getReadyToPayTurn() {
        return readyToPayTurn;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " #" + id
                + " [" + state + ", satisfaction " + satisfaction + "]";
    }
}