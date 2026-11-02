package restaurantrush.entity.customer;

import java.util.List;

import restaurantrush.entity.menu.Menu;
import restaurantrush.entity.menu.MenuItem;

/**
 * A Critic: impatient (loses 12 satisfaction per waiting turn) and pays full price.
 *
 * Special behaviour (documented order preference): a Critic always orders the most
 * expensive item on the menu, to test the kitchen. With the base menu that is Pasta
 * (2 preparation units), so the manager has to plan the Chef's time around them.
 * If two items share the highest price, the one listed first on the menu is chosen.
 */
public class CriticCustomer extends Customer {

    private static final int WAITING_LOSS = 12;

    public CriticCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitingLoss() {
        return WAITING_LOSS;
    }

    @Override
    public MenuItem chooseItem(Menu menu) {
        List<MenuItem> items = menu.getItems();
        if (items.isEmpty()) {
            throw new IllegalStateException("Menu has no items to choose from.");
        }
        MenuItem best = items.get(0);
        for (MenuItem item : items) {
            if (item.getPrice().compareTo(best.getPrice()) > 0) {
                best = item;
            }
        }
        return best;
    }
}