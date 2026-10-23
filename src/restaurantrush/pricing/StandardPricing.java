package restaurantrush.pricing;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.customer.Customer;

public class StandardPricing implements PricingPolicy {
    // Open/Closed Principle, using Customer abstract class
    public int priceFor(MenuItem item, Customer c) {
        return item.getPrice();
    }
}
