package restaurantrush.pricing;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.customer.Customer;
import restaurantrush.util.Money;

public class StandardPricing implements PricingPolicy {
    // Open/Closed Principle, using Customer abstract class:
    // each customer type decides its own bill (e.g. VIP 10% off) through calculateBill.
    public Money priceFor(MenuItem item, Customer c) {
        return c.calculateBill(item.getPrice());
    }
}
