package restaurantrush.entity.staff;

public abstract class Staff {
  private final String name;
    private boolean actedThisTurn;
 
    protected Staff(String name) {
        this.name = name;
        this.actedThisTurn = false;
    }
 
    public String getName() {
        return name;
    }
 
    public boolean hasActedThisTurn() {
        return actedThisTurn;
    }
 
    /** Subclasses call this ONLY after an action succeeds (invalid choices must not use up the task). */
    protected void markActed() {
        actedThisTurn = true;
    }
 
    /** Controller calls this at the start of every turn. */
    public void resetTurn() {
        actedThisTurn = false;
    }
 
    /** E.g. "Waiter", "Chef", "Cashier". Used by the CLI display. */
    public abstract String getRole();
}
