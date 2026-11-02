package restaurantrush.pricing;

import restaurantrush.entity.customer.Customer;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.util.Money;

/**
 * Stage 3. Happy Hour, a pricing policy the manager may switch on.
 *
 *  - Can be activated once per game, at the start of a turn.
 *  - Lasts for that turn and the next (DURATION_TURNS = 2).
 *  - Orders taken while active get 20% off the bill. The wrapped policy runs first, so the
 *    VIP discount is applied first and Happy Hour second: $15 x 0.9 x 0.8 = $10.80.
 *    Each step rounds to the nearest cent.
 *  - While active, customers still waiting for a table or food recover RECOVERY_POINTS at
 *    turn start (the controller applies this through Customer.recoverSatisfaction).
 *
 * It wraps another policy (Decorator over the Strategy interface), so the Waiter keeps one
 * PricingPolicy for the whole game and never needs to know whether Happy Hour exists.
 */
public class HappyHourPolicy implements PricingPolicy {

    public static final int DURATION_TURNS = 2;
    public static final int DISCOUNT_PERCENT = 20;
    public static final int RECOVERY_POINTS = 15;

    private final PricingPolicy base;
    private boolean used;
    private int turnsRemaining;

    public HappyHourPolicy(PricingPolicy base) {
        this.base = base;
        this.used = false;
        this.turnsRemaining = 0;
    }

    public Money priceFor(MenuItem item, Customer c) {
        Money price = base.priceFor(item, c);
        if (isActive()) {
            return price.applyPercentDiscount(DISCOUNT_PERCENT);
        }
        return price;
    }

    /** True until the manager has used Happy Hour (once per game). */
    public boolean isAvailable() {
        return !used;
    }

    /** Switches Happy Hour on for this turn and the next. Returns false if it was already used. */
    public boolean activate() {
        if (used) {
            return false;
        }
        used = true;
        turnsRemaining = DURATION_TURNS;
        return true;
    }

    public boolean isActive() {
        return turnsRemaining > 0;
    }

    /** Turns left, counting the current turn, while active. 0 otherwise. */
    public int getTurnsRemaining() {
        return turnsRemaining;
    }

    /** Call once at the end of every turn. */
    public void endTurn() {
        if (turnsRemaining > 0) {
            turnsRemaining--;
        }
    }
}