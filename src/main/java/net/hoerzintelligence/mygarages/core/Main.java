package net.hoerzintelligence.mygarages.core;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    public static Stage window;

    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setResizable(false);
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");

        window.setTitle("Garage Configuration Panel");
        window.setScene(scene);
        window.show();
    }

    public static void switchView(Scene scene) throws Exception {
        window.setTitle("Your garages");
        window.setScene(scene);
        window.show();
    }
}
