package restaurantrush.entity.staff;


public abstract class Staff {
    private final String name;
    protected boolean actedThisTurn;

    protected Staff(String name) {
        this.name = name;
        this.actedThisTurn = false;
    }

    public void startTurn() {
        actedThisTurn = false;
    }

    public boolean hasActed() {
        return actedThisTurn;
    }

    public String getName() {
        return name;
    }

    public abstract String getRole();
}
