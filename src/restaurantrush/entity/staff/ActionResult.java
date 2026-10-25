package restaurantrush.entity.staff;

public class ActionResult {

    private final boolean success;
    private final String message;

    private ActionResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public static ActionResult success(String msg) {
        return new ActionResult(true, msg);
    }

    public static ActionResult failure(String msg) {
        return new ActionResult(false, msg);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
