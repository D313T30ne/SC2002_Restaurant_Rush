package restaurantrush.entity.menu;

import restaurantrush.util.Money;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items;
    /** Stage 3: combos built from two of the items above. Kept separate so single-item rules are unchanged. */
    private List<ComboMeal> combos;

    public Menu() {
        this.items = new ArrayList<>();
        MenuItem salad = new MenuItem("Salad", Money.ofCents(900), 1);
        MenuItem burger = new MenuItem("Burger", Money.ofCents(1500), 1);
        MenuItem pasta = new MenuItem("Pasta", Money.ofCents(1800), 2);
        items.add(salad);
        items.add(burger);
        items.add(pasta);

        this.combos = new ArrayList<>();
        combos.add(new ComboMeal(salad, pasta));   // ($9 + $18) x 0.9 = $24.30, 3 units
        combos.add(new ComboMeal(salad, burger));  // ($9 + $15) x 0.9 = $21.60, 2 units
    }

    /** The single dishes: Salad, Burger, Pasta. */
    public List<MenuItem> getItems() {
        return this.items;
    }

    /** Stage 3 combo meals, in menu order. */
    public List<ComboMeal> getCombos() {
        return this.combos;
    }

    public MenuItem getItem(String name) {
        for (MenuItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }
}
