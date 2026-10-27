package restaurantrush.entity.menu;

import restaurantrush.util.Money;
import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items;
    
    public Menu() {
        this.items = new ArrayList<>();
        items.add(new MenuItem("Salad", Money.ofCents(900), 1));
        items.add(new MenuItem("Burger", Money.ofCents(1500), 1));
        items.add(new MenuItem("Pasta", Money.ofCents(1800), 2));
    }

    public List<MenuItem> getItems() {
        return this.items;
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
