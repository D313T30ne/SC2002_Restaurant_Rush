package restaurantrush.entity.menu;

/**
 * Stage 3. A combo is made of two existing menu items.
 *   preparation units = sum of the two components
 *   price             = 90% of the components' combined price, rounded to the nearest cent
 * Example: Salad + Pasta = ($9 + $18) x 0.9 = $24.30, 3 units.
 *
 * A ComboMeal IS-A MenuItem, so Order, Chef and Waiter handle it without any change.
 */
public class ComboMeal extends MenuItem {

    private static final int COMBO_DISCOUNT_PERCENT = 10;

    private final MenuItem first;
    private final MenuItem second;

    public ComboMeal(MenuItem first, MenuItem second) {
        super(first.getName() + " + " + second.getName(),
                first.getPrice().add(second.getPrice()).applyPercentDiscount(COMBO_DISCOUNT_PERCENT),
                first.getPrepUnits() + second.getPrepUnits());
        this.first = first;
        this.second = second;
    }

    public MenuItem getFirst() {
        return this.first;
    }

    public MenuItem getSecond() {
        return this.second;
    }
}