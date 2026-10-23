package restaurantrush.pricing;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.customer.Customer;

public interface PricingPolicy {
    // Strategy pattern
    int priceFor(MenuItem item, Customer customer);
}
