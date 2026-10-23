package restaurantrush.boundary;

import java.util.Scanner;

import restaurantrush.entity.restaurant.Restaurant;

// Various UI methods that GameController can call.
// Separation of concerns. GameCLI the boundary. GameController the controller
public class GameCLI {
    private InputReader input;
    private StateDisplay display;

    public GameCLI() {
        System.out.println("Welcome to Restaurant Rush");
        this.input = new InputReader();
        this.display = new StateDisplay();
    }

    public void showStartOfTurn(int turn, Restaurant r) {
        display.renderStartOfTurn(turn, r);
    }

    public void showEndOfTurn(int turn, Restaurant r) {
        display.renderEndOfTurn(turn, r);
    }

    public void showFinalResult(GameResult res, Restaurant r) {
        display.renderFinal(res, r);
    }

    public int requestPlayerAction() {
        System.out.println("1. Waiter seats a customer");
        System.out.println("2. Waiter takes an order");
        System.out.println("3. Chef cooks the food");
        System.out.println("4. Cashier collects money");
        System.out.println("5. Do nothing");
        return input.readInt("Select an action (1-5)", 1, 5);
    }
}
