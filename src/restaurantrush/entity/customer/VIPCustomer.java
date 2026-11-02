package restaurantrush.entity.customer;

import restaurantrush.util.Money;

/**
 * A VIP customer: more patient than a Regular (loses 5 satisfaction per waiting
 * turn) and receives 10% off the bill.
 *
 * The discount is applied through calculateBill(), which the pricing policy calls
 * when the Waiter takes the order, so the discounted price is locked at order time
 * and neither the Waiter nor the controller needs to know a VIP exists.
 */
public class VIPCustomer extends Customer {

    private static final int WAITING_LOSS = 5;
    private static final int DISCOUNT_PERCENT = 10;

    public VIPCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitingLoss() {
        return WAITING_LOSS;
    }

    @Override
    public Money calculateBill(Money basePrice) {
        return basePrice.applyPercentDiscount(DISCOUNT_PERCENT);
    }
}