package restaurantrush.entity.staff;

public class Staff {
    private String name;
    protected boolean actedThisTurn;
 
    protected Staff(String name) {
        this.name = name;
        this.actedThisTurn = false;
    }
 
    /** Controller calls this at the start of every turn. */
    public void startTurn() {
        actedThisTurn = false;
    }
 
    public boolean hasActed() {
        return actedThisTurn;
    }
 
    public String getName() {
        return name;
    }
 
    /** E.g. "Waiter", "Chef", "Cashier". */
    public abstract String getRole();
}
