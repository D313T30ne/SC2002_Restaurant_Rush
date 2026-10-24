package restaurantrush.entity.menu;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private List<MenuItem> items;
    
    public Menu() {
        this.items = new ArrayList<>();
        items.add(new MenuItem("Salad", 900, 1));
        items.add(new MenuItem("Burger", 1500, 1));
        items.add(new MenuItem("Pasta", 18, 2));
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
