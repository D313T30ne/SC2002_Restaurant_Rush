package restaurantrush;

import restaurantrush.boundary.GameCLI;
import restaurantrush.control.GameConfig;
import restaurantrush.control.GameController;

/** Entry point. Builds the base setting and starts the game. */
public class Main {

    public static void main(String[] args) {
        GameController controller = new GameController(GameConfig.baseSetting(), new GameCLI());
        controller.run();
    }
}
