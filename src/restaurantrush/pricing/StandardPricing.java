package restaurantrush.pricing;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.customer.Customer;
import restaurantrush.util.Money;

public class StandardPricing implements PricingPolicy {
    // Open/Closed Principle, using Customer abstract class
    public Money priceFor(MenuItem item, Customer c) {
        return item.getPrice();
    }
}
