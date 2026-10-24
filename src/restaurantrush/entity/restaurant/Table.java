package restaurantrush.entity.restaurant;

import restaurantrush.entity.customer.Customer;

public class Table {

    private final int number; //Table number
    private Customer occupant;

    public Table(int number) {
        this.number = number;
        this.occupant = null;
    }

    public boolean isFree() {
        return occupant == null;
    }

    // Seat a customer. Only allowed when the table is free
    public void seat(Customer c) {
        // Check not needed, just here in case of bug.
        if (!isFree()) {
            throw new IllegalStateException("Table " + number + " is already occupied");
        }
        this.occupant = c;
    }

    // Called when the customer pays or leaves
    public void free() {
        this.occupant = null;
    }

    public Customer getOccupant() {
        return occupant;
    }

    public int getNumber() {
        return number;
    }
}