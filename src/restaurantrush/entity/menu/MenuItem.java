package restaurantrush.entity.menu;

public class MenuItem {
    private String name;
    private int price;
    private int prepUnits;

    public MenuItem(String name, int price, int prepUnits) {
        this.name = name;
        this.price = price;
        this.prepUnits = prepUnits;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public int getPrepUnits() {
        return this.prepUnits;
    }
}
