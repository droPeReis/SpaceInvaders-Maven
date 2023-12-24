import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Handles window initialization and primary game setup
 * @author Bernardo Copstein, Rafael Copstein
 */

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Menu.getInstance();
    }

    public static void main(String args[]) {
        launch();
    }
}
