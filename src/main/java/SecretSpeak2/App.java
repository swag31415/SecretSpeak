package SecretSpeak2;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private static Stage pStage;
    private static App instance;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        pStage = primaryStage;
        primaryStage.setResizable(false);
        loadScene("enter_screen.fxml");
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        instance = this;
    }

    public static void loadScene(String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(instance.getClass().getClassLoader().getResource(fileName));
        Pane pane = loader.<Pane>load();
        pStage.setScene(new Scene(pane));
    }
}