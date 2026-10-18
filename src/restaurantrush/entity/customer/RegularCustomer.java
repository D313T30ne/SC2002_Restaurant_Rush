package restaurantrush.entity.customer;

/** A standard customer: loses 8 satisfaction per turn spent waiting. */
public class RegularCustomer extends Customer {

    private static final int WAITING_LOSS = 8;

    public RegularCustomer(int id) {
        super(id);
    }

    @Override
    protected int getWaitingLoss() {
        return WAITING_LOSS;
    }
}