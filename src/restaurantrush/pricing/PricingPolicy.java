package restaurantrush.pricing;
import restaurantrush.entity.menu.MenuItem;
import restaurantrush.entity.customer.Customer;
import restaurantrush.util.Money;

public interface PricingPolicy {
    // Strategy pattern
    Money priceFor(MenuItem item, Customer customer);
}
