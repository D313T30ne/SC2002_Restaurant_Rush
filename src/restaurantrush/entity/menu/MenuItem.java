package restaurantrush.entity.menu;

import restaurantrush.util.Money;  

public class MenuItem {
    private String name;
    private Money price;
    private int prepUnits;

    public MenuItem(String name, Money price, int prepUnits) {
        this.name = name;
        this.price = price;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return this.name;
    }

    public Money getPrice() {
        return this.price;
    }

    public int getPrepUnits() {
        return this.prepUnits;
    }
}
